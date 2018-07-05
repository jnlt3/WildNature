package dsekercioglu.client;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import dsekercioglu.general.characters.Animal;
import dsekercioglu.general.characters.DrawInfo;
import dsekercioglu.general.characters.Team;
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

    private static Visualizer v;

    private static ArrayList<DrawInfo> characters = new ArrayList();
    private static Animal animal;
    private static String name;

    public static Client client;

    @Override
    public void setup() {
        surface.setIcon(loadImage("img/Icon.png"));
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

    public static void connect(String ip, String name, Animal animal, Team team) {
        try {
            WildNature.name = name;
            WildNature.animal = animal;
            client = new Client();
            Kryo kryo = client.getKryo();
            kryo.register(ControlInfo.class);
            kryo.register(CharacterInfo.class);
            kryo.register(PlayerInfo.class);
            kryo.register(ArrayList.class);
            kryo.register(DrawInfo.class);
            kryo.register(Team.class);
            new Thread(client).start();
            client.connect(5000, ip, 54551, 54772);

            PlayerInfo pi = new PlayerInfo();
            pi.character = name + "/" + animal.name();
            pi.team = team;
            client.sendTCP(pi);
            client.addListener(new Listener() {
                @Override
                public void received(Connection connection, Object object) {
                    if ((object instanceof CharacterInfo)) {
                        WildNature.characters = ((CharacterInfo) object).characters;
                    }
                }
            });
            PApplet.main(new String[]{WildNature.class.getName()});
        } catch (IOException ex) {
            Logger.getLogger(WildNature.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
