package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Swimmer.drawCostume;
import static dsekercioglu.general.characters.Swimmer.pa;
import processing.core.PApplet;

public class BlackMarlin extends Swimmer {

    private int energyTime;

    public BlackMarlin(String name, float x, float y, PApplet p) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = BLACK_MARLIN_LENGTH;
        this.weight = BLACK_MARLIN_WEIGHT;
        this.velocity = BLACK_MARLIN_SPEED;
        this.passiveAbilityPower = BLACK_MARLIN_PASSIVE_ABILITY;
        this.maxEnergy = BLACK_MARLIN_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.energyIncrease = BLACK_MARLIN_ENERGY_INCREASE;
        this.maxTurn = BLACK_MARLIN_TURN;
        this.health = BLACK_MARLIN_MAX_HEALTH;
        this.maxHealth = BLACK_MARLIN_MAX_HEALTH;
        this.damage = BLACK_MARLIN_DAMAGE;
        
        this.type = "BlackMarlin";
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
        this.x = ((float) (this.x + Math.cos(this.angle) * this.velocity));
        this.y = ((float) (this.y + Math.sin(this.angle) * this.velocity));
        this.energy = Math.min(this.energy + this.energyIncrease, this.maxEnergy);
        if ((mousePressed) && (this.energy >= 1.0F) && (this.energyTime == 0)) {
            this.energy -= 1.0F;
            this.velocity *= 3.0F;
            this.maxTurn /= 3.0F;
            this.energyTime = 50;
        }
        this.energyTime = Math.max(this.energyTime - 1, 0);
        if (this.energyTime == 0) {
            this.velocity = BLACK_MARLIN_SPEED;
            this.maxTurn = BLACK_MARLIN_TURN;
        }
        this.health = Math.min(this.health + 1.0F, this.maxHealth);
    }

    @Override
    public void draw() {
        drawCostume(this.img, this.x, this.y, this.angle);
    }

    @Override
    public boolean isAlive() {
        return this.health > 0.0F;
    }

    @Override
    public void setImages() {
        this.img = pa.loadImage("Marlin.png");
        this.img.resize((int) this.length, 0);
    }

    @Override
    public int getWidth() {
        return (int) BLACK_MARLIN_LENGTH;
    }

    @Override
    public int getHeight() {
        return 49;
    }
    
}
