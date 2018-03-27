package dsekercioglu.general.characters;

import dsekercioglu.general.control.Control;
import dsekercioglu.server.Environment;
import processing.core.PApplet;
import processing.core.PImage;

public abstract class Swimmer {

    private static final double MOMENTUM = 0.3;
    private final String name;

    public Environment e;
    public Team team;
    public Control control;

    public static PApplet pa;
    public static float cx;
    public static float cy;
    public float x;
    public float y;
    public float angle;
    public float damage;
    public float velocity;
    public float regen;
    public int energyTime;
    public int abilityTime;
    public boolean hiding;
    public float health;
    public float maxHealth;
    public float energy;
    public Ability ability1;
    public Ability ability2;
    public Ability ability3;

    protected PImage img;
    protected Animal type;
    protected float length;
    protected float weight;
    protected float maxTurn;
    protected float passiveAbilityPower;
    protected float maxEnergy;
    protected float energyIncrease;
    protected int boostTime;
    public int blind;

    private double xChange;
    private double yChange;
    
    public Swimmer(String name, PApplet p) {
        this.name = name;
        pa = p;
    }

    public abstract void update(int paramInt1, int paramInt2, boolean paramBoolean);

    public void updateMove() {
        x += xChange;
        y += yChange;
        blind--;
        blind = Math.max(blind, 0);
    }

    public void regen() {
        health = Math.min(health + regen, maxHealth);
    }

    public void move(double velocity, double angle) {
        this.angle += turn(angle);
        xChange += (float) (Math.cos(this.angle) * velocity);
        yChange += (float) (Math.sin(this.angle) * velocity);
        xChange *= MOMENTUM;
        yChange *= MOMENTUM;
    }

    public void setMoveInAngle(double velocity, double angle) {
        this.angle = (float) angle;
        xChange += (float) (Math.cos(angle) * velocity);
        yChange += (float) (Math.sin(angle) * velocity);
        xChange *= MOMENTUM;
        yChange *= MOMENTUM;
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

    public boolean isAlive() {
        return this.health > 0.0F;
    }

    public static void setCenter(float x, float y) {
        cx = x;
        cy = y;
    }

    public static void drawCostume(PImage costume, float centerX, float centerY, float radians) {
        radians = (float) (radians + 3.141592653589793D);
        pa.imageMode(3);
        pa.translate(centerX - cx + 600.0F, centerY - cy + 300.0F);
        pa.rotate(radians);
        pa.image(costume, 0.0F, 0.0F);
        pa.rotate(-radians);
        pa.translate(-(centerX - cx + 600.0F), -(centerY - cy + 300.0F));
    }

    public String getName() {
        return this.name;
    }

    public Animal getType() {
        return this.type;
    }

    public DrawInfo getDrawInfo() {
        DrawInfo di = new DrawInfo();
        di.x = this.x;
        di.y = this.y;
        di.angle = this.angle;
        di.health = this.health;
        di.maxHealth = this.maxHealth;
        di.maxEnergy = this.maxEnergy;
        di.energy = this.energy;
        di.name = this.name;
        di.img = this.type + "";
        di.hiding = this.hiding;
        di.blind = blind > 0;
        di.team = this.team;
        return di;
    }

    public void hit(double damage) {
        health -= damage;
        health = Math.max(health, 0);
    }

    public abstract int getWidth();

    public abstract int getHeight();

    public void respawn(int width, int height) {
        x = (float) (Math.random() * 2 * width - width);
        y = (float) (Math.random() * 2 * height - height);
        health = maxHealth;
        energy = maxEnergy;
    }

    public void setBlind(int blind) {
        this.blind = blind;
    }

    public void attacked() {
    }
;
}
