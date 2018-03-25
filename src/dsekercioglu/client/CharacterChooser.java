package dsekercioglu.client;

import dsekercioglu.general.characters.Animal;
import static dsekercioglu.general.characters.Animal.*;
import java.util.ArrayList;
import processing.core.PApplet;
import static processing.core.PConstants.CENTER;
import static processing.core.PConstants.ENTER;
import static processing.core.PConstants.LEFT;
import static processing.core.PConstants.RIGHT;
import processing.core.PImage;

public class CharacterChooser {

    PApplet p;
    ArrayList<CharacterBox> characters = new ArrayList<>();
    int index = 0;

    Animal chosenCharacter;

    public CharacterChooser(PApplet p) {
        this.p = p;
        characters.add(new CharacterBox(600, 300, 400, 400, p.loadImage("img/Marlin.png"), MARLIN));
        characters.add(new CharacterBox(600, 300, 400, 400, p.loadImage("img/BlackMarlin.png"), BLACK_MARLIN));
        characters.add(new CharacterBox(600, 300, 400, 400, p.loadImage("img/Crocodile.png"), CROCODILE));
        characters.add(new CharacterBox(600, 300, 400, 400, p.loadImage("img/Shark.png"), SHARK));
        characters.add(new CharacterBox(600, 300, 400, 400, p.loadImage("img/ElectricEel.png"), ELECTRIC_EEL));
        characters.add(new CharacterBox(600, 300, 400, 400, p.loadImage("img/Barracuda.png"), BARRACUDA));
        characters.add(new CharacterBox(600, 300, 400, 400, p.loadImage("img/MegaMouth.png"), MEGA_MOUTH));
        characters.add(new CharacterBox(600, 300, 400, 400, p.loadImage("img/Orca.png"), ORCA));
        characters.add(new CharacterBox(600, 300, 400, 400, p.loadImage("img/Hippo.png"), HIPPO));
        characters.add(new CharacterBox(600, 300, 400, 400, p.loadImage("img/ColossalSquid.png"), COLOSSAL_SQUID));
        //characters.add(new CharacterBox(600, 300, 400, 400, p.loadImage("img/Doodfish.png"), DOOD_FISH));
        characters.add(new CharacterBox(600, 300, 400, 400, p.loadImage("img/ElectricMarlin.png"), ELECTRIC_MARLIN));
        //characters.add(new CharacterBox(600, 300, 400, 400, p.loadImage("img/Guardian.png"), GUARDIAN));
    }

    public boolean update() {
        p.fill(0);
        p.stroke(0);
        p.rect(0, 0, 1200, 600);
        if (p.keyPressed && p.frameCount % 6 == 0) {
            if (p.keyCode == RIGHT) {
                index += 1;
            } else if (p.keyCode == LEFT) {
                index -= 1;
            }
            index = (index + characters.size()) % characters.size();
        }
        if (p.keyPressed && p.key == ENTER) {
            chosenCharacter = characters.get(index).name;
            return true;
        }
        characters.get(index).draw();
        return false;
    }

    private class CharacterBox {

        float x;
        float y;
        float width;
        float height;
        PImage img;
        Animal name;

        public CharacterBox(float x, float y, float width, float height, PImage img, Animal name) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.img = img;
            this.name = name;
            if (img.width > img.height) {
                img.resize((int) width, 0);
            } else {
                img.resize(0, (int) height);
            }
        }

        public void draw() {
            p.fill(255);
            p.stroke(255);
            p.rect(x - width / 2, y - height / 2, width, height);
            p.imageMode(CENTER);
            p.image(img, x, y);
        }
    }
}
