package dsekercioglu.server;

import dsekercioglu.general.characters.Ability;
import static dsekercioglu.general.characters.Ability.*;
import dsekercioglu.general.characters.Animal;
import dsekercioglu.general.characters.HippoAI;
import dsekercioglu.general.characters.OrcaAI;
import dsekercioglu.general.characters.SharkAI;
import dsekercioglu.general.characters.Swimmer;
import dsekercioglu.general.multiPlayer.CharacterInfo;
import dsekercioglu.general.multiPlayer.ControlInfo;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import processing.core.PApplet;

public class Environment {

    public ArrayList<Swimmer> characters = new ArrayList();
    private final int WIDTH = 2000;
    private final int HEIGHT = 2000;
    private final float BLEED_DAMAGE = 5;
    private final float SHOCK_DAMAGE = 15;
    private final float HOLD_DAMAGE = 2;
    private final float STICK_DAMAGE = 1;
    private final float GRAB_DAMAGE = 1.5F;
    private final float REGEN = 2;
    private final float KNOCKBACK_MULTIPLIER = 2;
    private final float SUPERBITE_MULTIPLIER = 3;
    private final int BLINDNESS = 300;
    private final int POISON_DAMAGE = 1;

    private final float KNOCKBACK = 300;

    ArrayList<Ability> abilities = new ArrayList<>();
    ArrayList<Swimmer> attackers = new ArrayList<>();
    ArrayList<Swimmer> victims = new ArrayList<>();
    ArrayList<Integer> time = new ArrayList<>();

    HashMap<Animal, Rectangle2D.Double[]> animalTypes = new HashMap<>();

    public Environment() {
        this.characters = new ArrayList();
        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).respawn(WIDTH, HEIGHT);
        }
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
                    swimmer.update(c.mouseX, c.mouseY, c.mousePressed);
                } else {
                    swimmer.update(0, 0, true);
                }
                swimmer.x = Math.max(-WIDTH, Math.min(swimmer.x, WIDTH));
                swimmer.y = Math.max(-HEIGHT, Math.min(swimmer.y, HEIGHT));
                swimmer.regen();
            } else {
                swimmer.respawn(WIDTH, HEIGHT);
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
    }

    private void handleIntersections() {
        for (int i = 0; i < characters.size(); i++) {
            Swimmer p1 = characters.get(i);
            Line2D r1 = getHitter(p1);
            for (int j = 0; j < characters.size(); j++) {
                Swimmer p2 = characters.get(j);
                Line2D[] r2 = getHitBox(p2);
                if (p1.team.equals(p2.team) && !p1.equals(p2) && Geometry.intersects(new Line2D[]{r1}, r2)) {
                    p2.hit(p1.damage);
                    if (p1.energyTime > 0) {
                        p1.energyTime = 0;
                        abilities.add(p1.ability);
                        attackers.add(p1);
                        victims.add(p2);
                        time.add(p1.abilityTime);
                    } else {
                        p1.move(-KNOCKBACK, p1.angle);
                    }
                }
            }
        }
    }

    private void handleAbilities() {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < abilities.size(); i++) {
            Ability a = abilities.get(i);
            int newTime = time.get(i) - 1;
            if (a.equals(BLEED)) {
                victims.get(i).hit(BLEED_DAMAGE);
            } else if (a.equals(HOLD)) {
                Swimmer victim = victims.get(i);
                Swimmer attacker = attackers.get(i);
                attacker.move(0, attacker.angle);
                attacker.energyTime = 0;
                victim.x = (float) (attacker.x + (Math.cos(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.y = (float) (attacker.y + (Math.sin(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.angle = (float) (attacker.angle + Math.PI / 2);
                victim.hit(HOLD_DAMAGE);
                if (newTime <= 0) {
                    victim.setMoveInAngle(KNOCKBACK, attacker.angle);
                }
            } else if (a.equals(SHOCK)) {
                Swimmer victim = victims.get(i);
                victim.hit(SHOCK_DAMAGE);
            } else if (a.equals(STICK)) {
                Swimmer victim = victims.get(i);
                Swimmer attacker = attackers.get(i);
                attacker.x = (float) (victim.x - (Math.cos(victim.angle) * (victim.getWidth() / 2 + 1)));
                attacker.y = (float) (victim.y - (Math.sin(victim.angle) * (victim.getWidth() / 2 + 1)));
                attacker.angle = (float) victim.angle;
                victim.hit(STICK_DAMAGE);
                if (newTime <= 0) {
                    attacker.move(-KNOCKBACK, attacker.angle);
                }
            } else if (a.equals(REGEN_TIME)) {
                Swimmer victim = victims.get(i);
                Swimmer attacker = attackers.get(i);
                attacker.energyTime = 0;
                victim.x = (float) (attacker.x + (Math.cos(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.y = (float) (attacker.y + (Math.sin(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.angle = (float) (attacker.angle + Math.PI / 2);
                if (newTime <= 0) {
                    victim.setMoveInAngle(KNOCKBACK * 2, attacker.angle);
                }
                attacker.hit(-REGEN);
                victim.hit(0.5F);
            } else if (a.equals(Ability.KNOCKBACK)) {
                Swimmer victim = victims.get(i);
                Swimmer attacker = attackers.get(i);
                victim.hit(attacker.damage * KNOCKBACK_MULTIPLIER);
                victim.setMoveInAngle(KNOCKBACK_MULTIPLIER * KNOCKBACK, attacker.angle);
                attacker.move(-KNOCKBACK, attacker.angle);
                newTime = 0;
            } else if (a.equals(GRAB)) {
                Swimmer victim = victims.get(i);
                Swimmer attacker = attackers.get(i);
                attacker.energyTime = 0;
                victim.x = (float) (attacker.x + (Math.cos(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.y = (float) (attacker.y + (Math.sin(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.angle = (float) (attacker.angle + Math.PI / 2);
                victim.hit(GRAB_DAMAGE);
                if (newTime <= 0) {
                    victim.setMoveInAngle(KNOCKBACK, attacker.angle);
                }
            } else if (a.equals(SUPERBITE)) {
                Swimmer victim = victims.get(i);
                Swimmer attacker = attackers.get(i);
                victim.hit(attacker.damage * SUPERBITE_MULTIPLIER);
                victim.setMoveInAngle(SUPERBITE_MULTIPLIER * KNOCKBACK, attacker.angle);
                newTime = 0;
            } else if (a.equals(INKSPILL)) {
                Swimmer victim = victims.get(i);
                Swimmer attacker = attackers.get(i);
                attacker.energyTime = 0;
                victim.x = (float) (attacker.x - (Math.cos(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.y = (float) (attacker.y - (Math.sin(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.angle = (float) (attacker.angle + Math.PI);
                victim.setBlind(BLINDNESS);
            } else if (a.equals(POISON)) {
                attackers.get(i).energyTime = 0;
                victims.get(i).hit(POISON_DAMAGE);
            }
            time.set(i, newTime);
            if (newTime <= 0) {
                indices.add(i);
            }
        }
        for (int i = 0; i < indices.size(); i++) {
            abilities.remove(indices.get(i) - i);
            victims.remove(indices.get(i) - i);
            attackers.remove(indices.get(i) - i);
            time.remove(indices.get(i) - i);
        }
    }

    private Line2D getHitter(Swimmer s) {
        return Geometry.rotateCenter(new Rectangle2D.Double(s.x - s.getWidth() / 2, s.y - s.getHeight() / 2, s.getWidth(), s.getHeight()), s.angle)[1];
    }

    private Line2D[] getHitBox(Swimmer s) {
        return Geometry.rotateCenter(new Rectangle2D.Double(s.x - s.getWidth() / 2, s.y - s.getHeight() / 2, s.getWidth(), s.getHeight()), s.angle);
    }
}
