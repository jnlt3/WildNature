package dsekercioglu.general.characters;

import dsekercioglu.general.characters.Swimmer;
import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.KNOCKBACK;
import static dsekercioglu.general.characters.Animal.SHARK;
import dsekercioglu.server.Environment;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import processing.core.PApplet;

public class SharkAI extends Swimmer {

    Environment e;
    private final float MAX_DIST = 100;
    private final int ANGLE_NUM = 36;
    double targetAngle = 0;

    public SharkAI(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = SHARK_LENGTH;
        this.weight = SHARK_WEIGHT;
        this.velocity = SHARK_SPEED;
        this.passiveAbilityPower = SHARK_PASSIVE_ABILITY;
        this.maxEnergy = SHARK_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.regen = SHARK_HEALTH_REGEN;
        this.energyIncrease = SHARK_ENERGY_INCREASE;
        this.maxTurn = SHARK_TURN;
        this.health = SHARK_MAX_HEALTH;
        this.maxHealth = SHARK_MAX_HEALTH;
        this.damage = SHARK_DAMAGE;
        this.abilityTime = SHARK_ABILITY_TIME;
        this.boostTime = SHARK_BOOST_TIME;
        this.ability = KNOCKBACK;

        this.type = SHARK;

        this.e = e;
        targetAngle = Math.random() * 2 * Math.PI;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        energyTime--;
        if (energyTime <= 0) {
            velocity = SHARK_SPEED;
//            if (Point2D.distance(600, 300, mouseX, mouseY) < 100) {
//                velocity = 0;
//            }
        } else {
            velocity = SHARK_SPEED * 5;
        }
        ArrayList<Pair<Double, Point2D.Double>> targetPoints = new ArrayList<>();
        int seen = 0;
        for (int i = 0; i < ANGLE_NUM; i++) {
            double moveAngle = (double) i / ANGLE_NUM * Math.PI * 2;
            for (int j = 10; j <= MAX_DIST; j += 10) {
                Point2D.Double point = new Point2D.Double(x + Math.cos(moveAngle) * j, y + Math.sin(moveAngle) * j);
                Double danger = 0.0;
                for (int k = 0; k < e.characters.size(); k++) {
                    Swimmer s = e.characters.get(k);
                    if (!(s instanceof SharkAI)) {
                        if (!s.hiding && blind <= 0 && Point2D.distance(x, y, s.x, s.y) < 1500) {
                            danger += Math.max((this.health * this.damage - s.health * s.damage), 1) * point.distance(s.x, s.y);
                            seen++;
                        }
                    }
                }
                targetPoints.add(new Pair(danger, point));
            }
        }
        if (seen == 0) {
            targetAngle = Math.random() * 2 * Math.PI;
            this.move(velocity, angle);
        } else {
            Collections.sort(targetPoints);
            Pair<Double, Point2D.Double> best = targetPoints.get((int) (Math.random() * 5));
            this.move(velocity, Math.atan2(best.value.y - y, best.value.x - x));
        }
        mousePressed = true;
        if (mousePressed && energy >= 1 && energyTime <= 0) {
            energy -= 1;
            energyTime = boostTime;
        }
        energy = Math.min(energy + energyIncrease, maxEnergy);
    }

    @Override
    public int getWidth() {
        return (int) SHARK_LENGTH;
    }

    @Override
    public int getHeight() {
        return 62;
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
