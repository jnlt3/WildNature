package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Animal.SEA_DRAGON;
import processing.core.PApplet;
import static dsekercioglu.general.characters.Ability.PRISON;
import dsekercioglu.server.Environment;

public class SeaDragon extends Swimmer {

    public float mouseX;
    public float mouseY;

    public SeaDragon(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        this.x = x;
        this.y = y;
        knockbackPower = 50;
        knockbackResistence = 3F;
        this.length = SEA_DRAGON_LENGTH;
        this.weight = SEA_DRAGON_WEIGHT;
        this.velocity = SEA_DRAGON_SPEED;
        this.passiveAbilityPower = SEA_DRAGON_PASSIVE_ABILITY;
        this.maxEnergy = SEA_DRAGON_MAX_ENERGY;
        this.regen = SEA_DRAGON_HEALTH_REGEN;
        this.energy = this.maxEnergy;
        this.energyIncrease = SEA_DRAGON_ENERGY_INCREASE;
        this.maxTurn = SEA_DRAGON_TURN;
        this.health = SEA_DRAGON_MAX_HEALTH;
        this.maxHealth = SEA_DRAGON_MAX_HEALTH;
        this.damage = SEA_DRAGON_DAMAGE;
        this.abilityTime = SEA_DRAGON_ABILITY_TIME;
        this.boostTime = SEA_DRAGON_BOOST_TIME;
        this.ability1 = Ability.BANG;
        // = SLOW_DOWN;
        // = REGEN_BOOST;

        this.type = SEA_DRAGON;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 1500, mouseX, mouseY, mousePressed);
        this.mouseX = x + mouseX - 600;
        this.mouseY = y + mouseY - 300;
        energyTime--;
        if (energyTime <= 0) {
            velocity = SEA_DRAGON_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = SEA_DRAGON_SPEED * 1.2F;
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
        return (int) SEA_DRAGON_LENGTH;
    }

    @Override
    public int getHeight() {
        return 48;
    }
}
