package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.BIRTH;
import static dsekercioglu.general.characters.Ability.BLEED;
import static dsekercioglu.general.characters.Ability.DRAIN_HIT;
import dsekercioglu.general.control.StraightAttackControl;
import static dsekercioglu.general.characters.Ability.HOLD;
import static dsekercioglu.general.characters.Ability.HORN;
import static dsekercioglu.general.characters.Ability.KNOCKBACK;
import static dsekercioglu.general.characters.Animal.MARLINIUM;
import dsekercioglu.general.control.BackTrackControl;
import dsekercioglu.server.Environment;
import processing.core.PApplet;

public class Marlinium extends Swimmer {

    Environment e;

    public Marlinium(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        control = new BackTrackControl(this, e);
        this.x = x;
        this.y = y;
        this.length = MARLINIUM_LENGTH;
        this.weight = MARLINIUM_WEIGHT;
        this.velocity = MARLINIUM_SPEED;
        this.passiveAbilityPower = MARLINIUM_PASSIVE_ABILITY;
        this.maxEnergy = MARLINIUM_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.regen = MARLINIUM_HEALTH_REGEN;
        this.energyIncrease = MARLINIUM_ENERGY_INCREASE;
        this.maxTurn = MARLINIUM_TURN;
        this.health = MARLINIUM_MAX_HEALTH;
        this.maxHealth = MARLINIUM_MAX_HEALTH;
        this.damage = MARLINIUM_DAMAGE;
        this.abilityTime = MARLINIUM_ABILITY_TIME;
        this.boostTime = MARLINIUM_BOOST_TIME;
        this.ability1 = BLEED;
        this.ability2 = BIRTH;
        this.ability3 = HORN;

        this.type = MARLINIUM;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 3000, 0, 0, false);
        energyTime--;
        if (energyTime <= 0) {
            velocity = MARLINIUM_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = MARLINIUM_SPEED * 5;
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
        return (int) MARLINIUM_LENGTH;
    }

    @Override
    public int getHeight() {
        return 44;
    }
}
