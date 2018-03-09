package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.GRAB;
import static dsekercioglu.general.characters.Swimmer.drawCostume;
import static dsekercioglu.general.characters.Swimmer.pa;
import java.awt.geom.Point2D;
import processing.core.PApplet;

public class Orca extends Swimmer {

    public Orca(String name, float x, float y, PApplet p) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = ORCA_LENGTH;
        this.weight = ORCA_WEIGHT;
        this.velocity = ORCA_SPEED;
        this.passiveAbilityPower = ORCA_PASSIVE_ABILITY;
        this.maxEnergy = ORCA_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.energyIncrease = ORCA_ENERGY_INCREASE;
        this.maxTurn = ORCA_TURN;
        this.health = ORCA_MAX_HEALTH;
        this.maxHealth = ORCA_MAX_HEALTH;
        this.damage = ORCA_DAMAGE;
        this.abilityTime = ORCA_ABILITY_TIME;
        this.boostTime = ORCA_BOOST_TIME;
        this.ability = GRAB;

        this.type = "Orca";
    }
    
    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        energyTime--;
        if (energyTime <= 0) {
            velocity = ORCA_SPEED;
            if (Point2D.distance(600, 300, mouseX, mouseY) < 100) {
                velocity = 0;
            }
        } else {
            velocity = ORCA_SPEED * 5;
        }
        this.move(velocity, Math.atan2(mouseY - 300, mouseX - 600));
        if (mousePressed && energy >= 1 && energyTime <= 0) {
            energy -= 1;
            energyTime = boostTime;
        }
        energy = Math.min(energy + energyIncrease, maxEnergy);
        this.health = Math.min(this.health + ORCA_HEALTH_REGEN, this.maxHealth);
    }

    @Override
    public int getWidth() {
        return (int) ORCA_LENGTH;
    }

    @Override
    public int getHeight() {
        return 62;
    }

}
