package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.DAMAGE_BOOST;
import static dsekercioglu.general.characters.Ability.POISON;
import static dsekercioglu.general.characters.Ability.SLOW_DOWN;
import static dsekercioglu.general.characters.Animal.DOOD_FISH;
import dsekercioglu.server.Environment;
import java.awt.geom.Point2D;
import processing.core.PApplet;

public class DoodFish extends Swimmer {

    public DoodFish(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        this.x = x;
        this.y = y;
        this.length = DOOD_FISH_LENGTH;
        this.weight = DOOD_FISH_WEIGHT;
        this.velocity = DOOD_FISH_SPEED;
        this.passiveAbilityPower = DOOD_FISH_PASSIVE_ABILITY;
        this.maxEnergy = DOOD_FISH_MAX_ENERGY;
        this.regen = DOOD_FISH_HEALTH_REGEN;
        this.energy = this.maxEnergy;
        this.energyIncrease = DOOD_FISH_ENERGY_INCREASE;
        this.maxTurn = DOOD_FISH_TURN;
        this.health = DOOD_FISH_MAX_HEALTH;
        this.maxHealth = DOOD_FISH_MAX_HEALTH;
        this.damage = DOOD_FISH_DAMAGE;
        this.abilityTime = DOOD_FISH_ABILITY_TIME;
        this.boostTime = DOOD_FISH_BOOST_TIME;
        this.ability1 = POISON;
        this.ability2 = SLOW_DOWN;
        this.ability3 = DAMAGE_BOOST;

        this.type = DOOD_FISH;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 15000,  mouseX, mouseY, mousePressed);
        energyTime--;
        if (energyTime <= 0) {
            velocity = DOOD_FISH_SPEED;
            if (control.stop()) {
                velocity = 0;
                hiding = true;
            } else {
                hiding = false;
            }
        } else {
            velocity = DOOD_FISH_SPEED * 1.5F;
            hiding = true;
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
        return (int) DOOD_FISH_LENGTH;
    }

    @Override
    public int getHeight() {
        return 86;
    }
}
