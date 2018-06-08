package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.BLEEDING_KNOCKBACK;
import static dsekercioglu.general.characters.Ability.SHORT_GRAB;
import processing.core.PApplet;
import static dsekercioglu.general.characters.Animal.MAKO_SHARK;
import dsekercioglu.server.Environment;

public class MakoShark extends Swimmer {

    public MakoShark(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        this.x = x;
        this.y = y;
        knockbackPower = 37F;
        this.length = MAKO_SHARK_LENGTH;
        this.weight = MAKO_SHARK_WEIGHT;
        this.velocity = MAKO_SHARK_SPEED;
        this.passiveAbilityPower = MAKO_SHARK_PASSIVE_ABILITY;
        this.maxEnergy = MAKO_SHARK_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.regen = MAKO_SHARK_HEALTH_REGEN;
        this.energyIncrease = MAKO_SHARK_ENERGY_INCREASE;
        this.maxTurn = MAKO_SHARK_TURN;
        this.health = MAKO_SHARK_MAX_HEALTH;
        this.maxHealth = MAKO_SHARK_MAX_HEALTH;
        this.damage = MAKO_SHARK_DAMAGE;
        this.abilityTime = MAKO_SHARK_ABILITY_TIME;
        this.boostTime = MAKO_SHARK_BOOST_TIME;
        this.armor = MAKO_SHARK_ARMOR;
        this.armorPiercing = MAKO_SHARK_ARMOR_PIERCING;
        this.ability1 = BLEEDING_KNOCKBACK;

        this.type = MAKO_SHARK;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 1500, mouseX, mouseY, mousePressed);
        energyTime--;
        if (energyTime <= 0) {
            velocity = MAKO_SHARK_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = MAKO_SHARK_SPEED * 5;
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
        return (int) MAKO_SHARK_LENGTH;
    }

    @Override
    public int getHeight() {
        return 34;
    }

}
