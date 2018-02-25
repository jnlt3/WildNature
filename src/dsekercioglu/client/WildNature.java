package dsekercioglu.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
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
    private static String character;
    private static String name;
    
    public static Client client;

    @Override
    public void setup() {
        size(1200, 600);
        v = new Visualizer(name, character, this);
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
            
            while (true) {
                System.out.println("Choose a character...");
                System.out.println("1) Marlin\n2) Black Marlin");
                int characterNo = scn.nextInt();
                if (characterNo == 1) {
                    character = "Marlin";
                    break;
                } else if (characterNo == 2) {
                    character = "BlackMarlin";
                    break;
                }
            }
            
            System.out.println("Enter server IP address:");
            String ip = scn.nextLine();
            
            client = new Client();
            new Thread(client).start();
            client.connect(5000, ip, 54556, 54778);
            Kryo kryo = client.getKryo();
            kryo.register(ControlInfo.class);
            kryo.register(CharacterInfo.class);
            kryo.register(PlayerInfo.class);
            kryo.register(ArrayList.class);
            kryo.register(DrawInfo.class);

            client.sendTCP(name + "/" + character);
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
