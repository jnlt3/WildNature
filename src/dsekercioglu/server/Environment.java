package dsekercioglu.server;

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
        ArrayList<Swimmer> knockback = new ArrayList<>();
        for (int i = 0; i < characters.size(); i++) {
            Swimmer p1 = characters.get(i);
            Line2D r1 = getHitter(p1);
            for (int j = 0; j < characters.size(); j++) {
                Swimmer p2 = characters.get(j);
                Line2D[] r2 = getHitBox(p2);
                if (!p1.equals(p2) && Geometry.intersects(new Line2D[] {r1}, r2)) {
                    p2.hit(p1.damage);
                    knockback.add(p2);
                }
            }
        }
    }

    private Line2D getHitter(Swimmer s) {
        return Geometry.rotateCenter(new Rectangle2D.Double(s.x, s.y, s.getWidth(), s.getHeight()), s.angle)[0];
    }

    private Line2D[] getHitBox(Swimmer s) {
        return Geometry.rotateCenter(new Rectangle2D.Double(s.x, s.y, s.getWidth(), s.getHeight()), s.angle);
    }
}
