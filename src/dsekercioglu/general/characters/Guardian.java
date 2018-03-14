package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.POISON;
import static dsekercioglu.general.characters.Animal.GUARDIAN;
import java.awt.geom.Point2D;
import processing.core.PApplet;
import static dsekercioglu.general.characters.Ability.PRISON;

public class Guardian extends Swimmer {
    
    public float mouseX;
    public float mouseY;

    public Guardian(String name, float x, float y, PApplet p) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = GUARDIAN_LENGTH;
        this.weight = GUARDIAN_WEIGHT;
        this.velocity = GUARDIAN_SPEED;
        this.passiveAbilityPower = GUARDIAN_PASSIVE_ABILITY;
        this.maxEnergy = GUARDIAN_MAX_ENERGY;
        this.regen = GUARDIAN_HEALTH_REGEN;
        this.energy = this.maxEnergy;
        this.energyIncrease = GUARDIAN_ENERGY_INCREASE;
        this.maxTurn = GUARDIAN_TURN;
        this.health = GUARDIAN_MAX_HEALTH;
        this.maxHealth = GUARDIAN_MAX_HEALTH;
        this.damage = GUARDIAN_DAMAGE;
        this.abilityTime = GUARDIAN_ABILITY_TIME;
        this.boostTime = GUARDIAN_BOOST_TIME;
        this.ability = PRISON;

        this.type = GUARDIAN;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        this.mouseX = x + mouseX - 600;
        this.mouseY = y + mouseY - 300;
        energyTime--;
        if (energyTime <= 0) {
            velocity = GUARDIAN_SPEED;
            if (Point2D.distance(600, 300, mouseX, mouseY) < 100) {
                velocity = 0;
            }
        } else {
            this.regen();
            this.regen();
            this.regen();
        }
        this.move(velocity, Math.atan2(mouseY - 300, mouseX - 600));
         if (mousePressed && energy >= 1 && energyTime <= 0) {
            energy -= 1;
            energyTime = boostTime;
        }
        energy = Math.min(energy + energyIncrease, maxEnergy);
    }

    @Override
    public int getWidth() {
        return (int) GUARDIAN_LENGTH;
    }

    @Override
    public int getHeight() {
        return 86;
    }
}
