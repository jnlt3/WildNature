package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.STICK;
import static dsekercioglu.general.characters.Swimmer.drawCostume;
import static dsekercioglu.general.characters.Swimmer.pa;
import java.awt.geom.Point2D;
import processing.core.PApplet;

public class Barracuda extends Swimmer {

    public Barracuda(String name, float x, float y, PApplet p) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = BARRACUDA_LENGTH;
        this.weight = BARRACUDA_WEIGHT;
        this.velocity = BARRACUDA_SPEED;
        this.passiveAbilityPower = BARRACUDA_PASSIVE_ABILITY;
        this.maxEnergy = BARRACUDA_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.energyIncrease = BARRACUDA_ENERGY_INCREASE;
        this.maxTurn = BARRACUDA_TURN;
        this.health = BARRACUDA_MAX_HEALTH;
        this.maxHealth = BARRACUDA_MAX_HEALTH;
        this.damage = BARRACUDA_DAMAGE;
        this.abilityTime = BARRACUDA_ABILITY_TIME;
        this.boostTime = BARRACUDA_BOOST_TIME;
        this.ability = STICK;

        this.type = "Barracuda";
    }
    
    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        double targetVelocity;
        energyTime--;
        if (energyTime <= 0) {
            targetVelocity = BARRACUDA_SPEED;
            if (Point2D.distance(600, 300, mouseX, mouseY) < 100) {
                targetVelocity = 0;
            }
        } else {
            targetVelocity = BARRACUDA_SPEED * 5;
        }
        this.move(targetVelocity, Math.atan2(mouseY - 300, mouseX - 600));
        if (mousePressed && energy >= 1 && energyTime <= 0) {
            energy -= 1;
            energyTime = boostTime;
        }
        energy = Math.min(energy + energyIncrease, maxEnergy);
        this.health = Math.min(this.health + BARRACUDA_HEALTH_REGEN, this.maxHealth);
    }

    @Override
    public int getWidth() {
        return (int) BARRACUDA_LENGTH;
    }

    @Override
    public int getHeight() {
        return 26;
    }
}
