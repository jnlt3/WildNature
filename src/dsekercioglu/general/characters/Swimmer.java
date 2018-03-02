package dsekercioglu.general.characters;

import processing.core.PApplet;
import processing.core.PImage;

public abstract class Swimmer {

    private final String name;

    public static PApplet pa;
    public static float cx;
    public static float cy;
    public float x;
    public float y;
    public float angle;
    public float damage;

    protected PImage img;
    protected String type;
    protected float health;
    protected float maxHealth;
    protected float length;
    protected float weight;
    protected float velocity;
    protected float maxTurn;
    protected float passiveAbilityPower;
    protected float maxEnergy;
    protected float energy;
    protected float energyIncrease;

    public Swimmer(String name, PApplet p) {
        this.name = name;
        pa = p;
    }

    public abstract void setImages();

    public abstract void update(int paramInt1, int paramInt2, boolean paramBoolean);

    public abstract boolean isAlive();

    public static void setCenter(float x, float y) {
        cx = x;
        cy = y;
    }

    public abstract void draw();

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

    public DrawInfo getDrawInfo() {
        DrawInfo di = new DrawInfo();
        di.x = this.x;
        di.y = this.y;
        di.angle = this.angle;
        di.health = this.health;
        di.maxHealth = this.maxHealth;
        di.name = this.name;
        di.img = this.type;
        return di;
    }
    
    public void hit(double damage) {
        health -= damage;
        health = Math.max(health, 0);
    }

    public abstract int getWidth();

    public abstract int getHeight();
}
