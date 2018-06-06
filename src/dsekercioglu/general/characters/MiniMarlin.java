package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.BLEED;
import static dsekercioglu.general.characters.Animal.MINI_MARLIN;
import dsekercioglu.general.control.BackTrackControl;
import dsekercioglu.server.Environment;
import processing.core.PApplet;

public class MiniMarlin extends Swimmer {

    Environment e;

    public MiniMarlin(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        control = new BackTrackControl(this, e);
        this.x = x;
        this.y = y;
        this.length = MINI_MARLIN_LENGTH;
        this.weight = MINI_MARLIN_WEIGHT;
        this.velocity = MINI_MARLIN_SPEED;
        this.passiveAbilityPower = MINI_MARLIN_PASSIVE_ABILITY;
        this.maxEnergy = MINI_MARLIN_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.regen = MINI_MARLIN_HEALTH_REGEN;
        this.energyIncrease = MINI_MARLIN_ENERGY_INCREASE;
        this.maxTurn = MINI_MARLIN_TURN;
        this.health = MINI_MARLIN_MAX_HEALTH;
        this.maxHealth = MINI_MARLIN_MAX_HEALTH;
        this.damage = MINI_MARLIN_DAMAGE;
        this.abilityTime = MINI_MARLIN_ABILITY_TIME;
        this.boostTime = MINI_MARLIN_BOOST_TIME;
        this.ability1 = BLEED;
        // = DRAIN_HIT;
        // = HORN;

        this.type = MINI_MARLIN;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 3000, 0, 0, false);
        energyTime--;
        if (energyTime <= 0) {
            velocity = MINI_MARLIN_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = MINI_MARLIN_SPEED * 5;
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
        return (int) MINI_MARLIN_LENGTH;
    }

    @Override
    public int getHeight() {
        return 21;
    }
}
