package dsekercioglu.client;

import dsekercioglu.general.characters.Animal;
import dsekercioglu.general.characters.Team;
import dsekercioglu.simplifier.SimplifiedGUI;
import java.io.File;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Label ip_label = SimplifiedGUI.newLabel("Enter IP:");
        TextField ip_textField = SimplifiedGUI.newTextField("", 150, true);

        Label name_label = SimplifiedGUI.newLabel("Enter Name:");
        TextField name_textField = SimplifiedGUI.newTextField("", 150, true);

        Label team_label = SimplifiedGUI.newLabel("Select Team:");
        ChoiceBox team_choiceBox = SimplifiedGUI.newChoiceBox(0, "RED", "GREEN", "BLUE", "YELLOW", "INDEPENDENT");

        Node[][] gridNodes = {{ip_label, name_label, team_label},
        {ip_textField, name_textField, team_choiceBox}};

        GridPane gridPane = SimplifiedGUI.newGridPane(20, 10, 30, Color.ALICEBLUE, gridNodes);

        ToggleGroup toggleGroup = new ToggleGroup();

        Button btn = SimplifiedGUI.newButton("Run game", Color.GOLD, (a) -> {

            String ip = ip_textField.getText();
            String name = name_textField.getText();
            int selectedIndex = team_choiceBox.getSelectionModel().getSelectedIndex();
            Team team = Team.values()[selectedIndex];
            Animal animal = Animal.values()[toggleGroup.getToggles().indexOf(toggleGroup.getSelectedToggle())];
            WildNature.connect(ip, name, animal, team);

        });

        Label character_label = SimplifiedGUI.newLabel("Select Character:");

        String[] fileNames = {"Marlin", "BlackMarlin", "Crocodile", "Shark",
            "ElectricEel", "Barracuda", "MegaMouth", "Orca", "Hippo", "ColossalSquid",
            "DoodFish", "ElectricMarlin", "Guardian"};
        RadioButton[] chars = new RadioButton[fileNames.length];
        for (int i = 0; i < fileNames.length; i++) {
            chars[i] = SimplifiedGUI.newRadioButton("", true, toggleGroup);
            File imageFile = new File("img/" + fileNames[i] + ".png");
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

        GridPane characterPane = SimplifiedGUI.newGridPane(10, 10, 15, Color.ALICEBLUE, characterNodes);
        Node[] nodes = {gridPane, character_label, characterPane, btn};
        VBox root = SimplifiedGUI.newVBox(30, 30, Color.ALICEBLUE, nodes);

        stage.setTitle("Wild Nature");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

    }

}
