package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.BLEED;
import static dsekercioglu.general.characters.Animal.MARLIN;
import processing.core.PApplet;
import dsekercioglu.server.Environment;

public class Marlin extends Swimmer {

    public Marlin(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
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
        // = DRAIN_HIT;
        // = HORN;

        this.type = MARLIN;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 2500, mouseX, mouseY, mousePressed);
        energyTime--;
        if (energyTime <= 0) {
            velocity = MARLIN_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = MARLIN_SPEED * 5;
        }
        this.move(velocity, control.moveAngle());
        if (control.mousePressed() && energy >= 1 && energyTime <= 0) {
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
        return 29;
    }
}
