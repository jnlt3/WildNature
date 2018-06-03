package dsekercioglu.general.control;

import dsekercioglu.general.characters.Ghost;
import dsekercioglu.general.characters.Swimmer;
import static dsekercioglu.general.characters.Team.INDEPENDENT;
import dsekercioglu.general.multiPlayer.ControlInfo;
import dsekercioglu.server.Environment;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

public class TwinControl extends Control {

    final int ANGLE_NUM = 36;
    final float MAX_DIST = 100;

    Environment env;

    Swimmer base;

    public TwinControl(Swimmer owner, Swimmer base, Environment e) {
        super(owner);
        this.env = e;
        this.base = base;
    }

    @Override
    public void riskControl(ArrayList<Swimmer> characters, float bloodRange, float sightRange, int mouseX, int mouseY, boolean mousePressed) {
        ArrayList<Pair<Double, Point2D.Double>> targetPoints = new ArrayList<>();
        int seen = 0;
        for (int i = 0; i < ANGLE_NUM; i++) {
            double moveAngle = (double) i / ANGLE_NUM * Math.PI * 2;
            for (int j = 10; j <= MAX_DIST; j += 10) {
                Point2D.Double point = new Point2D.Double(owner.x + Math.cos(moveAngle) * j, owner.y + Math.sin(moveAngle) * j);
                Double danger = 0.0;
                for (int k = 0; k < characters.size(); k++) {
                    Swimmer s = characters.get(k);
                    if (!(s instanceof Ghost)) {
                        if (!(s.team.equals(owner.team)) || s.team.equals(INDEPENDENT)) {
                            double distance = Point2D.distance(owner.x, owner.y, s.x, s.y);
                            if (owner.blind <= 0 && ((distance < sightRange && !s.hiding) || (distance < bloodRange && s.health != s.maxHealth))) {
                                if (Math.abs(Math.atan2(s.y - owner.y, s.x - owner.x) - owner.angle + Math.PI) % Math.PI < Math.PI / 9) {
                                    env.charAbilities.put(owner, owner.ability1);
                                }
                                danger += danger(point, s);
                                seen++;
                            }
                        }
                    }
                }
                targetPoints.add(new Pair(danger, point));
            }
        }
        if (seen == 0) {
            moveAngle = (float) (Math.random() * 2 * Math.PI);
            this.mousePressed = false;
        } else {
            Collections.sort(targetPoints);
            Pair<Double, Point2D.Double> best = targetPoints.get(0);
            moveAngle = (float) Math.atan2(best.value.y - owner.y, best.value.x - owner.x);
            this.mousePressed = true;
        }
    }

    private double danger(Point2D.Double point, Swimmer s) {
        double distToBase = Math.max(point.distance(base.x, base.y), 300) / 600;
        double ta = s.angle + Math.PI;
        double hwidth = s.getWidth() / 2;
        double tx = s.x + Math.cos(ta) * hwidth;
        double ty = s.y + Math.sin(ta) * hwidth;
        double distToTarget = point.distanceSq(tx, ty);
        tx = s.x - Math.cos(ta) * hwidth;
        ty = s.y - Math.sin(ta) * hwidth;
        double distToDanger = point.distance(tx, ty);
        return distToTarget * Math.pow(distToBase, 4) / distToDanger;
    }

    @Override
    public void ownerAttacked() {

    }

    @Override
    public void ownerHit() {

    }

    private class Pair<T extends Comparable, U> implements Comparable {

        T comparable;
        U value;

        public Pair(T comparable, U value) {
            this.comparable = comparable;
            this.value = value;
        }

        @Override
        public int compareTo(Object o) {
            return comparable.compareTo(((Pair) o).comparable);
        }

    }

}
