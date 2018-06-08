package dsekercioglu.server;

import dsekercioglu.general.characters.Ability;
import static dsekercioglu.general.characters.Ability.*;
import dsekercioglu.general.characters.Alien;
import dsekercioglu.general.characters.BlackMarlin;
import dsekercioglu.general.characters.ColossalSquid;
import dsekercioglu.general.characters.Crocodile;
import dsekercioglu.general.characters.Dolphin;
import dsekercioglu.general.characters.DoodFish;
import dsekercioglu.general.characters.Sharkodile;
import dsekercioglu.general.characters.DrawInfo;
import dsekercioglu.general.characters.ElectricMarlin;
import dsekercioglu.general.characters.Guardian;
import dsekercioglu.general.characters.Hippo;
import dsekercioglu.general.characters.MakoShark;
import dsekercioglu.general.characters.Marlin;
import dsekercioglu.general.characters.Marlinium;
import dsekercioglu.general.characters.MegaMouth;
import dsekercioglu.general.characters.MiniMarlin;
import dsekercioglu.general.characters.Orca;
import dsekercioglu.general.characters.Shark;
import dsekercioglu.general.characters.Swimmer;
import dsekercioglu.general.characters.Team;
import static dsekercioglu.general.characters.Team.BLUE;
import static dsekercioglu.general.characters.Team.INDEPENDENT;
import static dsekercioglu.general.characters.Team.DOMINATOR;
import static dsekercioglu.general.characters.Team.GREEN;
import static dsekercioglu.general.characters.Team.RED;
import static dsekercioglu.general.characters.Team.YELLOW;
import dsekercioglu.general.characters.TigerShark;
import dsekercioglu.general.characters.TwoRulers;
import dsekercioglu.general.control.AmbushControl;
import dsekercioglu.general.control.BackTrackControl;
import dsekercioglu.general.control.StraightAttackControl;
import dsekercioglu.general.multiPlayer.CharacterInfo;
import dsekercioglu.general.multiPlayer.ControlInfo;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Environment {

    public ArrayList<Swimmer> characters = new ArrayList();
    public final int WIDTH = 2000;
    public final int HEIGHT = 2000;
    private final float BLEED_DAMAGE = 5F;
    private final float SHOCK_DAMAGE = 8F;
    private final float HOLD_DAMAGE = 3F;
    private final float GRAB_DAMAGE = 2F;
    private final float KNOCKBACK_MULTIPLIER = 1.5F;
    private final float SUPERBITE_MULTIPLIER = 4F;
    private final int BLINDNESS = 300;
    private final int POISON_DAMAGE = 3;
    private final float HORN_MULTIPLIER = 1;
    private final int REGEN_AMOUNT = 200;

    public HashMap<Swimmer, Ability> charAbilities = new HashMap<>();

    ArrayList<Integer> indices = new ArrayList<>();

    ArrayList<Ability> abilities = new ArrayList<>();
    ArrayList<Swimmer> attackers = new ArrayList<>();
    ArrayList<Swimmer> victims = new ArrayList<>();
    ArrayList<Integer> time = new ArrayList<>();

    public HashMap<String, Integer> scores = new HashMap<>();

    public Environment() {
//        for (int i = 0; i < 1; i++) {
//            Swimmer m = new Angleraptor("7", 0, 0, null, this);
//            m.team = GREEN;
//            m.control = new StraightAttackControl(m, this);
//            addCharacter(m);
//        }
        for (int i = 0; i < 1; i++) {
            Swimmer m = new Shark("Porpoz", 0, 0, null, this);
            m.team = BLUE;
            m.control = new StraightAttackControl(m, this);
            addCharacter(m);
        }
//        for (int i = 0; i < 1; i++) {
//            Swimmer m = new Pirahna("Porpoz", 0, 0, null, this);
//            m.team = INDEPENDENT;
//            m.control = new StraightAttackControl(m, this);
//            addCharacter(m);
//        }
//        for (int i = 0; i < 1; i++) {
//            Swimmer m = new Sharkodile("Borb", 0, 0, null, this);
//            m.team = BLUE;
//            m.control = new StraightAttackControl(m, this);
//            addCharacter(m);
//        }
//        for (int i = 0; i < 1; i++) {
//            Swimmer m = new Sharkodile("Borbe", 0, 0, null, this);
//            m.team = BLUE;
//            m.control = new StraightAttackControl(m, this);
//            addCharacter(m);
//        }
//        for (int i = 0; i < 1; i++) {
//            Swimmer m = new Marlinium("Foof", 0, 0, null, this);
//            m.team = BLUE;
//            m.control = new BackTrackControl(m, this);
//            addCharacter(m);
//        }
    }

    public void update(HashMap<String, ControlInfo> hashMap) {
        handleIntersections();
        ArrayList<Swimmer> toRemove = new ArrayList<>();
        for (int i = 0; i < this.characters.size(); i++) {
            Swimmer swimmer = (Swimmer) this.characters.get(i);
            if (swimmer.isAlive()) {
                swimmer.updateMove();
                if (hashMap.containsKey(swimmer.getName())) {
                    ControlInfo c = (ControlInfo) hashMap.get(swimmer.getName());
                    swimmer.update(c.mouseX, c.mouseY, c.power);
                    if (c.power) {
                        charAbilities.put(swimmer, swimmer.ability1);
                    }
                } else {
                    swimmer.update(600, 300, true);
                }
                swimmer.x = Math.max(-WIDTH, Math.min(swimmer.x, WIDTH));
                swimmer.y = Math.max(-HEIGHT, Math.min(swimmer.y, HEIGHT));
                swimmer.regen();
            } else {
                long sleepTime = swimmer.team.equals(DOMINATOR) ? 180000L : 3000L;
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        characters.remove(swimmer);
                        if (!swimmer.clone) {
                            try {
                                Thread.sleep(sleepTime);
                            } catch (InterruptedException ex) {
                                Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            long time = System.currentTimeMillis();
                            while (System.currentTimeMillis() - time < 60000) {
                                ControlInfo c = (ControlInfo) hashMap.get(swimmer.getName());
                                if (c == null) {
                                    swimmer.respawn(WIDTH, HEIGHT);
                                    characters.add(swimmer);
                                    break;
                                } else if (c.enter) {
                                    swimmer.respawn(WIDTH, HEIGHT);
                                    characters.add(swimmer);
                                    break;
                                }
                            }
                        }
                    }
                };
                Thread t = new Thread(r);
                t.start();
            }
        }
        characters.removeAll(toRemove);
        toRemove.clear();

        handleAbilities();

        CharacterInfo ci = new CharacterInfo();
        ci.characters = new ArrayList();
        for (int i = 0; i < this.characters.size(); i++) {
            DrawInfo di = ((Swimmer) this.characters.get(i)).getDrawInfo();
            this.characters.get(i).damageRecieved = 0;
            try {
                di.score = scores.get(di.name);
            } catch (NullPointerException e) {
                System.out.println(di.name);
            }
            ci.characters.add(di);
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
                if (attackable(p1.team, p2.team) && !p1.equals(p2) && Geometry.intersects(new Line2D[]{r1}, r2)) {
                    if (p1.energyTime > 0 && p2.isAlive()) {
                        p1.energyTime = 0;
                        int index = attackers.indexOf(p1);
                        if (index == -1 || (index != -1 && victims.get(index) != p2)) {
                            abilities.add(charAbilities.get(p1));
                            attackers.add(p1);
                            victims.add(p2);
                            time.add(p1.abilityTime);
                        }
                    } else if (p2.getInvulnerability() <= 0) {
                        p1.energyTime = 0;
                        p2.characterHit(p1.damage, p1.armorPiercing);
                        if (!p2.isAlive()) {
                            scores.put(p1.getName(), scores.get(p1.getName()) + 1);
                        }
                    }
                    p1.setMove(-p2.knockbackPower, p1.angle);
                }
            }
        }
    }

    private void handleAbilities() {
        indices.clear();
        for (int i = 0; i < abilities.size(); i++) {
            Ability a = abilities.get(i);
            int newTime = time.get(i) - 1;
            Swimmer victim = victims.get(i);
            Swimmer attacker = attackers.get(i);
//            if (!attacker.isAlive() || (victim instanceof Guardian && victim.energyTime > 0)) {
//                newTime = 0;
//            }
            switch (a) {
                case BLEED:
                    victims.get(i).abilityHit(BLEED_DAMAGE);
                    break;
                case HOLD:
                    attacker.control.freeze(true);
                    victim.x = (float) (attacker.x + (Math.cos(attacker.angle) * ((attacker.getWidth() + victim.getHeight()) / 2 + 2)));
                    victim.y = (float) (attacker.y + (Math.sin(attacker.angle) * ((attacker.getWidth() + victim.getHeight()) / 2 + 2)));
                    victim.angle = (float) (attacker.angle + Math.PI / 2);
                    victim.abilityHit(HOLD_DAMAGE + victim.regen);
                    if (newTime <= 0) {
                        attacker.control.freeze(false);
                        victim.setMoveInAngle(attacker.knockbackPower, attacker.angle);
                    }
                    break;
                case SHOCK:
                    victim.abilityHit(SHOCK_DAMAGE);
                    break;
                case KNOCKBACK:
                    victim.characterHit(attacker.damage * KNOCKBACK_MULTIPLIER, attacker.armorPiercing);
                    victim.setMoveInAngle(KNOCKBACK_MULTIPLIER * attacker.knockbackPower, attacker.angle);
                    newTime = 0;
                    break;
                case GRAB:
                    victim.x = (float) (attacker.x + (Math.cos(attacker.angle) * ((attacker.getWidth() + victim.getHeight()) / 2 + 2)));
                    victim.y = (float) (attacker.y + (Math.sin(attacker.angle) * ((attacker.getWidth() + victim.getHeight()) / 2 + 2)));
                    victim.angle = (float) (attacker.angle + Math.PI / 2);
                    victim.abilityHit(GRAB_DAMAGE + victim.regen);
                    if (newTime <= 0) {
                        victim.setMoveInAngle(attacker.knockbackPower, attacker.angle);
                    }
                    break;
                case SUPERBITE:
                    victim.characterHit(attacker.damage * SUPERBITE_MULTIPLIER, attacker.armorPiercing);
                    victim.setMoveInAngle(SUPERBITE_MULTIPLIER * attacker.knockbackPower, attacker.angle);
                    newTime = 0;
                    break;
                case INKSPILL:
                    victim.abilityHit(BLEED_DAMAGE + victim.regen);
                    victim.x = (float) (attacker.x - (Math.cos(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                    victim.y = (float) (attacker.y - (Math.sin(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                    victim.angle = (float) (attacker.angle + Math.PI);
                    if (newTime <= 0) {
                        victim.setMoveInAngle(attacker.knockbackPower, attacker.angle + Math.PI);
                        victim.setBlind(BLINDNESS);
                    }
                    break;
                case POISON:
                    victim.abilityHit(POISON_DAMAGE + victim.regen);
                    break;
                case PRISON:
                    victim.x = ((Guardian) attacker).mouseX;
                    victim.y = ((Guardian) attacker).mouseY;
                    victim.velocity = 0;
                    victim.angle += Math.PI / 9;
                    break;
                case REGEN:
                    victim.characterHit(attacker.damage, attacker.armorPiercing);
                    attacker.health += REGEN_AMOUNT;
                    break;
                case REGEN_GRAB:
                    victim.x = (float) (attacker.x + (Math.cos(attacker.angle) * ((attacker.getWidth() + victim.getHeight()) / 2 + 2)));
                    victim.y = (float) (attacker.y + (Math.sin(attacker.angle) * ((attacker.getWidth() + victim.getHeight()) / 2 + 2)));
                    victim.angle = (float) (attacker.angle + Math.PI / 2);
                    attacker.health += 2;
                    break;
                case SLOW_DOWN:
                    victim.velocity = 1;
                    break;
                case HORN:
                    victim.characterHit(attacker.damage * HORN_MULTIPLIER, attacker.armorPiercing);
                    newTime = 0;
                    victim.setMoveInAngle(attacker.knockbackPower, attacker.angle);
                    abilities.add(BLEED);
                    attackers.add(attacker);
                    victims.add(victim);
                    time.add(attacker.abilityTime);
                    break;
                case SHORT_GRAB:
                    victim.x = (float) (attacker.x + (Math.cos(attacker.angle) * ((attacker.getWidth() + victim.getHeight()) / 2 + 2)));
                    victim.y = (float) (attacker.y + (Math.sin(attacker.angle) * ((attacker.getWidth() + victim.getHeight()) / 2 + 2)));
                    victim.angle = (float) (attacker.angle + Math.PI / 2);
                    victim.abilityHit(attacker.damage / 4);
                    if (newTime <= 0) {
                        victim.characterHit(100, attacker.armorPiercing);
                        victim.setMoveInAngle(attacker.knockbackPower * KNOCKBACK_MULTIPLIER, attacker.angle);
                    }
                    break;
                case ELECTRIC_HORN:
                    victim.abilityHit(SHOCK_DAMAGE);
                    victim.control.freeze(true);
                    if (newTime <= 4) {
                        victim.energy = Math.max(victim.energy - 0.1F, 0);
                        victim.control.freeze(false);
                    }
                    break;
                case BLEEDING_KNOCKBACK:
                    victim.characterHit(attacker.damage, attacker.armorPiercing);
                    victim.setMoveInAngle(SUPERBITE_MULTIPLIER * attacker.knockbackPower, attacker.angle);
                    newTime = 0;
                    abilities.add(BLEED);
                    attackers.add(attacker);
                    victims.add(victim);
                    time.add(attacker.abilityTime);
                    break;
                case STUN:
                    victim.control.freeze(true);
                    if (newTime <= 0) {
                        victim.control.freeze(false);
                    }
                    break;
                case SPEEDY:
                    victim.characterHit(attacker.damage, attacker.armorPiercing);
                    victim.setMoveInAngle(KNOCKBACK_MULTIPLIER * attacker.knockbackPower, attacker.angle);
                    attacker.energyTime = 120;
                default:
                    break;
            }
            if (!victim.isAlive()) {
                scores.put(attacker.getName(), scores.get(attacker.getName()) + 1);
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
                victims.get(index).setInvulnerability(1);
                abilities.remove(index);
                victims.remove(index);
                attackers.remove(index);
                time.remove(index);
            }
        }
    }

    private boolean attackable(Team t1, Team t2) {
        return !t1.equals(t2) || t1.equals(INDEPENDENT) || t2.equals(INDEPENDENT);
    }

    private Line2D getHitter(Swimmer s) {
        return Geometry.rotateCenter(new Rectangle2D.Double(s.x - s.getWidth() / 2, s.y - s.getHeight() / 2, s.getWidth(), s.getHeight()), s.angle)[1];
    }

    private Line2D[] getHitBox(Swimmer s) {
        return Geometry.rotateCenter(new Rectangle2D.Double(s.x - s.getWidth() / 2, s.y - s.getHeight() / 2, s.getWidth(), s.getHeight()), s.angle);
    }
}
