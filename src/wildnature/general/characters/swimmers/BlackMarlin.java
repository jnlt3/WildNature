package wildnature.general.characters.swimmers;

import static wildnature.general.Defaults.*;
import static wildnature.general.characters.Ability.HORN;
import static wildnature.general.characters.Animal.BLACK_MARLIN;
import processing.core.PApplet;
import wildnature.general.characters.Swimmer;
import wildnature.server.Environment;

public class BlackMarlin extends Swimmer {

    public BlackMarlin(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        this.x = x;
        this.y = y;
        this.length = BLACK_MARLIN_LENGTH;
        this.weight = BLACK_MARLIN_WEIGHT;
        this.velocity = BLACK_MARLIN_SPEED;
        this.passiveAbilityPower = BLACK_MARLIN_PASSIVE_ABILITY;
        this.maxEnergy = BLACK_MARLIN_MAX_ENERGY;
        this.regen = BLACK_MARLIN_HEALTH_REGEN;
        this.energy = this.maxEnergy;
        this.energyIncrease = BLACK_MARLIN_ENERGY_INCREASE;
        this.maxTurn = BLACK_MARLIN_TURN;
        this.health = BLACK_MARLIN_MAX_HEALTH;
        this.maxHealth = BLACK_MARLIN_MAX_HEALTH;
        this.damage = BLACK_MARLIN_DAMAGE;
        this.abilityTime = BLACK_MARLIN_ABILITY_TIME;
        this.boostTime = BLACK_MARLIN_BOOST_TIME;
        this.ability1 = HORN;
        this.armor = BLACK_MARLIN_ARMOR;
        this.armorPiercing = BLACK_MARLIN_ARMOR_PIERCING;
        this.type = BLACK_MARLIN;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 2500, mouseX, mouseY, mousePressed);
        energyTime--;
        if (energyTime <= 0) {
            velocity = BLACK_MARLIN_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = BLACK_MARLIN_SPEED * 5;
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
        return (int) BLACK_MARLIN_LENGTH;
    }

    @Override
    public int getHeight() {
        return 31;
    }

}
