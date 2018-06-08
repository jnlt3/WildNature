package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.ELECTRIC_HORN;
import static dsekercioglu.general.characters.Ability.SHOCK;
import static dsekercioglu.general.characters.Animal.GHOST;
import static dsekercioglu.general.characters.Animal.GHOST;
import processing.core.PApplet;
import dsekercioglu.server.Environment;

public class Ghost extends Swimmer {

    public Ghost(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        this.x = x;
        this.y = y;
        this.length = GHOST_LENGTH;
        this.weight = GHOST_WEIGHT;
        this.velocity = GHOST_SPEED;
        this.passiveAbilityPower = GHOST_PASSIVE_ABILITY;
        this.maxEnergy = GHOST_MAX_ENERGY;
        this.regen = GHOST_HEALTH_REGEN;
        this.energy = this.maxEnergy;
        this.energyIncrease = GHOST_ENERGY_INCREASE;
        this.maxTurn = GHOST_TURN;
        this.health = GHOST_MAX_HEALTH;
        this.maxHealth = GHOST_MAX_HEALTH;
        this.damage = GHOST_DAMAGE;
        this.abilityTime = GHOST_ABILITY_TIME;
        this.boostTime = GHOST_BOOST_TIME;
        this.ability1 = ELECTRIC_HORN;
        
        this.type = GHOST;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 15000, mouseX, mouseY, mousePressed);
        energyTime--;
        if (energyTime <= 0) {
            velocity = GHOST_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = GHOST_SPEED * 5;
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
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

}
