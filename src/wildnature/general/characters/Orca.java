package wildnature.general.characters;

import static wildnature.general.Defaults.*;
import static wildnature.general.characters.Ability.GRAB;
import static wildnature.general.characters.Animal.ORCA;
import processing.core.PApplet;
import wildnature.server.Environment;

public class Orca extends Swimmer {

    public Orca(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        this.x = x;
        this.y = y;
        this.length = ORCA_LENGTH;
        this.weight = ORCA_WEIGHT;
        this.velocity = ORCA_SPEED;
        this.passiveAbilityPower = ORCA_PASSIVE_ABILITY;
        this.maxEnergy = ORCA_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.regen = ORCA_HEALTH_REGEN;
        this.energyIncrease = ORCA_ENERGY_INCREASE;
        this.maxTurn = ORCA_TURN;
        this.health = ORCA_MAX_HEALTH;
        this.maxHealth = ORCA_MAX_HEALTH;
        this.damage = ORCA_DAMAGE;
        this.abilityTime = ORCA_ABILITY_TIME;
        this.boostTime = ORCA_BOOST_TIME;
        this.armor = ORCA_ARMOR;
        this.armorPiercing = ORCA_ARMOR_PIERCING;
        this.ability1 = GRAB;

        this.type = ORCA;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 1500, mouseX, mouseY, mousePressed);
        energyTime--;
        if (energyTime <= 0) {
            velocity = ORCA_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = ORCA_SPEED * 5;
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
        return (int) ORCA_LENGTH;
    }

    @Override
    public int getHeight() {
        return 42;
    }

}
