package dsekercioglu.general.characters;

import static dsekercioglu.general.Defaults.*;
import static dsekercioglu.general.characters.Ability.GRAB;
import static dsekercioglu.general.characters.Swimmer.drawCostume;
import static dsekercioglu.general.characters.Swimmer.pa;
import java.awt.geom.Point2D;
import processing.core.PApplet;

public class Shark extends Swimmer {

    private int energyTime;

    public Shark(String name, float x, float y, PApplet p) {
        super(name, p);
        this.x = x;
        this.y = y;
        this.length = SHARK_LENGTH;
        this.weight = SHARK_WEIGHT;
        this.velocity = SHARK_SPEED;
        this.passiveAbilityPower = SHARK_PASSIVE_ABILITY;
        this.maxEnergy = SHARK_MAX_ENERGY;
        this.energy = this.maxEnergy;
        this.energyIncrease = SHARK_ENERGY_INCREASE;
        this.maxTurn = SHARK_TURN;
        this.health = SHARK_MAX_HEALTH;
        this.maxHealth = SHARK_MAX_HEALTH;
        this.damage = SHARK_DAMAGE;
        this.ability = GRAB;
        
        this.type = "Shark";
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
            this.hiding = false;
        } else {
            this.hiding = true;
        }
        this.energy = Math.min(this.energy + this.energyIncrease, this.maxEnergy);
        if ((mousePressed) && (this.energy >= 1.0F) && (this.energyTime == 0)) {
            this.energy -= 1.0F;
            this.velocity *= 5.0F;
            this.maxTurn /= 10.0F;
            this.energyTime = 100;
        }
        this.energyTime = Math.max(this.energyTime - 1, 0);
        if (this.energyTime == 0) {
            this.velocity = SHARK_SPEED;
            this.maxTurn = SHARK_TURN;
        }
        this.health = Math.min(this.health + SHARK_HEALTH_REGEN, this.maxHealth);
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
        this.img = pa.loadImage("Shark.png");
        this.img.resize((int) this.length, 0);
    }

    @Override
    public int getWidth() {
        return (int) SHARK_LENGTH;
    }

    @Override
    public int getHeight() {
        return 62;
    }
    
}
