package dsekercioglu.server;

import dsekercioglu.general.characters.Swimmer;
import dsekercioglu.general.multiPlayer.CharacterInfo;
import dsekercioglu.general.multiPlayer.ControlInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class Environment {

    public ArrayList<Swimmer> characters = new ArrayList();
    private final int WIDTH = 5000;
    private final int HEIGHT = 5000;

    public Environment() {
        this.characters = new ArrayList();
    }

    public void update(HashMap<String, ControlInfo> hashMap) {
        for (int i = 0; i < this.characters.size(); i++) {
            Swimmer swimmer = (Swimmer) this.characters.get(i);
            ControlInfo c = (ControlInfo) hashMap.get(swimmer.getName());
            swimmer.update(c.mouseX, c.mouseY, c.mousePressed);
            swimmer.x = Math.max(-WIDTH, Math.min(swimmer.x, WIDTH));
            swimmer.y = Math.max(-HEIGHT, Math.min(swimmer.y, HEIGHT));
        }
        CharacterInfo ci = new CharacterInfo();
        ci.characters = new ArrayList();
        for (int i = 0; i < this.characters.size(); i++) {
            ci.characters.add(((Swimmer) this.characters.get(i)).getDrawInfo());
        }
        WildNatureServer.server.sendToAllUDP(ci);
    }

    public void addCharacter(Swimmer s) {
        this.characters.add(s);
    }
    
    
    public void checkHits() {
        
    }
}
