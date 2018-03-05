package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.BLEED;
import java.awt.geom.Point2D;
import processing.core.PApplet;

public class Marlin extends Swimmer {

    public Marlin(String name, float x, float y, PApplet p) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = MARLIN_LENGTH;
        this.weight = MARLIN_WEIGHT;
        this.velocity = MARLIN_SPEED;
        this.passiveAbilityPower = MARLIN_PASSIVE_ABILITY;
        this.maxEnergy = MARLIN_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.energyIncrease = MARLIN_ENERGY_INCREASE;
        this.maxTurn = MARLIN_TURN;
        this.health = MARLIN_MAX_HEALTH;
        this.maxHealth = MARLIN_MAX_HEALTH;
        this.damage = MARLIN_DAMAGE;
        this.abilityTime = MARLIN_ABILITY_TIME;
        this.boostTime = MARLIN_BOOST_TIME;
        this.ability = BLEED;

        this.type = "Marlin";
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        double targetVelocity;
        energyTime--;
        if (energyTime <= 0) {
            targetVelocity = MARLIN_SPEED;
            if (Point2D.distance(600, 300, mouseX, mouseY) < 100) {
                targetVelocity = 0;
            }
        } else {
            targetVelocity = MARLIN_SPEED * 5;
        }
        this.move(targetVelocity, Math.atan2(mouseY - 300, mouseX - 600));
         if (mousePressed && energy >= 1 && energyTime <= 0) {
            energy -= 1;
            energyTime = boostTime;
        }
        energy = Math.min(energy + energyIncrease, maxEnergy);
        this.health = Math.min(this.health + MARLIN_HEALTH_REGEN, this.maxHealth);
    }

    @Override
    public int getWidth() {
        return (int) MARLIN_LENGTH;
    }

    @Override
    public int getHeight() {
        return 44;
    }
}
