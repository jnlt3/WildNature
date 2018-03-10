package dsekercioglu.client;

import com.sun.javafx.geom.Point2D;
import static dsekercioglu.general.Defaults.*;
import dsekercioglu.general.characters.Animal;
import static dsekercioglu.general.characters.Animal.*;
import dsekercioglu.general.characters.DrawInfo;
import dsekercioglu.general.characters.Swimmer;
import dsekercioglu.general.multiPlayer.ControlInfo;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PImage;

public class Visualizer {

    private final int WIDTH = 5000;
    private final int HEIGHT = 5000;
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
        images.put("MARLIN", marlin);

        PImage blackMarlin = pa.loadImage("img/BlackMarlin.png");
        blackMarlin.resize((int) BLACK_MARLIN_LENGTH, 0);
        images.put("BLACK_MARLIN", blackMarlin);

        PImage crocodile = pa.loadImage("img/Crocodile.png");
        crocodile.resize((int) CROCODILE_LENGTH, 0);
        images.put("CROCODILE", crocodile);

        PImage shark = pa.loadImage("img/Shark.png");
        shark.resize((int) SHARK_LENGTH, 0);
        images.put("SHARK", shark);

        PImage electricEel = pa.loadImage("img/ElectricEel.png");
        electricEel.resize((int) ELECTRIC_EEL_LENGTH, 0);
        images.put("ELECTRIC_EEL", electricEel);

        PImage barracuda = pa.loadImage("img/Barracuda.png");
        barracuda.resize((int) BARRACUDA_LENGTH, 0);
        images.put("BARRACUDA", barracuda);

        PImage megaMouth = pa.loadImage("img/MegaMouth.png");
        megaMouth.resize((int) MEGA_MOUTH_SHARK_LENGTH, 0);
        images.put("MEGA_MOUTH", megaMouth);

        PImage orca = pa.loadImage("img/Orca.png");
        orca.resize((int) ORCA_LENGTH, 0);
        images.put("ORCA", orca);

        PImage hippo = pa.loadImage("img/Hippo.png");
        hippo.resize((int) HIPPO_LENGTH, 0);
        images.put("HIPPO", hippo);

        PImage colossalSquid = pa.loadImage("img/ColossalSquid.png");
        colossalSquid.resize((int) COLOSSAL_SQUID_LENGTH, 0);
        images.put("COLOSSAL_SQUID", colossalSquid);

        PImage doodFish = pa.loadImage("img/Doodfish.png");
        doodFish.resize((int) DOOD_FISH_LENGTH, 0);
        images.put("DOOD_FISH", doodFish);
        System.out.println(doodFish.height);
    }

    public void update(ArrayList<DrawInfo> characters) {
        ControlInfo c = new ControlInfo();
        c.mouseX = this.pa.mouseX;
        c.mouseY = this.pa.mouseY;
        c.mousePressed = this.pa.mousePressed;
        c.name = this.name;
        WildNature.client.sendUDP(c);
        drawGrids();
        boolean blind = false;
        for (int i = 0; i < characters.size(); i++) {
            DrawInfo d = (DrawInfo) characters.get(i);
            if (d.name.equals(this.name)) {
                drawVision(animal.name(), characters);
                Swimmer.setCenter(d.x, d.y);
                this.pa.fill(0, 0, 0, 0);
                this.pa.stroke(255);
                this.pa.ellipse(600, 300, 200, 200);
                blind = d.blind;
            }
        }
        if (!blind) {
            for (int i = 0; i < characters.size(); i++) {
                DrawInfo di = (DrawInfo) characters.get(i);
                if (!di.hiding) {
                    Swimmer.drawCostume(images.get(di.img), di.x, di.y, di.angle);
                    drawHealthBar(di.health / di.maxHealth, di.x - Swimmer.cx + 600, di.y - Swimmer.cy + 300);
                }
            }
        }
    }

    private void drawGrids() {
        this.pa.stroke(0, 0, 255);
        this.pa.fill(0, 0, 255);
        this.pa.rect(0.0F, 0.0F, 1200.0F, 600.0F);
        this.pa.stroke(0.0F, 0, 230);
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
        this.pa.rect(-WIDTH - Swimmer.cx + 600.0F, -HEIGHT - Swimmer.cy + 300.0F, WIDTH * 2, HEIGHT * 2);
    }

    private void drawHealthBar(double rate, float x, float y) {
        int green = Math.min((int) (rate * 510), 255);
        int red = Math.min((int) ((1 - rate) * 510), 255);
        this.pa.fill(red, green, 0);
        this.pa.stroke(red, green, 0);
        float radius = (float) (rate * 50);
        this.pa.ellipse(x, y - 100, radius, radius);
    }

    private void drawVision(String type, ArrayList<DrawInfo> characters) {
        if (type.equals(MARLIN.name()) || type.equals(BLACK_MARLIN.name())) {
            this.pa.fill(0);
            this.pa.stroke(0);
            for (int i = 0; i < characters.size(); i++) {
                DrawInfo d = characters.get(i);
                if (!d.hiding && Point2D.distance(Swimmer.cx, Swimmer.cy, d.x, d.y) < 2500 && !d.name.equals(name)) {
                    double angle = Math.atan2(d.y - Swimmer.cy, d.x - Swimmer.cx);
                    float x = (float) (600 + Math.cos(angle) * 100);
                    float y = (float) (300 + Math.sin(angle) * 100);
                    this.pa.ellipse(x, y, 20, 20);
                }
            }
        } else if (type.equals(SHARK.name())) {
            this.pa.fill(255, 0, 0);
            this.pa.stroke(255, 0, 0);
            for (int i = 0; i < characters.size(); i++) {
                DrawInfo d = characters.get(i);
                if (d.health != d.maxHealth && Point2D.distance(Swimmer.cx, Swimmer.cy, d.x, d.y) < 6000 && !d.name.equals(name)) {
                    double angle = Math.atan2(d.y - Swimmer.cy, d.x - Swimmer.cx);
                    float x = (float) (600 + Math.cos(angle) * 100);
                    float y = (float) (300 + Math.sin(angle) * 100);
                    this.pa.ellipse(x, y, 20, 20);
                }
            }
        } else if (type.equals(COLOSSAL_SQUID.name())) {
            this.pa.fill(0);
            this.pa.stroke(0);
            for (int i = 0; i < characters.size(); i++) {
                DrawInfo d = characters.get(i);
                if (!d.hiding && Point2D.distance(Swimmer.cx, Swimmer.cy, d.x, d.y) < 1250 && !d.name.equals(name)) {
                    double angle = Math.atan2(d.y - Swimmer.cy, d.x - Swimmer.cx);
                    float x = (float) (600 + Math.cos(angle) * 100);
                    float y = (float) (300 + Math.sin(angle) * 100);
                    this.pa.ellipse(x, y, 20, 20);
                }
            }
        } else if (type.equals(DOOD_FISH.name())) {
            this.pa.fill(0);
            this.pa.stroke(0);
            for (int i = 0; i < characters.size(); i++) {
                DrawInfo d = characters.get(i);
                double distance = Point2D.distance(Swimmer.cx, Swimmer.cy, d.x, d.y);
                if (distance < 4000 && !d.name.equals(name)) {
                    double angle = Math.atan2(d.y - Swimmer.cy, d.x - Swimmer.cx);
                    float x = (float) (600 + Math.cos(angle) * 100);
                    float y = (float) (300 + Math.sin(angle) * 100);
                    float radius = 30 - (float) (10 + (distance / 200));
                    this.pa.ellipse(x, y, radius, radius);
                }
            }
        }
    }

}
