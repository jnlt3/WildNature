package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.DAMAGE_BOOST;
import static dsekercioglu.general.characters.Ability.INKSPILL;
import static dsekercioglu.general.characters.Animal.COLOSSAL_SQUID;
import java.awt.geom.Point2D;
import processing.core.PApplet;
import static dsekercioglu.general.characters.Ability.DRAIN_HIT;

public class ColossalSquid extends Swimmer {

    public ColossalSquid(String name, float x, float y, PApplet p) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = COLOSSAL_SQUID_LENGTH;
        this.weight = COLOSSAL_SQUID_WEIGHT;
        this.velocity = COLOSSAL_SQUID_SPEED;
        this.passiveAbilityPower = COLOSSAL_SQUID_PASSIVE_ABILITY;
        this.maxEnergy = COLOSSAL_SQUID_MAX_ENERGY;
        this.regen = COLOSSAL_SQUID_HEALTH_REGEN;
        this.energy = this.maxEnergy;
        this.energyIncrease = COLOSSAL_SQUID_ENERGY_INCREASE;
        this.maxTurn = COLOSSAL_SQUID_TURN;
        this.health = COLOSSAL_SQUID_MAX_HEALTH;
        this.maxHealth = COLOSSAL_SQUID_MAX_HEALTH;
        this.damage = COLOSSAL_SQUID_DAMAGE;
        this.abilityTime = COLOSSAL_SQUID_ABILITY_TIME;
        this.boostTime = COLOSSAL_SQUID_BOOST_TIME;
        this.ability1 = INKSPILL;
        this.ability2 = DRAIN_HIT;
        this.ability3 = DAMAGE_BOOST;

        this.type = COLOSSAL_SQUID;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        energyTime--;
        if (energyTime <= 0) {
            velocity = COLOSSAL_SQUID_SPEED;
            if (Point2D.distance(600, 300, mouseX, mouseY) < 100) {
                velocity = 0;
            }
        } else {
            velocity = COLOSSAL_SQUID_SPEED * 5;
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
        return (int) COLOSSAL_SQUID_LENGTH;
    }

    @Override
    public int getHeight() {
        return 56;
    }

}
