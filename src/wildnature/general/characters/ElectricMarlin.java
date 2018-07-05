package wildnature.general.characters;

import static wildnature.general.Defaults.*;
import static wildnature.general.characters.Ability.ELECTRIC_HORN;
import static wildnature.general.characters.Ability.SHOCK;
import static wildnature.general.characters.Animal.ELECTRIC_MARLIN;
import processing.core.PApplet;
import wildnature.server.Environment;

public class ElectricMarlin extends Swimmer {

    public ElectricMarlin(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        this.x = x;
        this.y = y;
        this.length = ELECTRIC_MARLIN_LENGTH;
        this.weight = ELECTRIC_MARLIN_WEIGHT;
        this.velocity = ELECTRIC_MARLIN_SPEED;
        this.passiveAbilityPower = ELECTRIC_MARLIN_PASSIVE_ABILITY;
        this.maxEnergy = ELECTRIC_MARLIN_MAX_ENERGY;
        this.regen = ELECTRIC_MARLIN_HEALTH_REGEN;
        this.energy = this.maxEnergy;
        this.energyIncrease = ELECTRIC_MARLIN_ENERGY_INCREASE;
        this.maxTurn = ELECTRIC_MARLIN_TURN;
        this.health = ELECTRIC_MARLIN_MAX_HEALTH;
        this.maxHealth = ELECTRIC_MARLIN_MAX_HEALTH;
        this.damage = ELECTRIC_MARLIN_DAMAGE;
        this.abilityTime = ELECTRIC_MARLIN_ABILITY_TIME;
        this.boostTime = ELECTRIC_MARLIN_BOOST_TIME;
        this.armor = ELECTRIC_MARLIN_ARMOR;
        this.armorPiercing = ELECTRIC_MARLIN_ARMOR_PIERCING;
        this.ability1 = ELECTRIC_HORN;
        
        this.type = ELECTRIC_MARLIN;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 2500, mouseX, mouseY, mousePressed);
        energyTime--;
        if (energyTime <= 0) {
            velocity = ELECTRIC_MARLIN_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = ELECTRIC_MARLIN_SPEED * 5;
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
        return (int) ELECTRIC_MARLIN_LENGTH;
    }

    @Override
    public int getHeight() {
        return 29;
    }

}
