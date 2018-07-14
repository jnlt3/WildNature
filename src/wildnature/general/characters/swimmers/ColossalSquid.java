package wildnature.general.characters.swimmers;

import wildnature.general.characters.*;
import static wildnature.general.Defaults.*;
import static wildnature.general.characters.Ability.INKSPILL;
import static wildnature.general.characters.Animal.COLOSSAL_SQUID;
import processing.core.PApplet;
import wildnature.server.Environment;

public class ColossalSquid extends Swimmer {

    public ColossalSquid(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
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
        this.armor = COLOSSAL_SQUID_ARMOR;
        this.armorPiercing = COLOSSAL_SQUID_ARMOR_PIERCING;
        this.type = COLOSSAL_SQUID;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 2000, mouseX, mouseY, mousePressed);
        energyTime--;
        if (energyTime <= 0) {
            velocity = COLOSSAL_SQUID_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = COLOSSAL_SQUID_SPEED * 5;
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
        return (int) COLOSSAL_SQUID_LENGTH;
    }

    @Override
    public int getHeight() {
        return 71;
    }

}
