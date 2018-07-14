package wildnature.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import wildnature.general.characters.swimmers.Alien;
import wildnature.general.characters.Animal;
import wildnature.general.characters.swimmers.BlackMarlin;
import wildnature.general.characters.DrawInfo;
import wildnature.general.characters.Swimmer;
import wildnature.general.characters.Team;
import wildnature.general.control.AmbushControl;
import wildnature.general.control.BackTrackControl;
import wildnature.general.control.StraightAttackControl;
import wildnature.general.control.UserControl;
import wildnature.general.multiPlayer.CharacterInfo;
import wildnature.general.multiPlayer.ControlInfo;
import wildnature.general.multiPlayer.PlayerInfo;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import wildnature.general.characters.swimmers.ColossalSquid;
import wildnature.general.characters.swimmers.Crocodile;
import wildnature.general.characters.swimmers.Dolphin;
import wildnature.general.characters.swimmers.DoodFish;
import wildnature.general.characters.swimmers.ElectricMarlin;
import wildnature.general.characters.swimmers.Ghost;
import wildnature.general.characters.swimmers.GreenlandShark;
import wildnature.general.characters.swimmers.Guardian;
import wildnature.general.characters.swimmers.Hippo;
import wildnature.general.characters.swimmers.MakoShark;
import wildnature.general.characters.swimmers.Marlin;
import wildnature.general.characters.swimmers.MegaMouth;
import wildnature.general.characters.swimmers.Orca;
import wildnature.general.characters.swimmers.Shark;
import wildnature.general.characters.swimmers.TigerShark;
import wildnature.general.characters.swimmers.TwoRulers;

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
                    } else if (Animal.DOLPHIN.name().equals(s)) {
                        p = new Dolphin(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.MAKO_SHARK.name().equals(s)) {
                        p = new MakoShark(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.TWO_RULERS.name().equals(s)) {
                        p = new TwoRulers(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.GHOST.name().equals(s)) {
                        p = new Ghost(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.ALIEN.name().equals(s)) {
                        p = new Alien(name, 0.0F, 0.0F, null, env);
                    } else if (Animal.GREENLAND_SHARK.name().equals(s)) {
                        p = new GreenlandShark(name, 0.0F, 0.0F, null, env);
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
                long time = System.currentTimeMillis();
                env.update(currentControls);
                try {
                    Thread.sleep(Math.max(10 - (System.currentTimeMillis() - time), 0));
                } catch (InterruptedException ex) {
                    Logger.getLogger(Environment.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        new Thread(r).start();
    }
}
