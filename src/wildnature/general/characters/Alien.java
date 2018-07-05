package wildnature.general.characters;

import static wildnature.general.Defaults.*;
import static wildnature.general.characters.Ability.BLEEDING_KNOCKBACK;
import static wildnature.general.characters.Ability.SHORT_GRAB;
import static wildnature.general.characters.Ability.SPEEDY;
import static wildnature.general.characters.Animal.ALIEN;
import processing.core.PApplet;
import wildnature.server.Environment;

public class Alien extends Swimmer {

    public Alien(String name, float x, float y, PApplet p, Environment e) {
        super(name, p);
        this.e = e;
        this.x = x;
        this.y = y;
        knockbackPower = 25F;
        this.length = ALIEN_LENGTH;
        this.weight = ALIEN_WEIGHT;
        this.velocity = ALIEN_SPEED;
        this.passiveAbilityPower = ALIEN_PASSIVE_ABILITY;
        this.maxEnergy = ALIEN_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.regen = ALIEN_HEALTH_REGEN;
        this.energyIncrease = ALIEN_ENERGY_INCREASE;
        this.maxTurn = ALIEN_TURN;
        this.health = ALIEN_MAX_HEALTH;
        this.maxHealth = ALIEN_MAX_HEALTH;
        this.damage = ALIEN_DAMAGE;
        this.abilityTime = ALIEN_ABILITY_TIME;
        this.boostTime = ALIEN_BOOST_TIME;
        this.armor = ALIEN_ARMOR;
        this.armorPiercing = ALIEN_ARMOR_PIERCING;
        this.ability1 = SPEEDY;
        this.type = ALIEN;
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        control.riskControl(e.characters, 0, 1500, mouseX, mouseY, mousePressed);
        energyTime--;
        if (energyTime <= -1) {
            velocity = ALIEN_SPEED;
            if (control.stop()) {
                velocity = 0;
            }
        } else {
            velocity += 0.02;
            velocity = Math.min(10F, velocity);
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
        return (int) ALIEN_LENGTH;
    }

    @Override
    public int getHeight() {
        return 36;
    }

}
