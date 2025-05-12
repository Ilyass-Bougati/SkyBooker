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
import javafx.scene.control.DatePicker;
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

    @FXML
    private DatePicker date;

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
        Platform.runLater(this::initializeDatePicker);
    }

    private void initializeDatePicker()
    {
        date.getEditor().setDisable(true);
    }

    private void initializePopup()
    {
        StackPane contextMenuContainer = new StackPane();
        contextMenu = new Popup();

        contextMenuContainer.setStyle("-fx-background-color: rgb(255,255,255) ; -fx-border-radius: 12 ; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian , black , 10 , 0 , 0 , 0)");
        contextMenuContainer.setPadding(new Insets(10 , 10, 10, 10));

        VBox rows = new VBox();


        ArrayList<Label> labels = new ArrayList<>();
        ArrayList<Button> counters = new ArrayList<>();
        ArrayList<String> categoriesNames = new ArrayList<>();
        ArrayList<HBox> categoriesContainers = new ArrayList<>();

        categoriesNames.add("Children" );
        categoriesNames.add("Adults" );
        categoriesNames.add("Seniors" );


        for(int i = 0 ; i < categoriesNames.size() ; i++)
        {
            categoriesContainers.add(new HBox());
            labels.add(new Label(categoriesNames.get(i)));
            counters.add(new Button("0"));
            SearchresultsView.passengers.put(categoriesNames.get(i) , 0);
        }


        for(int i = 0 ; i <  categoriesNames.size() ; i++)
        {
            Label tmpL = labels.get(i) ;
            Button tmpTF = counters.get(i);

            tmpL.setStyle("-fx-font-family: 'Roboto Light' ; -fx-font-size: 20 ;");
            tmpTF.setStyle(" -fx-text-alignment: left; -fx-max-width: 50; -fx-min-width: 50; -fx-max-height: 50  ;-fx-min-height: 50 ; -fx-background-color: white ; -fx-font-size: 22.5 ;-fx-border-radius: 12  ; -fx-effect: innershadow( gaussian, rgba(0,0,0,0.3), 10, 0, 2, 2); -fx-background-radius: 12 ;");
            tmpTF.textProperty().addListener((_)->{
                int sum = 0 ;
                for(Button t : counters)
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
            plusButton.setStyle("-fx-background-color: white ;-fx-font-family: 'Roboto Light' ;-fx-border-radius: 12  ; -fx-effect: dropshadow( gaussian, rgba(0,0,0,0.3), 10, 0, 0, 0); -fx-background-radius: 12 ; -fx-cursor: hand");
            plusButton.setOnAction(e->{
                int pass = Integer.parseInt(tmpTF.getText());
                pass++;
                tmpTF.setText(String.valueOf(pass));
            });


            Button minusButton = new Button("-");
            minusButton.setStyle("-fx-background-color: white ;-fx-font-family: 'Roboto Light' ;-fx-border-radius: 12  ; -fx-effect: dropshadow( gaussian, rgba(0,0,0,0.3), 10, 0, 0, 0); -fx-background-radius: 12 ; -fx-cursor: hand");
            minusButton.setOnAction(e->{
                int pass = Integer.parseInt(tmpTF.getText());
                if(pass > 0)
                {
                    pass--;
                }
                tmpTF.setText(String.valueOf(pass));
            });


            categoriesContainers.get(i).getChildren().add(tmpL);
            categoriesContainers.get(i).getChildren().add(paddingRect);
            categoriesContainers.get(i).getChildren().add(plusButton);
            categoriesContainers.get(i).getChildren().add(tmpTF);
            categoriesContainers.get(i).getChildren().add(minusButton);

            categoriesContainers.get(i).setAlignment(Pos.CENTER_RIGHT);
            categoriesContainers.get(i).setPadding(new Insets(0 ,10 ,0, 10));

            rows.getChildren().add(categoriesContainers.get(i));
            rows.setAlignment(Pos.CENTER);
        }

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

        departure.setOnAction(_ ->{
            String initialValue =arrival.getValue() ;
            ArrayList<String> newLocations = new ArrayList<>(locations);
            newLocations.remove(departure.getValue());
            arrival.setItems(FXCollections.observableArrayList(newLocations));
            arrival.setValue(initialValue);
        });

        arrival.setOnAction(_ ->{
            String initialValue =departure.getValue() ;
            ArrayList<String> newLocations = new ArrayList<>(locations);
            newLocations.remove(arrival.getValue());
            departure.setItems(FXCollections.observableArrayList(newLocations));
            departure.setValue(initialValue);
        });
    }

}
