package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.BLEED;
import static dsekercioglu.general.characters.Animal.BLACK_MARLIN;
import java.awt.geom.Point2D;
import processing.core.PApplet;

public class BlackMarlin extends Swimmer {

    public BlackMarlin(String name, float x, float y, PApplet p) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = BLACK_MARLIN_LENGTH;
        this.weight = BLACK_MARLIN_WEIGHT;
        this.velocity = BLACK_MARLIN_SPEED;
        this.passiveAbilityPower = BLACK_MARLIN_PASSIVE_ABILITY;
        this.maxEnergy = BLACK_MARLIN_MAX_ENERGY;
        this.regen = BLACK_MARLIN_HEALTH_REGEN;
        this.energy = this.maxEnergy;
        this.energyIncrease = BLACK_MARLIN_ENERGY_INCREASE;
        this.maxTurn = BLACK_MARLIN_TURN;
        this.health = BLACK_MARLIN_MAX_HEALTH;
        this.maxHealth = BLACK_MARLIN_MAX_HEALTH;
        this.damage = BLACK_MARLIN_DAMAGE;
        this.abilityTime = BLACK_MARLIN_ABILITY_TIME;
        this.boostTime = BLACK_MARLIN_BOOST_TIME;
        this.ability = BLEED;

        this.type = BLACK_MARLIN;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        energyTime--;
        if (energyTime <= 0) {
            velocity = BLACK_MARLIN_SPEED;
            if (Point2D.distance(600, 300, mouseX, mouseY) < 100) {
                velocity = 0;
            }
        } else {
            velocity = BLACK_MARLIN_SPEED * 5;
        }
        this.move(velocity, Math.atan2(mouseY - 300, mouseX - 600));
        if (mousePressed && energy >= 1 && energyTime <= 0) {
            energy -= 1;
            energyTime = boostTime;
        }
        energy = Math.min(energy + energyIncrease, maxEnergy);
    }

    @Override
    public int getWidth() {
        return (int) BLACK_MARLIN_LENGTH;
    }

    @Override
    public int getHeight() {
        return 49;
    }

}
