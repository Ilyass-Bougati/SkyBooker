package skybooker.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;


public class LandingpageView {

    @FXML
    private ChoiceBox<String> classes;

    @FXML
    private ChoiceBox<String> departure;

    @FXML
    private ChoiceBox<String> arrival;

    @FXML
    private Button contextMenuTrigger;

    private Popup contextMenu;

    @FXML
    protected void onFindButton() throws IOException
    {
        HelloApplication.fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("searchresults-view.fxml"));
        HelloApplication.scene.setRoot(HelloApplication.fxmlLoader.load());
    }

    @FXML
    public void initialize()
    {
        ((Stage)HelloApplication.scene.getWindow()).setTitle("Welcome to SkyBooker");

        Platform.runLater(this::initializeClasses);
        Platform.runLater(this::initializeLocations);
        Platform.runLater(this::initializePopup);
    }

    private void initializePopup()
    {
        StackPane contextMenuContainer = new StackPane();
        contextMenu = new Popup();

        Rectangle contextMenuBackground = new Rectangle();
        contextMenuBackground.setWidth(200);
        contextMenuBackground.setHeight(200);
        contextMenuBackground.setFill(Color.WHITE);
        contextMenuBackground.setStyle("-fx-max-width: 100% ; -fx-max-height: 100%; -fx-border-radius: 12 ; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian , black , 10 , 0 , 0 , 0)");

        VBox rows = new VBox();

        ArrayList<Label> labels = new ArrayList<>();
        ArrayList<HBox> categories = new ArrayList<>();
        ArrayList<Button> textFields = new ArrayList<>();

        categories.add(new HBox());
        categories.add(new HBox());
        categories.add(new HBox());

        labels.add(new Label("Children"));
        labels.add(new Label("Adults"));
        labels.add(new Label("Seniors"));

        textFields.add(new Button("0"));
        textFields.add(new Button("0"));
        textFields.add(new Button("0"));

        for(int i = 0 ; i < 3 ; i++)
        {
            Label tmpL = labels.get(i) ;
            Button tmpTF = textFields.get(i);

            tmpL.setStyle("-fx-font-family: 'Roboto Light' ; -fx-font-size: 20 ; -fx-font-weight: bold ;");
            tmpTF.setStyle(" -fx-text-alignment: left; -fx-max-width: 50; -fx-min-width: 50; -fx-max-height: 50  ;-fx-min-height: 50 ; -fx-background-color: white ; -fx-font-size: 22.5 ; -fx-font-weight: bold ;-fx-border-radius: 12  ; -fx-effect: innershadow( gaussian, rgba(0,0,0,0.3), 10, 0, 2, 2); -fx-background-radius: 12 ;");
            tmpTF.textProperty().addListener((_)->{
                int sum = 0 ;
                for(Button t : textFields)
                {
                    if(t.getText().isEmpty())
                    {
                        continue;
                    }
                    sum += Integer.parseInt(t.getText());
                }
                contextMenuTrigger.setText(((Integer)sum).toString());
            });

            Rectangle paddingRect = new Rectangle(0,0,Color.TRANSPARENT);
            paddingRect.setWidth(25);

            Button plusButton = new Button("+");
            plusButton.setStyle("-fx-background-color: white ;-fx-font-family: 'Roboto Light' ;-fx-font-weight: bold ;-fx-border-radius: 12  ; -fx-effect: dropshadow( gaussian, rgba(0,0,0,0.3), 10, 0, 0, 0); -fx-background-radius: 12 ; -fx-cursor: hand");
            plusButton.setOnAction(e->{
                int pass = Integer.parseInt(tmpTF.getText());
                pass++;
                tmpTF.setText(String.valueOf(pass));
            });


            Button minusButton = new Button("-");
            minusButton.setStyle("-fx-background-color: white ;-fx-font-family: 'Roboto Light' ;-fx-font-weight: bold ;-fx-border-radius: 12  ; -fx-effect: dropshadow( gaussian, rgba(0,0,0,0.3), 10, 0, 0, 0); -fx-background-radius: 12 ; -fx-cursor: hand");
            minusButton.setOnAction(e->{
                int pass = Integer.parseInt(tmpTF.getText());
                if(pass > 0)
                {
                    pass--;
                }
                tmpTF.setText(String.valueOf(pass));
            });


            categories.get(i).getChildren().add(tmpL);
            categories.get(i).getChildren().add(paddingRect);
            categories.get(i).getChildren().add(plusButton);
            categories.get(i).getChildren().add(tmpTF);
            categories.get(i).getChildren().add(minusButton);

            categories.get(i).setAlignment(Pos.CENTER_RIGHT);
            categories.get(i).setPadding(new Insets(0 ,10 ,0, 10));

            rows.getChildren().add(categories.get(i));
            rows.setAlignment(Pos.CENTER);
        }

        contextMenuContainer.getChildren().add(contextMenuBackground);
        contextMenuContainer.getChildren().add(rows);

        contextMenu.getContent().add(contextMenuContainer);
        contextMenu.setAutoHide(true);

        contextMenuTrigger.setOnAction(e->{
                Bounds bounds = contextMenuTrigger.localToScreen(contextMenuTrigger.getBoundsInLocal());
                contextMenu.show(contextMenuTrigger , bounds.getMinX() , bounds.getMaxY());
        });
    }

    private void initializeClasses()
    {
        classes.setItems(FXCollections.observableArrayList("Economy" , "Business" , "First"));
    }

    private void initializeLocations()
    {
        ArrayList<String> locations = new ArrayList<>();

        locations.add("Casablanca");
        locations.add("Marrakech");
        locations.add("Rabat");
        locations.add("Brussels");
        locations.add("Rome");
        locations.add("Paris");
        locations.add("Barcelona");

        departure.setItems(FXCollections.observableArrayList(locations));
        arrival.setItems(FXCollections.observableArrayList(locations));
    }
}
