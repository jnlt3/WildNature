package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.SUPERBITE;
import static dsekercioglu.general.characters.Animal.HIPPO;
import java.awt.geom.Point2D;
import processing.core.PApplet;

public class Hippo extends Swimmer {

    public Hippo(String name, float x, float y, PApplet p) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = HIPPO_LENGTH;
        this.weight = HIPPO_WEIGHT;
        this.velocity = HIPPO_SPEED;
        this.passiveAbilityPower = HIPPO_PASSIVE_ABILITY;
        this.maxEnergy = HIPPO_MAX_ENERGY;
        this.regen = HIPPO_HEALTH_REGEN;
        this.energy = this.maxEnergy;
        this.energyIncrease = HIPPO_ENERGY_INCREASE;
        this.maxTurn = HIPPO_TURN;
        this.health = HIPPO_MAX_HEALTH;
        this.maxHealth = HIPPO_MAX_HEALTH;
        this.damage = HIPPO_DAMAGE;
        this.abilityTime = HIPPO_ABILITY_TIME;
        this.boostTime = HIPPO_BOOST_TIME;
        this.ability = SUPERBITE;

        this.type = HIPPO;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        energyTime--;
        velocity = HIPPO_SPEED;
        if (Point2D.distance(600, 300, mouseX, mouseY) < 100) {
            velocity = 0;
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
        return (int) HIPPO_LENGTH;
    }

    @Override
    public int getHeight() {
        return 65;
    }

}
