package dsekercioglu.general.control;

import dsekercioglu.general.characters.Swimmer;
import dsekercioglu.general.multiPlayer.ControlInfo;
import java.util.ArrayList;

public abstract class Control {

    Swimmer owner;
    boolean mousePressed;
    boolean stop;
    float moveAngle;

    public Control(Swimmer s) {
        this.owner = s;
    }

    public abstract void riskControl(ArrayList<Swimmer> chars, float bloodRange, float sightRange, int mouseX, int mouseY, boolean mousePressed);

    public boolean mousePressed() {
        return mousePressed;
    }

    public boolean stop() {
        return stop;
    }

    public float moveAngle() {
        return moveAngle;
    }
}
