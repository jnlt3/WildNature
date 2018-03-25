package dsekercioglu.client;

import java.awt.Color;
import processing.core.PApplet;

public class TextFitter {
    
    PApplet g;
    
    public TextFitter(PApplet p) {
        this.g = p;
    }

    private int getBestSize(String s, int maxSize, float w, float h) {
        int size = 1;
        for (int i = maxSize; i > 0; i--) {
            g.textSize(i);
            String[] words = s.split(" ");
            float height = 0;
            int wordIndex = 0;
            while (height < h - i && wordIndex < words.length) {
                float width = 0;
                while (width < w && wordIndex < words.length) {
                    float textWidth = g.textWidth(words[wordIndex] + " ");
                    if (width + textWidth > w) {
                        break;
                    }
                    width += textWidth;
                    wordIndex++;
                }
                height += i;
            }
            if (wordIndex >= words.length - 1) {
                size = i;
                break;
            }
        }
        return size;
    }

    public void draw(String s, int maxSize, float x, float y, float w, float h, Color c) {
        int size = getBestSize(s, maxSize, w, h);
        g.textSize(size);
        g.fill(c.getRGB());
        g.stroke(c.getRGB());
        String[] words = s.split(" ");
        float height = 0;
        int wordIndex = 0;
        while (height < h && wordIndex < words.length) {
            float width = 0;
            while (width < w && wordIndex < words.length) {
                float textWidth = g.textWidth(words[wordIndex] + " ");
                if (width + textWidth > w) {
                    break;
                }
                g.text(words[wordIndex] + " ", x + width, y + height + size);
                width += textWidth;
                wordIndex++;
            }
            height += size;
        }
    }
}
