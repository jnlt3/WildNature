package dsekercioglu.server;

import dsekercioglu.general.characters.Ability;
import static dsekercioglu.general.characters.Ability.*;
import dsekercioglu.general.characters.Swimmer;
import dsekercioglu.general.multiPlayer.CharacterInfo;
import dsekercioglu.general.multiPlayer.ControlInfo;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import java.util.ArrayList;
import java.util.HashMap;

public class Environment {

    public ArrayList<Swimmer> characters = new ArrayList();
    private final int WIDTH = 5000;
    private final int HEIGHT = 5000;
    private final float ABILITY_THRESHOLD = 10;//Every boost passes this value
    private final int ABILITY_TIME = 150;//Every ability lasts this much;
    private final float BLEED_DAMAGE = 10F;
    private final float GRAB_DAMAGE = 15F;

    ArrayList<Ability> abilities = new ArrayList<>();
    ArrayList<Swimmer> attackers = new ArrayList<>();
    ArrayList<Swimmer> victims = new ArrayList<>();
    ArrayList<Integer> time = new ArrayList<>();

    public Environment() {
        this.characters = new ArrayList();
    }

    public void update(HashMap<String, ControlInfo> hashMap) {
        ArrayList<Swimmer> toRemove = new ArrayList<>();
        for (int i = 0; i < this.characters.size(); i++) {
            Swimmer swimmer = (Swimmer) this.characters.get(i);
            if (swimmer.isAlive()) {
                ControlInfo c = (ControlInfo) hashMap.get(swimmer.getName());
                swimmer.update(c.mouseX, c.mouseY, c.mousePressed);
                swimmer.x = Math.max(-WIDTH, Math.min(swimmer.x, WIDTH));
                swimmer.y = Math.max(-HEIGHT, Math.min(swimmer.y, HEIGHT));
            } else {
                toRemove.add(swimmer);
            }
        }
        characters.removeAll(toRemove);
        toRemove.clear();
        handleAbilities();
        handleIntersections();
        CharacterInfo ci = new CharacterInfo();
        ci.characters = new ArrayList();
        for (int i = 0; i < this.characters.size(); i++) {
            ci.characters.add(((Swimmer) this.characters.get(i)).getDrawInfo());
        }
        WildNatureServer.server.sendToAllUDP(ci);
    }

    public void addCharacter(Swimmer s) {
        this.characters.add(s);
    }

    private void handleIntersections() {
        for (int i = 0; i < characters.size(); i++) {
            Swimmer p1 = characters.get(i);
            Line2D r1 = getHitter(p1);
            System.out.println(r1.getX1() - p1.x + "  " + (r1.getY1() - p1.y));
            for (int j = 0; j < characters.size(); j++) {
                Swimmer p2 = characters.get(j);
                Line2D[] r2 = getHitBox(p2);
                if (!p1.equals(p2) && Geometry.intersects(new Line2D[]{r1}, r2)) {
                    p2.hit(p1.damage);
                    if (p1.velocity > ABILITY_THRESHOLD) {
                        abilities.add(p1.ability);
                        attackers.add(p1);
                        victims.add(p2);
                        time.add(ABILITY_TIME);
                    }
                }
            }
        }
    }

    private void handleAbilities() {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < abilities.size(); i++) {
            Ability a = abilities.get(i);
            if (a.equals(BLEED)) {
                victims.get(i).hit(BLEED_DAMAGE);
            } else if (a.equals(GRAB)) {
                Swimmer victim = victims.get(i);
                Swimmer attacker = attackers.get(i);
                victim.x = (float) (attacker.x + (Math.cos(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.y = (float) (attacker.y + (Math.sin(attacker.angle) * (attacker.getWidth() / 2 + 1)));
                victim.angle = (float) (attacker.angle + Math.PI / 2);
                victim.hit(GRAB_DAMAGE);
            }
            int newTime = time.get(i) - 1;
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
        return Geometry.rotateCenter(new Rectangle2D.Double(s.x, s.y, s.getWidth(), s.getHeight()), s.angle)[0];
    }

    private Line2D[] getHitBox(Swimmer s) {
        return Geometry.rotateCenter(new Rectangle2D.Double(s.x - s.getWidth() / 2, s.y - s.getHeight() / 2, s.getWidth(), s.getHeight()), s.angle);
    }
}
