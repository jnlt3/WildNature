package dsekercioglu.simplifier;

import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.util.Optional;

public class SimplifiedGUI {

    private SimplifiedGUI() {}

    public static Button newButton(String text, Color backgroundColor, EventHandler<ActionEvent> onAction) {
        Button button = new Button();
        button.setText(text);
        button.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, new Insets(1))));
        button.setFont(new Font(14));

        Color textColor;
        if (backgroundColor.getBrightness() <= 0.5) {
            textColor = Color.WHITE;
        } else {
            textColor = Color.BLACK;
        }
        button.setTextFill(textColor);

        button.setOnMouseEntered((eh) -> {
            button.setCursor(Cursor.HAND);
            button.setTextFill(((Color) button.getTextFill()).invert());
            button.setBackground(new Background(new BackgroundFill(
                    backgroundColor.getBrightness() <= 0.5 ? backgroundColor.brighter() : backgroundColor.darker(),
                    CornerRadii.EMPTY, new Insets(1))));
        });
        button.setOnMouseExited((eh) -> {
            button.setTextFill(textColor);
            button.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, new Insets(1))));
        });
        button.setOnAction(onAction);
        return button;
    }

    public static Label newLabel(String text) {
        Label label = new Label();
        label.setText(text);
        label.setFont(new Font(14));
        return label;
    }

    public static TextField newTextField(String text, int prefWidth, boolean isEditable) {
        TextField textField = new TextField();
        textField.setText(text);
        textField.setPrefWidth(prefWidth);
        textField.setEditable(isEditable);
        textField.setFont(new Font(14));
        return textField;
    }

    public static TextArea newTextArea(String text, int prefWidth, int prefHeight, boolean isEditable) {
        TextArea textArea = new TextArea();
        textArea.setText(text);
        textArea.setPrefWidth(prefWidth);
        textArea.setPrefHeight(prefHeight);
        textArea.setEditable(isEditable);
        textArea.setFont(new Font(14));
        return textArea;
    }

    public static CheckBox newCheckBox(String text, boolean isSelected) {
        CheckBox checkBox = new CheckBox();
        checkBox.setText(text);
        checkBox.setFont(new Font(14));
        checkBox.setSelected(isSelected);
        return checkBox;
    }

    public static RadioButton newRadioButton(String text, boolean isSelected, ToggleGroup toggleGroup) {
        RadioButton radioButton = new RadioButton();
        radioButton.setText(text);
        radioButton.setFont(new Font(14));
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setSelected(isSelected);
        return radioButton;
    }

    public static ProgressIndicator newProgressIndicator() {
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setProgress(0);
        return progressIndicator;
    }

    public static ChoiceBox newChoiceBox(int defaultSelection, String... options) {
        ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(options));
        choiceBox.getSelectionModel().select(defaultSelection);
        return choiceBox;
    }

    public static <T> ListView<T> newListView(int prefWidth, int prefHeight, SelectionMode selectionMode) {
        ListView<T> listView = new ListView<>();
        listView.getSelectionModel().setSelectionMode(selectionMode);

        /*
        listView.setCellFactory(new Callback<ListView<T>, ListCell<T>>() {
            @Override
            public ListCell<T> call(ListView<T> list) {
                return new CheckBoxListCell<T>(new Callback<T, ObservableValue<Boolean>>() {
                    @Override
                    public ObservableValue<Boolean> call(T param) {
                        return null;
                    }
                });
            }
        });
         */
        // I don't know whether the following code will work in exceptional cases. // Surplus term gizlemesi için çalışmıyor!!! // Bunu ne için eklemiştim ki??
//        listView.setCellFactory(new Callback<ListView<T>, ListCell<T>>() {
//            @Override
//            public ListCell<T> call(ListView<T> param) {
//                return new ListCell<T>() {
//                    @Override
//                    protected void updateItem(T item, boolean empty) {
//                        super.updateItem(item, empty);
//                        if (item != null) {
//                            // It doesn't have to be a Label!
//                            Label label = newLabel(item.toString());
//                            super.setGraphic(label);
//                        }
//                    }
//                };
//            }
//        });

        ObservableList<T> observableItems = FXCollections.observableArrayList();
        listView.setItems(observableItems);
        listView.setPrefWidth(prefWidth);
        listView.setPrefHeight(prefHeight);
        return listView;
        // listView.getItems().add...
    }

    public static <T> TableView<T> newTableView(String[] columnNames, String... propertyNames) {
        // There should be getters in T !!!

        // Arrays.asList(..., ...);
        TableView<T> tableView = new TableView<>();

        // assert columnNames.length == propertyNames.length
        //tableView.setEditable(false);
        for (int i = 0; i < columnNames.length; i++) {
            TableColumn<T, String> column = new TableColumn<>(columnNames[i]);
            
            column.setCellValueFactory(new PropertyValueFactory<>(propertyNames[i]));
            column.prefWidthProperty().bind(tableView.widthProperty().divide(propertyNames.length)); // Automatic column width
            tableView.getColumns().add(column);
        }

        //ObservableList<Person> data = FXCollections.observableArrayList(new Person("A","B"));
        // ObservableList: addListener, removeListener
        //tableView.setItems(data);
        //tableView.getItems().add(...)
        //tableView.getItems().addAll(...)
        return tableView;
    }

    public static void showDialog(Alert.AlertType alertType, String title, String headerText, String contentText) {
        if (alertType == Alert.AlertType.CONFIRMATION) {
            throw new IllegalArgumentException("Alert.AlertType.CONFIRMATION is not supported.");
        }

        Alert alert;
        if (alertType == null) {
            alert = new Alert(Alert.AlertType.NONE);
        } else {
            alert = new Alert(alertType);
        }

        alert.setTitle(title);
        alert.setHeaderText(headerText); // If it is null, that part will not appear.
        alert.setContentText(contentText);

        alert.showAndWait();

        // for Alert.AlertType.CONFIRMATION
        /*
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            // ... user chose OK
        } else {
            // ... user chose CANCEL or closed the dialog
        }
         */
    }

    public static int showConfirmationDialog(String title, String headerText, String contentText, String cancelButtonText, String... buttonTexts) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        ButtonType[] buttonTypes = new ButtonType[buttonTexts.length];
        for (int i = 0; i < buttonTexts.length; i++) {
            buttonTypes[i] = new ButtonType(buttonTexts[i]);
        }

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(buttonTypes);
        ButtonType buttonTypeCancel = null;
        if (cancelButtonText != null) {
            buttonTypeCancel = new ButtonType(cancelButtonText, ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().add(buttonTypeCancel);
        }

        Optional<ButtonType> result = alert.showAndWait();
        for (int i = 0; i < buttonTexts.length; i++) {
            if (result.get() == buttonTypes[i]) {
                return i;
            }
        }

        return -1; // Cancel or dialog is closed.
    }

    public static String showInputDialog(String title, String headerText, String contentText, String initialValue) {
        if (initialValue == null) {
            initialValue = "";
        }
        TextInputDialog dialog = new TextInputDialog(initialValue);

        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.setContentText(contentText);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    public static String showChoiceDialog(String title, String headerText, String contentText, String... choices) {

        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices[0], choices);

        dialog.setTitle(title);
        dialog.setHeaderText(headerText);
        dialog.setContentText(contentText);

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        } else {
            return null;
        }
    }

    public static FileChooser newFileChooser(String title, String description, String... extensions) {
        // Extensions: "*.*" or "*.txt", "*.jpg", "*.png".
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        if (description != null && extensions != null) {
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(description, extensions));
        }
        return fileChooser;
    }

    public static HBox newHBox(int padding, int spacing, Color backgroundColor, Node... nodes) {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(spacing);
        hBox.setPadding(new Insets(padding));

        if (backgroundColor != null) {
            hBox.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
        }

        for (Node node : nodes) {
            hBox.getChildren().add(node);
        }
        return hBox;
    }

    public static VBox newVBox(int padding, int spacing, Color backgroundColor, Node... nodes) {
        VBox vBox = new VBox();
        vBox.setSpacing(spacing);
        vBox.setPadding(new Insets(padding));

        if (backgroundColor != null) {
            vBox.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
        }

        vBox.getChildren().addAll(Arrays.asList(nodes));
        return vBox;
    }

    public static GridPane newGridPane(int hgap, int vgap, int padding, Color backgroundColor, Node[][] nodes) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(hgap);
        gridPane.setVgap(vgap);
        if (backgroundColor != null) {
            gridPane.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        gridPane.setPadding(new Insets(padding));

        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                if (nodes[i][j] != null) {
                    gridPane.add(nodes[i][j], j, i);
                }
            }
        }
        return gridPane;
    }

    public static ScrollPane newScrollPane(Pane content, int prefWidth, int prefHeight, ScrollPane.ScrollBarPolicy hbarPolicy, ScrollPane.ScrollBarPolicy vbarPolicy) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(content);
        scrollPane.setHbarPolicy(hbarPolicy);
        scrollPane.setVbarPolicy(vbarPolicy);
        scrollPane.setPrefSize(prefWidth, prefHeight);
        return scrollPane;
    }

    public static TabPane newTabPane(String[] tabNames, Pane... panes) {
        // assert true tabNames.length == panes.length
        TabPane tabPane = new TabPane();
        for (int i = 0; i < panes.length; i++) {
            Tab tab = new Tab();
            tab.setText(tabNames[i]);
            tab.setContent(panes[i]);

            tabPane.getTabs().add(tab);
        }
        //tabPane.setSide(Side.TOP);
        //tabPane.setPrefSize(500,300);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        return tabPane;
    }

}
