package wildnature.general.characters.swimmers;

import wildnature.general.characters.*;
import static wildnature.general.Defaults.*;
import static wildnature.general.characters.Ability.SUPERBITE;
import static wildnature.general.characters.Animal.HIPPO;
import wildnature.server.Environment;
import processing.core.PApplet;

public class Hippo extends Swimmer {

    public Hippo(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        this.x = x;
        this.y = y;
        knockbackResistence = 2;
        this.length = HIPPO_LENGTH;
        this.weight = HIPPO_WEIGHT;
        this.velocity = HIPPO_SPEED;
        this.passiveAbilityPower = HIPPO_PASSIVE_ABILITY;
        this.maxEnergy = HIPPO_MAX_ENERGY;
        this.regen = HIPPO_HEALTH_REGEN;
        this.energy = this.maxEnergy;
        this.energyIncrease = HIPPO_ENERGY_INCREASE;
        this.maxTurn = HIPPO_TURN;
        this.health = HIPPO_MAX_HEALTH;
        this.maxHealth = HIPPO_MAX_HEALTH;
        this.damage = HIPPO_DAMAGE;
        this.abilityTime = HIPPO_ABILITY_TIME;
        this.boostTime = HIPPO_BOOST_TIME;
        this.armor = HIPPO_ARMOR;
        this.armor = HIPPO_ARMOR_PIERCING;
        this.ability1 = SUPERBITE;

        this.type = HIPPO;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 1500, mouseX, mouseY, mousePressed);
        energyTime--;
        velocity = HIPPO_SPEED;
        if (control.stop()) {
            velocity = 0;
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
        return (int) HIPPO_LENGTH;
    }

    @Override
    public int getHeight() {
        return 63;
    }

}
