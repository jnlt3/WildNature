package dsekercioglu.client;

import static processing.core.PConstants.BACKSPACE;
import static processing.core.PConstants.DELETE;
import java.awt.Color;
import processing.core.PApplet;

public class TextBox {

    final float WIDTH;
    final float HEIGHT;
    final float RAD = 10;
    String text = "";
    boolean writable;
    long lastTime;
    Color c = new Color(255, 255, 255);
    Color textColor = new Color(0, 0, 0);
    PApplet g;

    TextFitter tf;

    public TextBox(float width, float height, boolean writable, PApplet g) {
        WIDTH = width;
        HEIGHT = height;
        lastTime = System.currentTimeMillis();
        this.g = g;
        this.writable = writable;
        tf = new TextFitter(g);
    }

    public TextBox(float width, float height, boolean writable, Color c, Color textColor) {
        WIDTH = width;
        HEIGHT = height;
        lastTime = System.currentTimeMillis();
        this.writable = writable;
        this.c = c;
        this.textColor = textColor;
    }

    public void draw(float x, float y) {
        long currentTime = System.currentTimeMillis();
        if (g.keyPressed && currentTime - lastTime > 100 && writable) {
            if (Character.isLetterOrDigit(g.key) || g.key == ' ' || g.key == '.' || g.key == '/') {
                text += g.key;
            } else if (g.key == BACKSPACE || g.key == DELETE) {
                if (text.length() > 0) {
                    text = text.substring(0, text.length() - 1);
                }
            }
            lastTime = currentTime;
        }
        g.fill(c.getRGB());
        g.stroke(c.getRGB());
        g.rect(x, y, WIDTH, HEIGHT, RAD);
        tf.draw(text, 14, x + RAD, y, WIDTH - RAD, HEIGHT, textColor);
    }

    public String getText() {
        return text;
    }

    public void setText(String s) {
        text = s;
    }
}
