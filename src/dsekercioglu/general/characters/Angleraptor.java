package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import dsekercioglu.general.control.StraightAttackControl;
import static dsekercioglu.general.characters.Ability.BLEED;
import static dsekercioglu.general.characters.Ability.GRAB;
import static dsekercioglu.general.characters.Ability.HOLD;
import static dsekercioglu.general.characters.Ability.KNOCKBACK;
import static dsekercioglu.general.characters.Ability.STICK;
import static dsekercioglu.general.characters.Animal.ANGLERAPTOR;
import dsekercioglu.general.control.AmbushControl;
import dsekercioglu.server.Environment;
import processing.core.PApplet;

public class Angleraptor extends Swimmer {

    Environment e;

    public Angleraptor(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        control = new AmbushControl(this, e);
        this.x = x;
        this.y = y;
        this.length = ANGLERAPTOR_LENGTH;
        this.weight = ANGLERAPTOR_WEIGHT;
        this.velocity = ANGLERAPTOR_SPEED;
        this.passiveAbilityPower = ANGLERAPTOR_PASSIVE_ABILITY;
        this.maxEnergy = ANGLERAPTOR_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.regen = ANGLERAPTOR_HEALTH_REGEN;
        this.energyIncrease = ANGLERAPTOR_ENERGY_INCREASE;
        this.maxTurn = ANGLERAPTOR_TURN;
        this.health = ANGLERAPTOR_MAX_HEALTH;
        this.maxHealth = ANGLERAPTOR_MAX_HEALTH;
        this.damage = ANGLERAPTOR_DAMAGE;
        this.abilityTime = ANGLERAPTOR_ABILITY_TIME;
        this.boostTime = ANGLERAPTOR_BOOST_TIME;
        this.ability1 = GRAB;
        this.ability2 = HOLD;
        this.ability3 = STICK;

        this.type = ANGLERAPTOR;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 1500, 0, 0, false);
        energyTime--;
        if (energyTime <= 0) {
            velocity = ANGLERAPTOR_SPEED;
            if (control.stop()) {
                velocity = 0;
                hiding = true;
            }
        } else {
            velocity = ANGLERAPTOR_SPEED * 20;
            hiding = false;
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
        return (int) ANGLERAPTOR_LENGTH;
    }

    @Override
    public int getHeight() {
        return 121;
    }
}
