package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import dsekercioglu.general.control.StraightAttackControl;
import static dsekercioglu.general.characters.Ability.BLEED;
import static dsekercioglu.general.characters.Ability.HOLD;
import static dsekercioglu.general.characters.Ability.KNOCKBACK;
import static dsekercioglu.general.characters.Animal.SHARKODILE;
import dsekercioglu.server.Environment;
import processing.core.PApplet;

public class Sharkodile extends Swimmer {

    Environment e;

    public Sharkodile(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        control = new StraightAttackControl(this, e);
        this.x = x;
        this.y = y;
        this.length = SHARKODILE_LENGTH;
        this.weight = SHARKODILE_WEIGHT;
        this.velocity = SHARKODILE_SPEED;
        this.passiveAbilityPower = SHARKODILE_PASSIVE_ABILITY;
        this.maxEnergy = SHARKODILE_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.regen = SHARKODILE_HEALTH_REGEN;
        this.energyIncrease = SHARKODILE_ENERGY_INCREASE;
        this.maxTurn = SHARKODILE_TURN;
        this.health = SHARKODILE_MAX_HEALTH;
        this.maxHealth = SHARKODILE_MAX_HEALTH;
        this.damage = SHARKODILE_DAMAGE;
        this.abilityTime = SHARKODILE_ABILITY_TIME;
        this.boostTime = SHARKODILE_BOOST_TIME;
        this.armor = SHARKODILE_ARMOR;
        this.armorPiercing = SHARKODILE_ARMOR_PIERCING;
        this.ability1 = HOLD;

        this.type = SHARKODILE;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 6000, 1500, mouseX, mouseY, mousePressed);
        energyTime--;
        if (energyTime <= 0) {
            velocity = SHARKODILE_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = SHARKODILE_SPEED * 5;
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
        return (int) SHARKODILE_LENGTH;
    }

    @Override
    public int getHeight() {
        return 42;
    }
}
