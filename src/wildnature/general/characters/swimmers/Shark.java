package wildnature.general.characters.swimmers;

import wildnature.general.characters.*;
import static wildnature.general.Defaults.*;
import processing.core.PApplet;
import static wildnature.general.characters.Ability.KNOCKBACK;
import static wildnature.general.characters.Animal.SHARK;
import wildnature.server.Environment;

public class Shark extends Swimmer {

    public Shark(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        this.x = x;
        this.y = y;
        this.length = SHARK_LENGTH;
        this.weight = SHARK_WEIGHT;
        this.velocity = SHARK_SPEED;
        this.passiveAbilityPower = SHARK_PASSIVE_ABILITY;
        this.maxEnergy = SHARK_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.regen = SHARK_HEALTH_REGEN;
        this.energyIncrease = SHARK_ENERGY_INCREASE;
        this.maxTurn = SHARK_TURN;
        this.health = SHARK_MAX_HEALTH;
        this.maxHealth = SHARK_MAX_HEALTH;
        this.damage = SHARK_DAMAGE;
        this.abilityTime = SHARK_ABILITY_TIME;
        this.boostTime = SHARK_BOOST_TIME;
        this.armor = SHARK_ARMOR;
        this.armorPiercing = SHARK_ARMOR_PIERCING;
        this.ability1 = KNOCKBACK;

        this.type = SHARK;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 6000, 1500, mouseX, mouseY, mousePressed);
        energyTime--;
        if (energyTime <= 0) {
            velocity = SHARK_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity = SHARK_SPEED * 3;
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
        return (int) SHARK_LENGTH;
    }

    @Override
    public int getHeight() {
        return 34;
    }

}
