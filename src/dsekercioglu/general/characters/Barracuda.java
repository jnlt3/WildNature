package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.REGEN_BOOST;
import static dsekercioglu.general.characters.Ability.STICK;
import static dsekercioglu.general.characters.Animal.BARRACUDA;
import java.awt.geom.Point2D;
import processing.core.PApplet;
import static dsekercioglu.general.characters.Ability.DRAIN_HIT;
import dsekercioglu.server.Environment;

public class Barracuda extends Swimmer {

    public Barracuda(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        this.x = x;
        this.y = y;
        this.length = BARRACUDA_LENGTH;
        this.weight = BARRACUDA_WEIGHT;
        this.velocity = BARRACUDA_SPEED;
        this.passiveAbilityPower = BARRACUDA_PASSIVE_ABILITY;
        this.maxEnergy = BARRACUDA_MAX_ENERGY;
        this.regen = BARRACUDA_HEALTH_REGEN;
        this.energy = this.maxEnergy;
        this.energyIncrease = BARRACUDA_ENERGY_INCREASE;
        this.maxTurn = BARRACUDA_TURN;
        this.health = BARRACUDA_MAX_HEALTH;
        this.maxHealth = BARRACUDA_MAX_HEALTH;
        this.damage = BARRACUDA_DAMAGE;
        this.abilityTime = BARRACUDA_ABILITY_TIME;
        this.boostTime = BARRACUDA_BOOST_TIME;
        this.ability1 = STICK;
        this.ability2 = DRAIN_HIT;
        this.ability3 = REGEN_BOOST;

        this.type = BARRACUDA;
    }
    
    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 1500, mouseX, mouseY, mousePressed);
        energyTime--;
        if (energyTime <= 0) {
            velocity = BARRACUDA_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = BARRACUDA_SPEED * 5;
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
        return (int) BARRACUDA_LENGTH;
    }

    @Override
    public int getHeight() {
        return 26;
    }
}
