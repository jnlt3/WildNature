package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.SHOCK;
import static dsekercioglu.general.characters.Animal.ELECTRIC_MARLIN;
import java.awt.geom.Point2D;
import processing.core.PApplet;

public class ElectricMarlin extends Swimmer {

    public ElectricMarlin(String name, float x, float y, PApplet p) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = ELECTRIC_MARLIN_LENGTH;
        this.weight = ELECTRIC_MARLIN_WEIGHT;
        this.velocity = ELECTRIC_MARLIN_SPEED;
        this.passiveAbilityPower = ELECTRIC_MARLIN_PASSIVE_ABILITY;
        this.maxEnergy = ELECTRIC_MARLIN_MAX_ENERGY;
        this.regen = ELECTRIC_MARLIN_HEALTH_REGEN;
        this.energy = this.maxEnergy;
        this.energyIncrease = ELECTRIC_MARLIN_ENERGY_INCREASE;
        this.maxTurn = ELECTRIC_MARLIN_TURN;
        this.health = ELECTRIC_MARLIN_MAX_HEALTH;
        this.maxHealth = ELECTRIC_MARLIN_MAX_HEALTH;
        this.damage = ELECTRIC_MARLIN_DAMAGE;
        this.abilityTime = ELECTRIC_MARLIN_ABILITY_TIME;
        this.boostTime = ELECTRIC_MARLIN_BOOST_TIME;
        this.ability = SHOCK;

        this.type = ELECTRIC_MARLIN;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        energyTime--;
        if (energyTime <= 0) {
            velocity = ELECTRIC_MARLIN_SPEED;
            if (Point2D.distance(600, 300, mouseX, mouseY) < 100) {
                velocity = 0;
            }
        } else {
            velocity = ELECTRIC_MARLIN_SPEED * 5;
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
        return (int) ELECTRIC_MARLIN_LENGTH;
    }

    @Override
    public int getHeight() {
        return 49;
    }

}