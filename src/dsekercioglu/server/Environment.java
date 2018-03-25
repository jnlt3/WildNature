package dsekercioglu.server;

import dsekercioglu.general.characters.Ability;
import static dsekercioglu.general.characters.Ability.*;
import dsekercioglu.general.characters.Animal;
import dsekercioglu.general.characters.Guardian;
import dsekercioglu.general.characters.Hippo;
import dsekercioglu.general.characters.HippoAI;
import dsekercioglu.general.characters.OrcaAI;
import dsekercioglu.general.characters.SharkAI;
import dsekercioglu.general.characters.Swimmer;
import static dsekercioglu.general.characters.Team.BLUE;
import static dsekercioglu.general.characters.Team.GREEN;
import static dsekercioglu.general.characters.Team.RED;
import dsekercioglu.general.multiPlayer.CharacterInfo;
import dsekercioglu.general.multiPlayer.ControlInfo;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.PApplet;

public class Environment {

    public ArrayList<Swimmer> characters = new ArrayList();
    public final int WIDTH = 2000;
    public final int HEIGHT = 2000;
    private final float BLEED_DAMAGE = 5;
    private final float SHOCK_DAMAGE = 15;
    private final float HOLD_DAMAGE = 3.5F;
    private final float STICK_DAMAGE = 1;
    private final float GRAB_DAMAGE = 2.5F;
    private final float KNOCKBACK_MULTIPLIER = 1.5F;
    private final float SUPERBITE_MULTIPLIER = 3;
    private final int BLINDNESS = 300;
    private final int POISON_DAMAGE = 3;
    private final float HORN_MULTIPLIER = 2;
    private final int REGEN_AMOUNT = 1200;

    private final float KNOCKBACK = 300;

    HashMap<Swimmer, Ability> charAbilities = new HashMap<>();

    ArrayList<Ability> abilities = new ArrayList<>();
    ArrayList<Swimmer> attackers = new ArrayList<>();
    ArrayList<Swimmer> victims = new ArrayList<>();
    ArrayList<Integer> time = new ArrayList<>();

    HashMap<Animal, Rectangle2D.Double[]> animalTypes = new HashMap<>();

    HashMap<String, Integer> scores = new HashMap<>();

    public Environment() {
        this.characters = new ArrayList();
    }

    public void update(HashMap<String, ControlInfo> hashMap) {
        handleIntersections();
        handleAbilities();
        ArrayList<Swimmer> toRemove = new ArrayList<>();
        for (int i = 0; i < this.characters.size(); i++) {
            Swimmer swimmer = (Swimmer) this.characters.get(i);
            if (swimmer.isAlive()) {
                swimmer.updateMove();
                if (hashMap.containsKey(swimmer.getName())) {
                    ControlInfo c = (ControlInfo) hashMap.get(swimmer.getName());
                    swimmer.update(c.mouseX, c.mouseY, c.one || c.two || c.three);
                    if (c.one) {
                        charAbilities.put(swimmer, swimmer.ability1);
                    } else if (c.two) {
                        charAbilities.put(swimmer, swimmer.ability2);
                    } else if (c.three) {
                        charAbilities.put(swimmer, swimmer.ability3);
                    }
                } else {
                    swimmer.update(0, 0, true);
                }
                swimmer.x = Math.max(-WIDTH, Math.min(swimmer.x, WIDTH));
                swimmer.y = Math.max(-HEIGHT, Math.min(swimmer.y, HEIGHT));
                swimmer.regen();
            } else {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        characters.remove(swimmer);
                        try {
                            Thread.sleep(3000L);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        swimmer.respawn(WIDTH, HEIGHT);
                        characters.add(swimmer);
                    }
                };
                Thread t = new Thread(r);
                t.start();
            }
        }
        characters.removeAll(toRemove);
        toRemove.clear();
        CharacterInfo ci = new CharacterInfo();
        ci.characters = new ArrayList();
        for (int i = 0; i < this.characters.size(); i++) {
            ci.characters.add(((Swimmer) this.characters.get(i)).getDrawInfo());
        }
        WildNatureServer.server.sendToAllUDP(ci);
    }

    public void addCharacter(Swimmer s) {
        s.respawn(WIDTH, HEIGHT);
        this.characters.add(s);
        charAbilities.put(s, s.ability1);
        scores.put(s.getName(), 0);
    }

    private void handleIntersections() {
        for (int i = 0; i < characters.size(); i++) {
            Swimmer p1 = characters.get(i);
            Line2D r1 = getHitter(p1);
            for (int j = 0; j < characters.size(); j++) {
                Swimmer p2 = characters.get(j);
                Line2D[] r2 = getHitBox(p2);
                if (!p1.team.equals(p2.team) && !p1.equals(p2) && Geometry.intersects(new Line2D[]{r1}, r2)) {
                    p2.hit(p1.damage);
                    if (p1.energyTime > 0 && p2.isAlive()) {
                        p1.energyTime = 0;
                        abilities.add(charAbilities.get(p1));
                        attackers.add(p1);
                        victims.add(p2);
                        time.add(p1.abilityTime);
                    } else {
                        if (!p2.isAlive()) {
                            scores.put(p1.getName(), scores.get(p1.getName()) + 1);
                        }
                        p1.move(-KNOCKBACK, p1.angle);
                    }
                    p1.energyTime = 0;
                }
            }
        }
    }

    private void handleAbilities() {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < abilities.size(); i++) {
            Ability a = abilities.get(i);
            int newTime = time.get(i) - 1;
            Swimmer victim = victims.get(i);
            Swimmer attacker = attackers.get(i);
            if (a.equals(BLEED)) {
                victims.get(i).hit(BLEED_DAMAGE);
            } else if (a.equals(HOLD)) {
                attacker.move(0, attacker.angle);
                victim.x = (float) (attacker.x + (Math.cos(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.y = (float) (attacker.y + (Math.sin(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.angle = (float) (attacker.angle + Math.PI / 2);
                victim.hit(HOLD_DAMAGE);
                if (newTime <= 0) {
                    victim.setMoveInAngle(KNOCKBACK, attacker.angle);
                }
            } else if (a.equals(SHOCK)) {
                victim.hit(SHOCK_DAMAGE);
            } else if (a.equals(STICK)) {
                attacker.x = (float) (victim.x - (Math.cos(victim.angle) * (victim.getWidth() / 2 + 1)));
                attacker.y = (float) (victim.y - (Math.sin(victim.angle) * (victim.getWidth() / 2 + 1)));
                attacker.angle = (float) victim.angle;
                victim.hit(STICK_DAMAGE);
                if (newTime <= 0) {
                    attacker.move(-KNOCKBACK, attacker.angle);
                }
            } else if (a.equals(DRAIN_GRAB)) {
                victim.energy -= 0.5;
                victim.x = (float) (attacker.x + (Math.cos(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.y = (float) (attacker.y + (Math.sin(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.angle = (float) (attacker.angle + Math.PI / 2);
                if (newTime <= 0) {
                    victim.setMoveInAngle(KNOCKBACK * 2, attacker.angle);
                }
                victim.energy = 0;
            } else if (a.equals(Ability.KNOCKBACK)) {
                victim.hit(attacker.damage * KNOCKBACK_MULTIPLIER);
                victim.setMoveInAngle(KNOCKBACK_MULTIPLIER * KNOCKBACK, attacker.angle);
                attacker.move(-KNOCKBACK, attacker.angle);
                newTime = 0;
            } else if (a.equals(GRAB)) {
                victim.x = (float) (attacker.x + (Math.cos(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.y = (float) (attacker.y + (Math.sin(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.angle = (float) (attacker.angle + Math.PI / 2);
                victim.hit(GRAB_DAMAGE);
                if (newTime <= 0) {
                    victim.setMoveInAngle(KNOCKBACK, attacker.angle);
                }
            } else if (a.equals(SUPERBITE)) {
                victim.hit(attacker.damage * SUPERBITE_MULTIPLIER);
                victim.setMoveInAngle(SUPERBITE_MULTIPLIER * KNOCKBACK, attacker.angle);
                newTime = 0;
            } else if (a.equals(INKSPILL)) {
                victim.x = (float) (attacker.x - (Math.cos(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.y = (float) (attacker.y - (Math.sin(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.angle = (float) (attacker.angle + Math.PI);
                victim.setBlind(BLINDNESS);
            } else if (a.equals(POISON)) {
                victim.hit(POISON_DAMAGE);
            } else if (a.equals(PRISON)) {
                victim.x = ((Guardian) attacker).mouseX;
                victim.y = ((Guardian) attacker).mouseY;
                victim.velocity = 0;
                victim.angle += Math.PI / 9;
            } else if (a.equals(REGEN_BOOST)) {
                attacker.health += REGEN_AMOUNT;
                newTime = 0;
            } else if (a.equals(DRAIN_HIT)) {
                victim.energy = Math.max(victim.energy - 1, 0);
                newTime = 0;
            } else if (a.equals(DAMAGE_BOOST)) {
                victim.hit(victim.damage * 3);
                newTime = 0;
            } else if (a.equals(SLOW_DOWN)) {
                victim.velocity = 1;
            } else if (a.equals(HORN)) {
                victim.hit(attacker.damage * HORN_MULTIPLIER);
                newTime = 0;
                victim.setMoveInAngle(KNOCKBACK, attacker.angle);
                abilities.add(BLEED);
                attackers.add(attacker);
                victims.add(victim);
                time.add(attacker.abilityTime / 3);
            }
            if (!victim.isAlive()) {
                scores.put(attacker.getName(), scores.get(attacker.getName()) + 1);
                newTime = 0;
            }
            if (!attacker.isAlive() || (victim instanceof Guardian && victim.energyTime > 0)) {
                newTime = 0;
            }
            time.set(i, newTime);
            if (newTime <= 0) {
                indices.add(i);
            }
        }
        for (int i = 0; i < indices.size(); i++) {
            int index = indices.get(i) - i;
            if (index >= 0) {
                abilities.remove(index);
                victims.remove(index);
                attackers.remove(index);
                time.remove(index);
            }
        }
    }

    private Line2D getHitter(Swimmer s) {
        return Geometry.rotateCenter(new Rectangle2D.Double(s.x - s.getWidth() / 2, s.y - s.getHeight() / 2, s.getWidth(), s.getHeight()), s.angle)[1];
    }

    private Line2D[] getHitBox(Swimmer s) {
        return Geometry.rotateCenter(new Rectangle2D.Double(s.x - s.getWidth() / 2, s.y - s.getHeight() / 2, s.getWidth(), s.getHeight()), s.angle);
    }
}
