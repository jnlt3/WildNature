package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.BLEED;
import java.awt.geom.Point2D;
import processing.core.PApplet;

public class Marlin extends Swimmer {

    private int energyTime;

    public Marlin(String name, float x, float y, PApplet p) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = MARLIN_LENGTH;
        this.weight = MARLIN_WEIGHT;
        this.velocity = MARLIN_SPEED;
        this.passiveAbilityPower = MARLIN_PASSIVE_ABILITY;
        this.maxEnergy = MARLIN_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.energyIncrease = MARLIN_ENERGY_INCREASE;
        this.maxTurn = MARLIN_TURN;
        this.health = MARLIN_MAX_HEALTH;
        this.maxHealth = MARLIN_MAX_HEALTH;
        this.damage = MARLIN_DAMAGE;
        this.ability = BLEED;

        this.type = "Marlin";
    }

    private double turn(double newAngle) {
        double dif = newAngle - this.angle;
        while (dif < -3.141592653589793D) {
            dif += 6.283185307179586D;
        }
        while (dif > 3.141592653589793D) {
            dif -= 6.283185307179586D;
        }
        return Math.max(Math.min(dif, this.maxTurn), -this.maxTurn);
    }

    @Override
    public void update(int mouseX, int mouseY, boolean mousePressed) {
        this.angle = ((float) (this.angle + turn((float) Math.atan2(mouseY - 300, mouseX - 600))));
        if (Point2D.distance(600, 300, mouseX, mouseY) > 100 || energyTime != 0) {
            this.x = ((float) (this.x + Math.cos(this.angle) * this.velocity));
            this.y = ((float) (this.y + Math.sin(this.angle) * this.velocity));
        }
        this.energy = Math.min(this.energy + this.energyIncrease, this.maxEnergy);
        if ((mousePressed) && (this.energy >= 1.0F) && (this.energyTime == 0)) {
            this.energy -= 1.0F;
            this.velocity *= 3.0F;
            this.maxTurn /= 3.0F;
            this.energyTime = 50;
        }
        this.energyTime = Math.max(this.energyTime - 1, 0);
        if (this.energyTime == 0) {
            this.velocity = MARLIN_SPEED;
            this.maxTurn = MARLIN_TURN;
        }
        this.health = Math.min(this.health + MARLIN_HEALTH_REGEN, this.maxHealth);
    }
    
    @Override
    public int getWidth() {
        return (int) MARLIN_LENGTH;
    }

    @Override
    public int getHeight() {
        return 44;
    }
}
