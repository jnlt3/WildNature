package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.BLEED;
import static dsekercioglu.general.characters.Ability.HORN;
import static dsekercioglu.general.characters.Animal.MARLIN;
import java.awt.geom.Point2D;
import processing.core.PApplet;
import static dsekercioglu.general.characters.Ability.DRAIN_HIT;

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
        this.regen = MARLIN_HEALTH_REGEN;
        this.energy = this.maxEnergy;
        this.energyIncrease = MARLIN_ENERGY_INCREASE;
        this.maxTurn = MARLIN_TURN;
        this.health = MARLIN_MAX_HEALTH;
        this.maxHealth = MARLIN_MAX_HEALTH;
        this.damage = MARLIN_DAMAGE;
        this.abilityTime = MARLIN_ABILITY_TIME;
        this.boostTime = MARLIN_BOOST_TIME;
        this.ability1 = BLEED;
        this.ability2 = DRAIN_HIT;
        this.ability3 = HORN;

        this.type = MARLIN;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        energyTime--;
        if (energyTime <= 0) {
            velocity = MARLIN_SPEED;
            if (Point2D.distance(600, 300, mouseX, mouseY) < 100) {
                velocity = 0;
            }
        } else {
            velocity = MARLIN_SPEED * 5;
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
        return (int) MARLIN_LENGTH;
    }

    @Override
    public int getHeight() {
        return 44;
    }
}
