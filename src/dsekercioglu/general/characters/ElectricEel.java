package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.SHOCK;
import static dsekercioglu.general.characters.Swimmer.drawCostume;
import static dsekercioglu.general.characters.Swimmer.pa;
import java.awt.geom.Point2D;
import processing.core.PApplet;

public class ElectricEel extends Swimmer {

    public ElectricEel(String name, float x, float y, PApplet p) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = ELECTRIC_EEL_LENGTH;
        this.weight = ELECTRIC_EEL_WEIGHT;
        this.velocity = ELECTRIC_EEL_SPEED;
        this.passiveAbilityPower = ELECTRIC_EEL_PASSIVE_ABILITY;
        this.maxEnergy = ELECTRIC_EEL_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.energyIncrease = ELECTRIC_EEL_ENERGY_INCREASE;
        this.maxTurn = ELECTRIC_EEL_TURN;
        this.health = ELECTRIC_EEL_MAX_HEALTH;
        this.maxHealth = ELECTRIC_EEL_MAX_HEALTH;
        this.damage = ELECTRIC_EEL_DAMAGE;
        this.abilityTime = ELECTRIC_EEL_ABILITY_TIME;
        this.boostTime = ELECTRIC_EEL_BOOST_TIME;
        this.ability = SHOCK;

        this.type = "ElectricEel";
    }
    
    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        energyTime--;
        if (energyTime <= 0) {
            velocity = ELECTRIC_EEL_SPEED;
            if (Point2D.distance(600, 300, mouseX, mouseY) < 100) {
                velocity = 0;
            }
        } else {
            velocity = ELECTRIC_EEL_SPEED * 6;
        }
        this.move(velocity, Math.atan2(mouseY - 300, mouseX - 600));
        if (mousePressed && energy >= 1 && energyTime <= 0) {
            energy -= 1;
            energyTime = boostTime;
        }
        energy = Math.min(energy + energyIncrease, maxEnergy);
        this.health = Math.min(this.health + ELECTRIC_EEL_HEALTH_REGEN, this.maxHealth);
    }

    @Override
    public int getWidth() {
        return (int) ELECTRIC_EEL_LENGTH;
    }

    @Override
    public int getHeight() {
        return 18;
    }
}
