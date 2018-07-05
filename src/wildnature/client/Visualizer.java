package wildnature.client;

import com.sun.javafx.geom.Point2D;
import static wildnature.general.Defaults.*;
import wildnature.general.characters.Animal;
import static wildnature.general.characters.Animal.*;
import wildnature.general.characters.DrawInfo;
import wildnature.general.characters.Swimmer;
import wildnature.general.characters.Team;
import wildnature.general.multiPlayer.ControlInfo;
import gifAnimation.Gif;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import processing.core.PApplet;
import static processing.core.PConstants.ENTER;
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
        Gif marlin = new Gif(pa, "src/dsekercioglu/img/Marlin.gif");
        images.put("MARLIN", marlin);

        Gif blackMarlin = new Gif(pa, "src/dsekercioglu/img/BlackMarlin.gif");
        images.put("BLACK_MARLIN", blackMarlin);

        Gif crocodile = new Gif(pa, "src/dsekercioglu/img/Crocodile.gif");
        images.put("CROCODILE", crocodile);

        Gif shark = new Gif(pa, "src/dsekercioglu/img/Shark.gif");
        images.put("SHARK", shark);

        Gif megaMouth = new Gif(pa, "src/dsekercioglu/img/MegaMouth.gif");
        images.put("MEGA_MOUTH", megaMouth);

        Gif orca = new Gif(pa, "src/dsekercioglu/img/Orca.gif");
        images.put("ORCA", orca);

        Gif hippo = new Gif(pa, "src/dsekercioglu/img/Hippo.gif");
        images.put("HIPPO", hippo);

        Gif colossalSquid = new Gif(pa, "src/dsekercioglu/img/ColossalSquid.gif");
        images.put("COLOSSAL_SQUID", colossalSquid);

        Gif doodFish = new Gif(pa, "src/dsekercioglu/img/DoodFish.gif");
        images.put("DOOD_FISH", doodFish);

        Gif electricMarlin = new Gif(pa, "src/dsekercioglu/img/ElectricMarlin.gif");
        images.put("ELECTRIC_MARLIN", electricMarlin);

        Gif guardian = new Gif(pa, "src/dsekercioglu/img/Guardian.gif");
        images.put("GUARDIAN", guardian);

        Gif sharkodile = new Gif(pa, "src/dsekercioglu/img/Sharkodile.gif");
        images.put("SHARKODILE", sharkodile);

        Gif marlinium = new Gif(pa, "src/dsekercioglu/img/Marlinium.gif");
        images.put("MARLINIUM", marlinium);

        Gif miniMarlin = new Gif(pa, "src/dsekercioglu/img/MiniMarlin.gif");
        images.put("MINI_MARLIN", miniMarlin);

        Gif angleraptor = new Gif(pa, "src/dsekercioglu/img/Angleraptor.gif");
        images.put("ANGLERAPTOR", angleraptor);

        Gif tigerShark = new Gif(pa, "src/dsekercioglu/img/TigerShark.gif");
        images.put("TIGER_SHARK", tigerShark);

        Gif dolphin = new Gif(pa, "src/dsekercioglu/img/Dolphin.gif");
        images.put("DOLPHIN", dolphin);

        Gif makoShark = new Gif(pa, "src/dsekercioglu/img/MakoShark.gif");
        images.put("MAKO_SHARK", makoShark);

        Gif twoRulers = new Gif(pa, "src/dsekercioglu/img/TwoRulers.gif");
        images.put("TWO_RULERS", twoRulers);

        Gif ghost = new Gif(pa, "src/dsekercioglu/img/Ghost.gif");
        images.put("GHOST", ghost);

        Gif alien = new Gif(pa, "src/dsekercioglu/img/Alien.gif");
        images.put("ALIEN", alien);

        Gif greenlandShark = new Gif(pa, "src/dsekercioglu/img/GreenlandShark.gif");
        images.put("GREENLAND_SHARK", greenlandShark);

        Set<String> keySet = images.keySet();
        for (String key : keySet) {
            Gif value = images.get(key);
            value.play();
        }

    }

    public void update(ArrayList<DrawInfo> characters) {
        ControlInfo c = new ControlInfo();
        c.mouseX = pa.mouseX;
        c.mouseY = pa.mouseY;
        c.power = pa.mousePressed || (pa.keyPressed && pa.key == ' ');
        c.enter = pa.keyPressed;
        c.name = this.name;
        WildNature.client.sendUDP(c);
        drawGrids();
        boolean blind = false;

        ArrayList<Pair<Integer, String>> scores = new ArrayList<>();
        boolean characterSpotted = false;
        for (int i = 0; i < characters.size(); i++) {
            DrawInfo d = (DrawInfo) characters.get(i);
            scores.add(new Pair(d.score, d.name));
            if (d.name.equals(this.name)) {
                characterSpotted = true;
                blind = d.blind;
                team = d.team;
                if (!d.blind) {
                    drawVision(animal.name(), characters);
                }
                Swimmer.setCenter(d.x, d.y);
                pa.fill(0, 0, 0, 0);
                pa.stroke(255);
                pa.ellipse(600, 300, 200, 200);
                drawEnergyBar(d.energy / d.maxEnergy);
            }
        }
        if (!characterSpotted) {
            pa.fill(255);
            pa.stroke(255);
            pa.textSize(30);
            String text = "Press Any Key to Respawn";
            pa.text(text, 600 - pa.textWidth(text) / 2, 285);
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
        pa.stroke(0, 0, 255);
        pa.fill(0, 0, 255);
        pa.rect(0.0F, 0.0F, 1200.0F, 600.0F);
        pa.stroke(0.0F, 0, 230);
        pa.strokeWeight(3.0F);
        float cx = Swimmer.cx % 60.0F;
        float cy = Swimmer.cy % 60.0F;
        for (int y = 0; y < 600; y += 60) {
            pa.line(0.0F, y - cy, 1200.0F, y - cy);
        }
        for (int x = 0; x < 1200; x += 60) {
            pa.line(x - cx, 0.0F, x - cx, 600.0F);
        }
        pa.stroke(255.0F, 0.0F, 0.0F);
        pa.fill(0.0F, 0.0F, 0.0F, 0.0F);
        pa.rect(-WIDTH - Swimmer.cx + 600.0F, -HEIGHT - Swimmer.cy + 300.0F, WIDTH * 2, HEIGHT * 2);

    }

    private void drawHealthBar(float rate, float x, float y) {
        float healthBarLength = 50;
        pa.fill(0);
        pa.stroke(0);
        pa.rect(x - healthBarLength / 2, y - 110, healthBarLength, 5);

        int red = (int) Math.min(510 - (rate * 510), 255);
        int green = (int) Math.min(rate * 510, 255);
        pa.fill(red, green, 0);
        pa.stroke(red, green, 0);
        pa.rect(x - healthBarLength / 2, y - 110, healthBarLength * rate, 5);

    }
    
    private void drawEnergyBar(double rate) {
        pa.fill(0, 0, 0, 0);
        pa.strokeWeight(3);
        pa.stroke(0, 255, 255);
        pa.rect(0, 600, 20, 600);
        pa.fill(0, 255, 255, 50);
        pa.rect(0, 600, 20, (float) -(600 * rate));
    }

    private void drawVision(String type, ArrayList<DrawInfo> characters) {
        if (type.equals(MARLIN.name()) || type.equals(BLACK_MARLIN.name()) || type.equals(ELECTRIC_MARLIN.name())) {
            pa.fill(0);
            pa.stroke(0);
            for (int i = 0; i < characters.size(); i++) {
                DrawInfo d = characters.get(i);
                if (!d.hiding && Point2D.distance(Swimmer.cx, Swimmer.cy, d.x, d.y) < 2500 && !d.name.equals(name)) {
                    double angle = Math.atan2(d.y - Swimmer.cy, d.x - Swimmer.cx);
                    float x = (float) (600 + Math.cos(angle) * 100);
                    float y = (float) (300 + Math.sin(angle) * 100);
                    pa.ellipse(x, y, 20, 20);
                }
            }
        } else if (type.equals(SHARK.name())) {
            pa.fill(255, 0, 0);
            pa.stroke(255, 0, 0);
            for (int i = 0; i < characters.size(); i++) {
                DrawInfo d = characters.get(i);
                if (d.health != d.maxHealth && Point2D.distance(Swimmer.cx, Swimmer.cy, d.x, d.y) < 6000 && !d.name.equals(name)) {
                    double angle = Math.atan2(d.y - Swimmer.cy, d.x - Swimmer.cx);
                    float x = (float) (600 + Math.cos(angle) * 100);
                    float y = (float) (300 + Math.sin(angle) * 100);
                    pa.ellipse(x, y, 20, 20);
                }
            }
        } else if (type.equals(COLOSSAL_SQUID.name())) {
            pa.fill(0);
            pa.stroke(0);
            for (int i = 0; i < characters.size(); i++) {
                DrawInfo d = characters.get(i);
                if (!d.hiding && Point2D.distance(Swimmer.cx, Swimmer.cy, d.x, d.y) < 2000 && !d.name.equals(name)) {
                    double angle = Math.atan2(d.y - Swimmer.cy, d.x - Swimmer.cx);
                    float x = (float) (600 + Math.cos(angle) * 100);
                    float y = (float) (300 + Math.sin(angle) * 100);
                    pa.ellipse(x, y, 20, 20);
                }
            }
        } else if (type.equals(DOOD_FISH.name()) || type.equals(GHOST.name())) {
            pa.fill(0);
            pa.stroke(0);
            for (int i = 0; i < characters.size(); i++) {
                DrawInfo d = characters.get(i);
                double distance = Point2D.distance(Swimmer.cx, Swimmer.cy, d.x, d.y);
                if (distance < 15000 && !d.name.equals(name)) {
                    double angle = Math.atan2(d.y - Swimmer.cy, d.x - Swimmer.cx);
                    float x = (float) (600 + Math.cos(angle) * 100);
                    float y = (float) (300 + Math.sin(angle) * 100);
                    float radius = 30 - (float) (10 + (distance / 750));
                    pa.ellipse(x, y, radius, radius);
                }
            }
        }

        pa.fill(0);
        pa.stroke(0);
        for (int i = 0; i < characters.size(); i++) {
            DrawInfo d = characters.get(i);
            if (!d.hiding && Point2D.distance(Swimmer.cx, Swimmer.cy, d.x, d.y) < 1500 && !d.name.equals(name)) {
                double angle = Math.atan2(d.y - Swimmer.cy, d.x - Swimmer.cx);
                float x = (float) (600 + Math.cos(angle) * 100);
                float y = (float) (300 + Math.sin(angle) * 100);
                pa.ellipse(x, y, 10, 10);
            }
        }

        pa.fill(0, 255, 0);
        pa.stroke(0, 255, 0);
        for (int i = 0; i < characters.size(); i++) {
            DrawInfo d = characters.get(i);
            if (!d.name.equals(name) && d.team.equals(this.team)) {
                double angle = Math.atan2(d.y - Swimmer.cy, d.x - Swimmer.cx);
                float x = (float) (600 + Math.cos(angle) * 100);
                float y = (float) (300 + Math.sin(angle) * 100);
                pa.ellipse(x, y, 10, 10);
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
