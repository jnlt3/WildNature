package dsekercioglu.client;

import static dsekercioglu.general.Defaults.*;
import dsekercioglu.general.characters.Animal;
import dsekercioglu.general.characters.DrawInfo;
import dsekercioglu.general.characters.Swimmer;
import dsekercioglu.general.multiPlayer.ControlInfo;
import java.util.ArrayList;
import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PImage;

public class Visualizer {

    private final HashMap<String, PImage> images = new HashMap<>();
    private final PApplet pa;
    private final String name;
    private final Animal animal;

    PImage img;

    public Visualizer(String name, Animal animal, PApplet pa) {
        this.pa = pa;
        this.name = name;
        this.animal = animal;
        Swimmer.pa = pa;
    }

    public void setImages() {
        PImage marlin = pa.loadImage("img/Marlin.png");
        marlin.resize((int) MARLIN_LENGTH, 0);
        images.put("Marlin", marlin);
        PImage blackMarlin = pa.loadImage("img/BlackMarlin.png");
        blackMarlin.resize((int) BLACK_MARLIN_LENGTH, 0);
        images.put("BlackMarlin", blackMarlin);
        PImage crocodile = pa.loadImage("img/Crocodile.png");
        crocodile.resize((int) CROCODILE_LENGTH, 0);
        images.put("Crocodile", crocodile);
    }

    public void update(ArrayList<DrawInfo> characters) {
        ControlInfo c = new ControlInfo();
        c.mouseX = this.pa.mouseX;
        c.mouseY = this.pa.mouseY;
        c.mousePressed = this.pa.mousePressed;
        c.name = this.name;
        WildNature.client.sendUDP(c);
        drawGrids();
        for (int i = 0; i < characters.size(); i++) {
            DrawInfo d = (DrawInfo) characters.get(i);
            if (d.name.equals(this.name)) {
                drawHealthBar(d.health / d.maxHealth);
                Swimmer.setCenter(d.x, d.y);
                this.pa.fill(0, 0, 0, 0);
                this.pa.stroke(255);
                this.pa.ellipse(600, 300, 200, 200);
            }
        }
        for (int i = 0; i < characters.size(); i++) {
            DrawInfo di = (DrawInfo) characters.get(i);
            if (!di.hiding) {
                Swimmer.drawCostume(images.get(di.img), di.x, di.y, di.angle);
            }
        }
    }

    private void drawGrids() {
        this.pa.stroke(0.0F, 0.0F, 255.0F);
        this.pa.fill(0.0F, 0.0F, 255.0F);
        this.pa.rect(0.0F, 0.0F, 1200.0F, 600.0F);
        this.pa.stroke(0.0F, 0.0F, 230.0F);
        this.pa.strokeWeight(3.0F);
        float cx = Swimmer.cx % 60.0F;
        float cy = Swimmer.cy % 60.0F;
        for (int y = 0; y < 600; y += 60) {
            this.pa.line(0.0F, y - cy, 1200.0F, y - cy);
        }
        for (int x = 0; x < 1200; x += 60) {
            this.pa.line(x - cx, 0.0F, x - cx, 600.0F);
        }
        this.pa.stroke(255.0F, 0.0F, 0.0F);
        this.pa.fill(0.0F, 0.0F, 0.0F, 0.0F);
        this.pa.rect(-5000.0F - Swimmer.cx + 600.0F, -5000.0F - Swimmer.cy + 300.0F, 10000.0F, 10000.0F);
    }

    private void drawHealthBar(double rate) {
        int green = Math.min((int) (rate * 510), 255);
        int red = Math.min((int) ((1 - rate) * 510), 255);
        System.out.println(red + " " + green);
        this.pa.fill(red, green, 0);
        this.pa.stroke(red, green, 0);
        float radius = (float) (rate * 50);
        this.pa.ellipse(600, 100, radius, radius);
    }

}
