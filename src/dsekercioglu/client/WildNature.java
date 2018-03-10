package dsekercioglu.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import dsekercioglu.general.characters.Animal;
import dsekercioglu.general.characters.DrawInfo;
import dsekercioglu.general.multiPlayer.CharacterInfo;
import dsekercioglu.general.multiPlayer.ControlInfo;
import dsekercioglu.general.multiPlayer.PlayerInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import processing.core.PApplet;

public class WildNature extends PApplet {

    private Visualizer v;
    
    private static ArrayList<DrawInfo> characters = new ArrayList();
    private static Animal animal;
    private static String name;
    
    public static Client client;

    @Override
    public void setup() {
        v = new Visualizer(name, animal, this);
        v.setImages();
    }

    @Override
    public void settings() {
        size(1200, 600);
    }

    @Override
    public void draw() {
        clear();
        v.update(characters);
    }

    public static void main(String[] args) {
        try {
            
            Scanner scn = new Scanner(System.in);
            System.out.println("Enter username:");
            name = scn.nextLine();
            
            OUTER:
            while (true) {
                System.out.println("Choose a character...");
                System.out.println("1) Marlin\n2) Black Marlin\n3) Crocodile\n4)"
                        + " Shark\n5) Electric Eel\n6) Barracuda\n7)"
                        + " Mega Mouth\n8) Orca\n9) Hippo\n10) Colossal Squid\n11)"
                        + " DoodFish");
                int characterNo = scn.nextInt();
                switch (characterNo) {
                    case 1:
                        animal = Animal.MARLIN;
                        break OUTER;
                    case 2:
                        animal = Animal.BLACK_MARLIN;
                        break OUTER;
                    case 3:
                        animal = Animal.CROCODILE;
                        break OUTER;
                    case 4:
                        animal = Animal.SHARK;
                        break OUTER;
                    case 5:
                        animal = Animal.ELECTRIC_EEL;
                        break OUTER;
                    case 6:
                        animal = Animal.BARRACUDA;
                        break OUTER;
                    case 7:
                        animal = Animal.MEGA_MOUTH;
                        break OUTER;
                    case 8:
                        animal = Animal.ORCA;
                        break OUTER;
                    case 9:
                        animal = Animal.HIPPO;
                        break OUTER;
                    case 10: 
                        animal = Animal.COLOSSAL_SQUID;
                        break OUTER;
                    case 11:
                        animal = Animal.DOOD_FISH;
                        break OUTER;
                    default:
                        break;
                }
            }
            
            System.out.println("Enter server IP address:");
            String ip = scn.next();
            
            client = new Client();
            new Thread(client).start();
            client.connect(5000, ip, 54555, 54777);
            Kryo kryo = client.getKryo();
            kryo.register(ControlInfo.class);
            kryo.register(CharacterInfo.class);
            kryo.register(PlayerInfo.class);
            kryo.register(ArrayList.class);
            kryo.register(DrawInfo.class);

            client.sendTCP(name + "/" + animal.name());
            client.addListener(new Listener() {
                @Override
                public void received(Connection connection, Object object) {
                    if ((object instanceof CharacterInfo)) {
                        WildNature.characters = ((CharacterInfo) object).characters;
                    }
                }
            });
        } catch (IOException ex) {
            Logger.getLogger(WildNature.class.getName()).log(Level.SEVERE, null, ex);
        }
        PApplet.main(new String[]{WildNature.class.getName()});
    }
    
}
