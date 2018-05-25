package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.REGEN;
import processing.core.PApplet;
import static dsekercioglu.general.characters.Animal.DOLPHIN;
import dsekercioglu.server.Environment;

public class Dolphin extends Swimmer {

    public Dolphin(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        this.x = x;
        this.y = y;
        this.length = DOLPHIN_LENGTH;
        this.weight = DOLPHIN_WEIGHT;
        this.velocity = DOLPHIN_SPEED;
        this.passiveAbilityPower = DOLPHIN_PASSIVE_ABILITY;
        this.maxEnergy = DOLPHIN_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.regen = DOLPHIN_HEALTH_REGEN;
        this.energyIncrease = DOLPHIN_ENERGY_INCREASE;
        this.maxTurn = DOLPHIN_TURN;
        this.health = DOLPHIN_MAX_HEALTH;
        this.maxHealth = DOLPHIN_MAX_HEALTH;
        this.damage = DOLPHIN_DAMAGE;
        this.abilityTime = DOLPHIN_ABILITY_TIME;
        this.boostTime = DOLPHIN_BOOST_TIME;
        this.ability1 = REGEN;
        // = DAMAGE_BOOST;
        // = BLEED;

        this.type = DOLPHIN;
    }
    
    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 1500,  mouseX, mouseY, mousePressed);
        energyTime--;
        if (energyTime <= 0) {
            velocity = DOLPHIN_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = DOLPHIN_SPEED * 2;
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
        return (int) DOLPHIN_LENGTH;
    }

    @Override
    public int getHeight() {
        return 61;
    }

}
