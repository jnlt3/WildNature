package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.HOLD;
import static dsekercioglu.general.characters.Animal.CROCODILE;
import java.awt.geom.Point2D;
import processing.core.PApplet;

public class Crocodile extends Swimmer {

    public Crocodile(String name, float x, float y, PApplet p) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = CROCODILE_LENGTH;
        this.weight = CROCODILE_WEIGHT;
        this.velocity = CROCODILE_SPEED;
        this.passiveAbilityPower = CROCODILE_PASSIVE_ABILITY;
        this.maxEnergy = CROCODILE_MAX_ENERGY;
        this.regen = CROCODILE_HEALTH_REGEN;
        this.energy = this.maxEnergy;
        this.energyIncrease = CROCODILE_ENERGY_INCREASE;
        this.maxTurn = CROCODILE_TURN;
        this.health = CROCODILE_MAX_HEALTH;
        this.maxHealth = CROCODILE_MAX_HEALTH;
        this.damage = CROCODILE_DAMAGE;
        this.abilityTime = CROCODILE_ABILITY_TIME;
        this.boostTime = CROCODILE_BOOST_TIME;
        this.ability = HOLD;

        this.type = CROCODILE;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        energyTime--;
        if (energyTime <= 0) {
            velocity = CROCODILE_SPEED;
            if (Point2D.distance(600, 300, mouseX, mouseY) < 100) {
                velocity = 0;
                hiding = true;
            } else {
                hiding = false;
            }
        } else {
            velocity = CROCODILE_SPEED * 10;
        }
        this.move(velocity, Math.atan2(mouseY - 300, mouseX - 600));
        if (mousePressed && energy >= 1 && energyTime <= 0) {
            energy -= 1;
            energyTime = boostTime;
            System.out.println(energy);
        }
        energy = Math.min(energy + energyIncrease, maxEnergy);
    }

    @Override
    public int getWidth() {
        return (int) CROCODILE_LENGTH;
    }

    @Override
    public int getHeight() {
        return 59;
    }

}
