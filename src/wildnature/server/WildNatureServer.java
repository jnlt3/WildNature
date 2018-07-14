package wildnature.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.util.Util;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import java.io.File;
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
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import wildnature.client.WildNature;
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
import wildnature.simplifier.SimplifiedGUI;

public class WildNatureServer extends Application  {

    private static final HashMap<String, ControlInfo> currentControls = new HashMap();
    private static final Environment env = new Environment();
    public static Server server;

    static int i = 0;

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        
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
                    
                    try {
                        p = Utilities.getSwimmer(s, name, env);
                    } catch (Utilities.NoSuchAnimalException ex) {
                        Logger.getLogger(WildNatureServer.class.getName()).log(Level.SEVERE, null, ex);
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
       
        Label name_label = SimplifiedGUI.newLabel("Enter Name:");
        TextField name_textField = SimplifiedGUI.newTextField("", 150, true);

        ToggleGroup toggleGroup = new ToggleGroup();
        
        ListView<String> x = SimplifiedGUI.newListView(300, 100, SelectionMode.SINGLE);
        
        Button btn = SimplifiedGUI.newButton("Add", Color.GOLD, (a) -> {

            String name = name_textField.getText();
            name_textField.setText("");
            Animal animal = Animal.values()[toggleGroup.getToggles().indexOf(toggleGroup.getSelectedToggle())];
            //Swimmer m = new Hippo("3", 0, 0, null, this);
            Swimmer swimmer = null;
            try {
                swimmer = Utilities.getSwimmer(animal.name(), name, env);
            } catch (Utilities.NoSuchAnimalException ex) {
                Logger.getLogger(WildNatureServer.class.getName()).log(Level.SEVERE, null, ex);
            }
            swimmer.team = Team.INDEPENDENT;
            swimmer.control = new StraightAttackControl(swimmer, env);
            env.addCharacter(swimmer);
            
            ObservableList<String> items = x.getItems();
            items.add(name);

        });
        
        Node[][] gridNodes = {{name_label},{name_textField, btn}};

        GridPane gridPane = SimplifiedGUI.newGridPane(20, 10, 30, Color.ALICEBLUE, gridNodes);

        


        
        

        Label character_label = SimplifiedGUI.newLabel("Select character to add...");

        String[] fileNames = {"Marlin", "BlackMarlin", "Crocodile", "Shark", "Hippo",
            "MegaMouth", "Orca", "ColossalSquid", "Guardian",
            "TigerShark", "Dolphin", "MakoShark", "GreenlandShark", "ElectricMarlin", "TwoRulers", "DoodFish",
            "Alien", "Sharkodile", "Marlinium", "MiniMarlin"};
        
        RadioButton[] chars = new RadioButton[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            chars[i] = SimplifiedGUI.newRadioButton("", true, toggleGroup);
            File imageFile = new File("resources/" + fileNames[i] + ".png");
            Image image = new Image(imageFile.toURI().toString());
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(150);
            chars[i].setGraphic(imageView);
        }

        int numOfRows = 3;
        Node[][] characterNodes = new Node[numOfRows][(int) Math.ceil((double) fileNames.length / numOfRows)];
        int k = 0;
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < characterNodes[i].length; j++) {
                if (k == fileNames.length) {
                    break;
                }
                characterNodes[i][j] = chars[k];
                k++;
            }
        }

        Node[][] swimmersGridNodes = {{x}};
        GridPane swimmersPane = SimplifiedGUI.newGridPane(20, 10, 30, Color.ALICEBLUE, swimmersGridNodes);
        
        GridPane characterPane = SimplifiedGUI.newGridPane(10, 10, 15, Color.ALICEBLUE, characterNodes);
        
        Node[] nodes = {character_label, characterPane, gridPane, swimmersPane};
        VBox root = SimplifiedGUI.newVBox(30, 30, Color.ALICEBLUE, nodes);

        stage.setTitle("Wild Nature");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
