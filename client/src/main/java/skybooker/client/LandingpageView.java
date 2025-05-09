package skybooker.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.stage.Stage;
import java.util.ArrayList;


public class LandingpageView {

    @FXML
    private Rectangle container;

    @FXML
    private ChoiceBox<String> classes;

    @FXML
    private Button contextMenuTrigger;

    private Popup contextMenu;

    @FXML
    public void initialize()
    {
        ((Stage)HelloApplication.scene.getWindow()).setTitle("Welcome to SkyBooker");
        initializeClasses();
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
        ArrayList<TextField> textFields = new ArrayList<>();

        categories.add(new HBox());
        categories.add(new HBox());
        categories.add(new HBox());

        labels.add(new Label("Children"));
        labels.add(new Label("Adults"));
        labels.add(new Label("Seniors"));

        textFields.add(new TextField("0"));
        textFields.add(new TextField("0"));
        textFields.add(new TextField("0"));

        for(int i = 0 ; i < 3 ; i++)
        {
            Label tmpL = labels.get(i) ;
            TextField tmpTF = textFields.get(i);

            tmpL.setStyle("-fx-font-family: 'Roboto Light' ; -fx-font-size: 17 ; -fx-font-weight: bold ;");
            tmpTF.setStyle("-fx-max-width: 60; -fx-pref-height: 60 ; -fx-background-color: white ; -fx-font-size: 22.5 ; -fx-font-weight: bold ;-fx-border-radius: 12  ; -fx-effect: innershadow( gaussian, rgba(0,0,0,0.3), 10, 0, 2, 2); -fx-background-radius: 12 ;");
            tmpTF.textProperty().addListener((_)->{
                int sum = 0 ;
                for(TextField t : textFields)
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
            paddingRect.setWidth(70);

            categories.get(i).getChildren().add(tmpL);
            categories.get(i).getChildren().add(paddingRect);
            categories.get(i).getChildren().add(tmpTF);

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
}
