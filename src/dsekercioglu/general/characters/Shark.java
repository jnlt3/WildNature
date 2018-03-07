package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.GRAB;
import static dsekercioglu.general.characters.Swimmer.drawCostume;
import static dsekercioglu.general.characters.Swimmer.pa;
import java.awt.geom.Point2D;
import processing.core.PApplet;

public class Shark extends Swimmer {

    public Shark(String name, float x, float y, PApplet p) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = SHARK_LENGTH;
        this.weight = SHARK_WEIGHT;
        this.velocity = SHARK_SPEED;
        this.passiveAbilityPower = SHARK_PASSIVE_ABILITY;
        this.maxEnergy = SHARK_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.energyIncrease = SHARK_ENERGY_INCREASE;
        this.maxTurn = SHARK_TURN;
        this.health = SHARK_MAX_HEALTH;
        this.maxHealth = SHARK_MAX_HEALTH;
        this.damage = SHARK_DAMAGE;
        this.abilityTime = SHARK_ABILITY_TIME;
        this.boostTime = SHARK_BOOST_TIME;
        this.ability = GRAB;

        this.type = "Shark";
    }
    
    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        double targetVelocity;
        energyTime--;
        if (energyTime <= 0) {
            targetVelocity = SHARK_SPEED;
            if (Point2D.distance(600, 300, mouseX, mouseY) < 100) {
                targetVelocity = 0;
            }
        } else {
            targetVelocity = SHARK_SPEED * 10;
        }
        this.move(targetVelocity, Math.atan2(mouseY - 300, mouseX - 600));
        if (mousePressed && energy >= 1 && energyTime <= 0) {
            energy -= 1;
            energyTime = boostTime;
        }
        energy = Math.min(energy + energyIncrease, maxEnergy);
        this.health = Math.min(this.health + SHARK_HEALTH_REGEN, this.maxHealth);
    }

    @Override
    public int getWidth() {
        return (int) SHARK_LENGTH;
    }

    @Override
    public int getHeight() {
        return 62;
    }

}