package dsekercioglu.general.control;

import dsekercioglu.general.characters.Ghost;
import dsekercioglu.general.characters.Swimmer;
import static dsekercioglu.general.characters.Team.INDEPENDENT;
import dsekercioglu.server.Environment;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;

public class AmbushControl extends Control {

    final int ANGLE_NUM = 36;
    final float MAX_DIST = 100;

    int timeSinceHit;

    Environment env;

    public AmbushControl(Swimmer owner, Environment e) {
        super(owner);
        this.env = e;
    }

    @Override
    public void riskControl(ArrayList<Swimmer> characters, float bloodRange, float sightRange, int mouseX, int mouseY, boolean mousePressed) {
        ArrayList<Pair<Double, Point2D.Double>> targetPoints = new ArrayList<>();
        int seen = 0;
        double minDistance = Double.POSITIVE_INFINITY;
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
                            if (distance < minDistance) {
                                minDistance = distance;
                            }
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
        if (minDistance < 1000 && minDistance > 300 && timeSinceHit <= 0) {
            stop = true;
        } else {
            stop = false;
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
        timeSinceHit--;
    }

    private double danger(Point2D.Double point, Swimmer s) {
        return Math.max((owner.health * owner.damage - s.health * s.damage), 1) * point.distance(s.x, s.y);
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
