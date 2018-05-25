package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Animal.GUARDIAN;
import processing.core.PApplet;
import static dsekercioglu.general.characters.Ability.PRISON;
import dsekercioglu.server.Environment;

public class Guardian extends Swimmer {

    public float mouseX;
    public float mouseY;

    public Guardian(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
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
        this.ability1 = PRISON;
        // = SLOW_DOWN;
        // = REGEN_BOOST;

        this.type = GUARDIAN;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 1500,  mouseX, mouseY, mousePressed);
        this.mouseX = x + mouseX - 600;
        this.mouseY = y + mouseY - 300;
        energyTime--;
        if (energyTime <= 0) {
            velocity = GUARDIAN_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            for (int i = 0; i < 10; i++) {
                this.regen();
            }
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
        return (int) GUARDIAN_LENGTH;
    }

    @Override
    public int getHeight() {
        return 86;
    }
}
