package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import processing.core.PApplet;
import static dsekercioglu.general.characters.Ability.TAKE_DOWN;
import static dsekercioglu.general.characters.Animal.GREENLAND_SHARK;
import dsekercioglu.server.Environment;

public class GreenlandShark extends Swimmer {

    public GreenlandShark(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        this.x = x;
        this.y = y;
        this.length = GREENLAND_SHARK_LENGTH;
        this.weight = GREENLAND_SHARK_WEIGHT;
        this.velocity = GREENLAND_SHARK_SPEED;
        this.passiveAbilityPower = GREENLAND_SHARK_PASSIVE_ABILITY;
        this.maxEnergy = GREENLAND_SHARK_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.regen = GREENLAND_SHARK_HEALTH_REGEN;
        this.energyIncrease = GREENLAND_SHARK_ENERGY_INCREASE;
        this.maxTurn = GREENLAND_SHARK_TURN;
        this.health = GREENLAND_SHARK_MAX_HEALTH;
        this.maxHealth = GREENLAND_SHARK_MAX_HEALTH;
        this.damage = GREENLAND_SHARK_DAMAGE;
        this.abilityTime = GREENLAND_SHARK_ABILITY_TIME;
        this.boostTime = GREENLAND_SHARK_BOOST_TIME;
        this.armor = GREENLAND_SHARK_ARMOR;
        this.armorPiercing = GREENLAND_SHARK_ARMOR_PIERCING;
        this.ability1 = TAKE_DOWN;

        this.type = GREENLAND_SHARK;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 1500, mouseX, mouseY, mousePressed);
        energyTime--;
        if (energyTime <= 0) {
            velocity = GREENLAND_SHARK_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = GREENLAND_SHARK_SPEED * 1.5F;
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
        return (int) GREENLAND_SHARK_LENGTH;
    }

    @Override
    public int getHeight() {
        return 47;
    }

}
