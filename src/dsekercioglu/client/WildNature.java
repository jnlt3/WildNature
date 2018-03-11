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

            while (true) {
                System.out.println("Choose a character...");

                Animal[] animals = Animal.values();
                for (int i = 0; i < animals.length; i++) {
                    System.out.println((i + 1) + ")" + animals[i].name().replaceAll("_", " "));
                }

                int characterNo = scn.nextInt();

                if (characterNo >= 1 && characterNo <= Animal.values().length) {
                    animal = animals[characterNo - 1];
                    break;
                }
            }

            Team team;
            while (true) {
                System.out.println("Choose your favorite color...");

                Team[] teams = Team.values();
                for (int i = 0; i < teams.length; i++) {
                    System.out.println((i + 1) + ")" + teams[i].name());
                }

                int characterNo = scn.nextInt();

                if (characterNo >= 1 && characterNo <= Animal.values().length) {
                    team = teams[characterNo - 1];
                    break;
                }
            }

            System.out.println("Enter server IP address:");
            String ip = scn.next();

            client = new Client();
            Kryo kryo = client.getKryo();
            kryo.register(ControlInfo.class);
            kryo.register(CharacterInfo.class);
            kryo.register(PlayerInfo.class);
            kryo.register(ArrayList.class);
            kryo.register(DrawInfo.class);
            kryo.register(Team.class);
            new Thread(client).start();
            client.connect(5000, ip, 54555, 54777);

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
        } catch (IOException ex) {
            Logger.getLogger(WildNature.class.getName()).log(Level.SEVERE, null, ex);
        }
        PApplet.main(new String[]{WildNature.class.getName()});
    }

}
