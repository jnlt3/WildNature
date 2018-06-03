package dsekercioglu.client;

import com.sun.javafx.geom.Point2D;
import static dsekercioglu.general.Defaults.*;
import dsekercioglu.general.characters.Animal;
import static dsekercioglu.general.characters.Animal.*;
import dsekercioglu.general.characters.DrawInfo;
import dsekercioglu.general.characters.Swimmer;
import dsekercioglu.general.characters.Team;
import dsekercioglu.general.multiPlayer.ControlInfo;
import gifAnimation.Gif;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import processing.core.PApplet;
import processing.core.PImage;

public class Visualizer {

    private final int WIDTH = 2000;
    private final int HEIGHT = 2000;
    private final Map<String, Gif> images = new HashMap<>();
    private final PApplet pa;
    private final String name;
    private final Animal animal;
    private Team team;

    Gif img;

    public Visualizer(String name, Animal animal, PApplet pa) {
        this.pa = pa;
        this.name = name;
        this.animal = animal;
        Swimmer.pa = pa;
    }

    public void setImages() {
        Gif marlin = new Gif(pa, "img/Marlin.gif");
        images.put("MARLIN", marlin);

        Gif blackMarlin = new Gif(pa, "img/BlackMarlin.gif");
        images.put("BLACK_MARLIN", blackMarlin);

        Gif crocodile = new Gif(pa, "img/Crocodile.gif");
        images.put("CROCODILE", crocodile);

        Gif shark = new Gif(pa, "img/Shark.gif");
        images.put("SHARK", shark);

        Gif electricEel = new Gif(pa, "img/ElectricEel.gif");
        images.put("ELECTRIC_EEL", electricEel);

        Gif barracuda = new Gif(pa, "img/Barracuda.gif");
        images.put("BARRACUDA", barracuda);

        Gif megaMouth = new Gif(pa, "img/MegaMouth.gif");
        images.put("MEGA_MOUTH", megaMouth);

        Gif orca = new Gif(pa, "img/Orca.gif");
        images.put("ORCA", orca);

        Gif hippo = new Gif(pa, "img/Hippo.gif");
        images.put("HIPPO", hippo);

        Gif colossalSquid = new Gif(pa, "img/ColossalSquid.gif");
        images.put("COLOSSAL_SQUID", colossalSquid);

        Gif doodFish = new Gif(pa, "img/DoodFish.gif");
        images.put("DOOD_FISH", doodFish);

        Gif electricMarlin = new Gif(pa, "img/ElectricMarlin.gif");
        images.put("ELECTRIC_MARLIN", electricMarlin);

        Gif guardian = new Gif(pa, "img/Guardian.gif");
        images.put("GUARDIAN", guardian);

        Gif sharkodile = new Gif(pa, "img/Sharkodile.gif");
        images.put("SHARKODILE", sharkodile);

        Gif marlinium = new Gif(pa, "img/Marlinium.gif");
        images.put("MARLINIUM", marlinium);

        Gif miniMarlin = new Gif(pa, "img/MiniMarlin.gif");
        images.put("MINI_MARLIN", miniMarlin);

        Gif angleraptor = new Gif(pa, "img/Angleraptor.gif");
        images.put("ANGLERAPTOR", angleraptor);

        Gif tigerShark = new Gif(pa, "img/TigerShark.gif");
        images.put("TIGER_SHARK", tigerShark);

        Gif dolphin = new Gif(pa, "img/Dolphin.gif");
        images.put("DOLPHIN", dolphin);

        Gif makoShark = new Gif(pa, "img/MakoShark.gif");
        images.put("MAKO_SHARK", makoShark);

        Gif twoRulers = new Gif(pa, "img/TwoRulers.gif");
        images.put("TWO_RULERS", twoRulers);

        Gif seaDragon = new Gif(pa, "img/SeaDragon.gif");
        images.put("SEA_DRAGON", seaDragon);

        Gif ghost = new Gif(pa, "img/Ghost.gif");
        images.put("GHOST", ghost);

        Set<String> keySet = images.keySet();
        for (String key : keySet) {
            Gif value = images.get(key);
            value.play();
        }

    }

    public void update(ArrayList<DrawInfo> characters) {
        ControlInfo c = new ControlInfo();
        c.mouseX = this.pa.mouseX;
        c.mouseY = this.pa.mouseY;
        c.power = this.pa.mousePressed || (this.pa.keyPressed && this.pa.key == ' ');
        c.name = this.name;
        WildNature.client.sendUDP(c);
        drawGrids();
        boolean blind = false;

        ArrayList<Pair<Integer, String>> scores = new ArrayList<>();
        for (int i = 0; i < characters.size(); i++) {
            DrawInfo d = (DrawInfo) characters.get(i);
            scores.add(new Pair(d.score, d.name));
            if (d.name.equals(this.name)) {
                blind = d.blind;
                team = d.team;
                if (!d.blind) {
                    drawVision(animal.name(), characters);
                }
                Swimmer.setCenter(d.x, d.y);
                this.pa.fill(0, 0, 0, 0);
                this.pa.stroke(255);
                this.pa.ellipse(600, 300, 200, 200);
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
        for (int i = scores.size() - 1; i >= 0; i--) {
            Pair<Integer, String> p = scores.get(i);
            int pos = scores.size() - i - 1;
            String name = p.value;
            int score = p.comparable;
            pa.fill(0, 0, 0, 50);
            pa.rect(1000, pos * 50, 200, 50, 10);
            pa.fill(255);
            pa.text(name + ": " + score, 1000, pos * 50 + 50);
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

    private void drawHealthBar(float rate, float x, float y) {
        float healthBarLength = 50;
        this.pa.fill(0);
        this.pa.stroke(0);
        this.pa.rect(x - healthBarLength / 2, y - 110, healthBarLength, 5);
        //rate > 0.5 green 255;
        int red = (int) Math.min(510 - (rate * 510), 255);
        int green = (int) Math.min(rate * 510, 255);
        this.pa.fill(red, green, 0);
        this.pa.stroke(red, green, 0);
        this.pa.rect(x - healthBarLength / 2, y - 110, healthBarLength * rate, 5);
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
        } else if (type.equals(DOOD_FISH.name()) || type.equals(GHOST.name())) {
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
