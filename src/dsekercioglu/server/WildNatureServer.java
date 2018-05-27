package dsekercioglu.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import dsekercioglu.general.characters.Animal;
import dsekercioglu.general.characters.Barracuda;
import dsekercioglu.general.characters.BlackMarlin;
import dsekercioglu.general.characters.ColossalSquid;
import dsekercioglu.general.characters.Crocodile;
import dsekercioglu.general.characters.Dolphin;
import dsekercioglu.general.characters.DoodFish;
import dsekercioglu.general.characters.DrawInfo;
import dsekercioglu.general.characters.ElectricEel;
import dsekercioglu.general.characters.ElectricMarlin;
import dsekercioglu.general.characters.Guardian;
import dsekercioglu.general.characters.Hippo;
import dsekercioglu.general.characters.Marlin;
import dsekercioglu.general.characters.MegaMouth;
import dsekercioglu.general.characters.Orca;
import dsekercioglu.general.characters.Shark;
import dsekercioglu.general.characters.Swimmer;
import dsekercioglu.general.characters.Team;
import dsekercioglu.general.characters.TigerShark;
import dsekercioglu.general.control.UserControl;
import dsekercioglu.general.multiPlayer.CharacterInfo;
import dsekercioglu.general.multiPlayer.ControlInfo;
import dsekercioglu.general.multiPlayer.PlayerInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WildNatureServer {

    private static final HashMap<String, ControlInfo> currentControls = new HashMap();
    private static final Environment env = new Environment();
    public static Server server;

    static int i = 0;

    public static void main(String[] args) throws IOException {
        server = new Server();
        Kryo kryo = server.getKryo();
        kryo.register(ControlInfo.class);
        kryo.register(CharacterInfo.class);
        kryo.register(PlayerInfo.class);
        kryo.register(ArrayList.class);
        kryo.register(DrawInfo.class);
        kryo.register(Team.class);
        server.start();
        server.bind(54551, 54772);

        System.out.println(InetAddress.getLocalHost().getHostAddress());

        server.addListener(new Listener() {
            @Override
            public void connected(Connection cnctn) {
                super.connected(cnctn);
                System.out.println("A player is connected.");
            }

            @Override
            public void received(Connection connection, Object object) {
                if ((object instanceof PlayerInfo)) {
                    PlayerInfo pi = (PlayerInfo) object;
                    String s = pi.character;
                    String name = s.substring(0, s.indexOf("/"));
                    s = s.replace(name + "/", "");
                    Swimmer p = null;
                    System.out.println(s);
                    if (Animal.MARLIN.name().equals(s)) {
                        p = new Marlin(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.BLACK_MARLIN.name().equals(s)) {
                        p = new BlackMarlin(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.CROCODILE.name().equals(s)) {
                        p = new Crocodile(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.SHARK.name().equals(s)) {
                        p = new Shark(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.ELECTRIC_EEL.name().equals(s)) {
                        p = new ElectricEel(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.BARRACUDA.name().equals(s)) {
                        p = new Barracuda(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.MEGA_MOUTH.name().equals(s)) {
                        p = new MegaMouth(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.ORCA.name().equals(s)) {
                        p = new Orca(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.HIPPO.name().equals(s)) {
                        p = new Hippo(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.COLOSSAL_SQUID.name().equals(s)) {
                        p = new ColossalSquid(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.DOOD_FISH.name().equals(s)) {
                        p = new DoodFish(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.ELECTRIC_MARLIN.name().equals(s)) {
                        p = new ElectricMarlin(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.GUARDIAN.name().equals(s)) {
                        p = new Guardian(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.TIGER_SHARK.name().equals(s)) {
                        p = new TigerShark(name, 0.0F, 0.0F, null, env);
                    }else if (Animal.DOLPHIN.name().equals(s)) {
                        p = new Dolphin(name, 0.0F, 0.0F, null, env);
                    }
                    if (p != null) {
                        p.control = new UserControl(p);
                        ControlInfo c = new ControlInfo();
                        c.mouseX = 0;
                        c.mouseY = 0;
                        c.power = false;
                        c.name = name;
                        p.team = pi.team;
                        WildNatureServer.env.addCharacter(p);
                        WildNatureServer.currentControls.put(c.name, c);
                    }
                } else if ((object instanceof ControlInfo)) {
                    ControlInfo controlInfo = (ControlInfo) object;
                    WildNatureServer.currentControls.put(controlInfo.name, controlInfo);
                }
            }

            @Override
            public void disconnected(Connection connection) {
                System.out.println(System.currentTimeMillis());
                System.out.println("Client disconnected.");
            }
        });
        System.out.println("done: " + System.currentTimeMillis());
        Runnable r = () -> {
            while (true) {
                env.update(currentControls);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        new Thread(r).start();
    }

    public static void get2DString(double[][] array) {
        System.out.print("{");
        for (int i = 0; i < array.length; i++) {
            if (i != 0) {
                System.out.print(",");
            }
            System.out.print("{");
            for (int j = 0; j < array[i].length; j++) {
                if (j != 0) {
                    System.out.print(",");
                }
                System.out.print(array[i][j]);
            }
            System.out.print("}");
        }
        System.out.println("};");
    }
}
