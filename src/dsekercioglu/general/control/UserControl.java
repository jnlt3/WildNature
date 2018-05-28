package dsekercioglu.general.control;

import dsekercioglu.general.characters.Swimmer;
import dsekercioglu.general.multiPlayer.ControlInfo;
import dsekercioglu.server.Environment;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class UserControl extends Control {

    public UserControl(Swimmer s) {
        super(s);
    }

    @Override
    public void riskControl(ArrayList<Swimmer> chars, float bloodRange, float sightRange, int mouseX, int mouseY, boolean mousePressed) {
        if (Point2D.distance(600, 300, mouseX, mouseY) < 100) {
            stop = true;
        } else {
            stop = false;
        }
        moveAngle = (float) Math.atan2(mouseY - 300, mouseX - 600);
        this.mousePressed = mousePressed; 
    }

    @Override
    public void ownerAttacked() {

    }

    @Override
    public void ownerHit() {

    }

}
