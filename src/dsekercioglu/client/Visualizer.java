package dsekercioglu.client;

import com.sun.javafx.geom.Point2D;
import static dsekercioglu.general.Defaults.*;
import dsekercioglu.general.characters.Animal;
import static dsekercioglu.general.characters.Animal.*;
import dsekercioglu.general.characters.DrawInfo;
import dsekercioglu.general.characters.Swimmer;
import dsekercioglu.general.characters.Team;
import dsekercioglu.general.multiPlayer.ControlInfo;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import processing.core.PApplet;
import processing.core.PImage;

public class Visualizer {

    private final int WIDTH = 2000;
    private final int HEIGHT = 2000;
    private final HashMap<String, PImage> images = new HashMap<>();
    private final PApplet pa;
    private final String name;
    private final Animal animal;
    private Team team;

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

        PImage electricMarlin = pa.loadImage("img/ElectricMarlin.png");
        electricMarlin.resize((int) ELECTRIC_MARLIN_LENGTH, 0);
        images.put("ELECTRIC_MARLIN", electricMarlin);

        PImage guardian = pa.loadImage("img/Guardian.png");
        guardian.resize((int) GUARDIAN_LENGTH, 0);
        images.put("GUARDIAN", guardian);
    }

    public void update(ArrayList<DrawInfo> characters) {
        ControlInfo c = new ControlInfo();
        c.mouseX = this.pa.mouseX;
        c.mouseY = this.pa.mouseY;
        c.one = this.pa.key == '1' && this.pa.keyPressed;
        c.two = this.pa.key == '2' && this.pa.keyPressed;
        c.three = this.pa.key == '3' && this.pa.keyPressed;
        c.name = this.name;
        WildNature.client.sendUDP(c);
        drawGrids();
        boolean blind = false;

        ArrayList<Pair<Integer, String>> scores = new ArrayList<>();
        for (int i = 0; i < characters.size(); i++) {
            DrawInfo d = (DrawInfo) characters.get(i);
            scores.add(new Pair(d.score, d.name));
            if (d.name.equals(this.name)) {
                team = d.team;
                drawVision(animal.name(), characters);
                Swimmer.setCenter(d.x, d.y);
                this.pa.fill(0, 0, 0, 0);
                this.pa.stroke(255);
                this.pa.ellipse(600, 300, 200, 200);
                blind = d.blind;
                drawEnergyBar(d.energy / d.maxEnergy);
            }
        }
        if (!blind) {
            for (int i = 0; i < characters.size(); i++) {
                DrawInfo di = (DrawInfo) characters.get(i);
                if (!di.hiding) {
                    float cx = di.x - Swimmer.cx + 600;
                    float cy = di.y - Swimmer.cy + 300;
                    pa.fill(255);
                    pa.textSize(20);
                    pa.text(di.name, cx, cy - 110);
                    Swimmer.drawCostume(images.get(di.img), di.x, di.y, di.angle);
                    drawHealthBar(di.health / di.maxHealth, cx, cy);
                }
            }
        }

        Collections.sort(scores);
        for (int i = 0; i < scores.size(); i++) {
            Pair<Integer, String> p = scores.get(i);
            String name = p.value;
            int score = p.comparable;
            pa.fill(0, 0, 0, 50);
            pa.rect(1000, i * 50, 200, 50, 10);
            pa.fill(255);
            pa.text(name + ": " + score, 1000, i * 50 + 50);
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
        this.pa.fill(0, 0, 0, 50);
        this.pa.stroke(0, 0, 0, 50);
        float radius = (float) (rate * 50);
        this.pa.ellipse(x, y - 150, radius, radius);
    }

    private void drawEnergyBar(double rate) {
        this.pa.fill(0, 0, 0, 0);
        this.pa.strokeWeight(3);
        this.pa.stroke(0, 255, 255);
        this.pa.rect(0, 600, 20, 600);
        this.pa.fill(0, 255, 255, 50);
        this.pa.rect(0, 600, 20, (float) -(600 * rate));
    }

    private void drawVision(String type, ArrayList<DrawInfo> characters) {
        if (type.equals(MARLIN.name()) || type.equals(BLACK_MARLIN.name()) || type.equals(ELECTRIC_MARLIN.name())) {
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
                if (!d.hiding && Point2D.distance(Swimmer.cx, Swimmer.cy, d.x, d.y) < 2000 && !d.name.equals(name)) {
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
                if (distance < 15000 && !d.name.equals(name)) {
                    double angle = Math.atan2(d.y - Swimmer.cy, d.x - Swimmer.cx);
                    float x = (float) (600 + Math.cos(angle) * 100);
                    float y = (float) (300 + Math.sin(angle) * 100);
                    float radius = 30 - (float) (10 + (distance / 750));
                    this.pa.ellipse(x, y, radius, radius);
                }
            }
        }
        this.pa.fill(0);
        this.pa.stroke(0);
        for (int i = 0; i < characters.size(); i++) {
            DrawInfo d = characters.get(i);
            if (!d.hiding && Point2D.distance(Swimmer.cx, Swimmer.cy, d.x, d.y) < 1500 && !d.name.equals(name)) {
                double angle = Math.atan2(d.y - Swimmer.cy, d.x - Swimmer.cx);
                float x = (float) (600 + Math.cos(angle) * 100);
                float y = (float) (300 + Math.sin(angle) * 100);
                this.pa.ellipse(x, y, 10, 10);
            }
        }
        this.pa.fill(0, 255, 0);
        this.pa.stroke(0, 255, 0);
        for (int i = 0; i < characters.size(); i++) {
            DrawInfo d = characters.get(i);
            if (!d.name.equals(name) && d.team.equals(this.team)) {
                double angle = Math.atan2(d.y - Swimmer.cy, d.x - Swimmer.cx);
                float x = (float) (600 + Math.cos(angle) * 100);
                float y = (float) (300 + Math.sin(angle) * 100);
                this.pa.ellipse(x, y, 10, 10);
            }
        }
    }

    private class Pair<T extends Comparable, U> implements Comparable {

        T comparable;
        U value;

        public Pair(T comparable, U value) {
            this.comparable = comparable;
            this.value = value;
        }

        @Override
        public int compareTo(Object o) {
            return comparable.compareTo(((Pair) o).comparable);
        }

    }
}
