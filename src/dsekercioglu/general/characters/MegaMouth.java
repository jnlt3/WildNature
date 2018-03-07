package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.REGEN_TIME;
import static dsekercioglu.general.characters.Swimmer.drawCostume;
import static dsekercioglu.general.characters.Swimmer.pa;
import java.awt.geom.Point2D;
import processing.core.PApplet;

public class MegaMouth extends Swimmer {

    public MegaMouth(String name, float x, float y, PApplet p) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = MEGA_MOUTH_SHARK_LENGTH;
        this.weight = MEGA_MOUTH_SHARK_WEIGHT;
        this.velocity = MEGA_MOUTH_SHARK_SPEED;
        this.passiveAbilityPower = MEGA_MOUTH_SHARK_PASSIVE_ABILITY;
        this.maxEnergy = MEGA_MOUTH_SHARK_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.energyIncrease = MEGA_MOUTH_SHARK_ENERGY_INCREASE;
        this.maxTurn = MEGA_MOUTH_SHARK_TURN;
        this.health = MEGA_MOUTH_SHARK_MAX_HEALTH;
        this.maxHealth = MEGA_MOUTH_SHARK_MAX_HEALTH;
        this.damage = MEGA_MOUTH_SHARK_DAMAGE;
        this.abilityTime = MEGA_MOUTH_SHARK_ABILITY_TIME;
        this.boostTime = MEGA_MOUTH_SHARK_BOOST_TIME;
        this.ability = REGEN_TIME;

        this.type = "MegaMouth";
    }
    
    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        double targetVelocity;
        energyTime--;
        if (energyTime <= 0) {
            targetVelocity = MEGA_MOUTH_SHARK_SPEED;
            if (Point2D.distance(600, 300, mouseX, mouseY) < 100) {
                targetVelocity = 0;
            }
        } else {
            targetVelocity = MEGA_MOUTH_SHARK_SPEED * 10;
        }
        this.move(targetVelocity, Math.atan2(mouseY - 300, mouseX - 600));
        if (mousePressed && energy >= 1 && energyTime <= 0) {
            energy -= 1;
            energyTime = boostTime;
        }
        energy = Math.min(energy + energyIncrease, maxEnergy);
        this.health = Math.min(this.health + MEGA_MOUTH_SHARK_HEALTH_REGEN, this.maxHealth);
    }

    @Override
    public int getWidth() {
        return (int) MEGA_MOUTH_SHARK_LENGTH;
    }

    @Override
    public int getHeight() {
        return 72;
    }

}
