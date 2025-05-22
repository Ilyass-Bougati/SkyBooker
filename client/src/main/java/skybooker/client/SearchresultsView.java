package skybooker.client;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Popup;
import javafx.util.Duration;
import utils.GeneralUtils;

import java.io.IOException;
import java.util.*;

public class SearchresultsView {

    @FXML
    private ChoiceBox<String> classes;

    @FXML
    private Button contextMenuTrigger;

    @FXML
    private DatePicker date;

    @FXML
    private VBox container;

    @FXML
    private HBox page;

    @FXML
    private VBox contentContainer;

    @FXML
    private VBox content;

    @FXML
    private ScrollPane scrollable;

    private Popup contextMenu ;

    private bookPopup bp = null;
    private final ArrayList<ArrayList<String>> Rows = new ArrayList<>();
    private final HashMap<Button , Integer> buttonDictionnary = new HashMap<>();
    public static String className = "Economy" , departure , arrival;

    public static HashMap<String , Integer> passengers = new HashMap<>();

    private class bookPopup extends Popup{
        private Button closeButton;
        private int buttonId ;
        private final ScrollPane container;
        private final VBox subcontainer;

        public bookPopup(int buttonId)
        {
            super();

            this.container = new ScrollPane();

            this.container.setMinWidth(500);
            this.container.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
            this.container.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            this.container.setMinHeight(680);
            this.container.setMaxHeight(680);

            this.container.setStyle("-fx-effect: dropshadow(gaussian , rgba(0,0,0,0.45) , 10 , 0 , 0 , 0) ; -fx-background-radius: 12 ;-fx-border-radius: 12 ;-fx-background-color: white ;");

            this.buttonId = buttonId;

            this.setX(HelloApplication.getScene().getWindow().getX() + HelloApplication.getScene().getWidth()*0.5 - 340);
            this.setY(HelloApplication.getScene().getWindow().getY() + HelloApplication.getScene().getHeight()*0.5 - 340);

            this.subcontainer = new VBox();
            this.subcontainer.setStyle("-fx-background-color: white ; -fx-background-radius: 12 ; -fx-border-radius: 12");
            this.subcontainer.setMouseTransparent(false);
            this.subcontainer.setMinWidth(650);
            this.subcontainer.setMaxWidth(650);
            this.subcontainer.setSpacing(20);

            this.container.getStylesheets().add(Objects.requireNonNull(getClass().getResource("style.css")).toExternalForm());

            closeButton = new Button("x");
            closeButton.setStyle("-fx-background-color: rgba(0,166,255,0.38) ;-fx-text-fill: white ; -fx-background-radius: 12 ; -fx-border-radius: 12 ; -fx-font-size: 20 ");
            closeButton.setMinHeight(57);
            closeButton.setMinWidth(57);

            closeButton.setOnAction(_ -> {
                page.setEffect(null);
                this.hide();
            });

            this.subcontainer.getChildren().add(closeButton);
            this.container.setContent(this.subcontainer);
            this.getContent().add(container);

            HelloApplication.getScene().getWindow().xProperty().addListener((_) ->{
                this.setX(HelloApplication.getScene().getWindow().getX() + HelloApplication.getScene().getWidth()*0.5 - 340);
            });

            HelloApplication.getScene().getWindow().yProperty().addListener((_) ->{
                this.setY(HelloApplication.getScene().getWindow().getY() + HelloApplication.getScene().getHeight()*0.5 - 340);
            });

            initialize();
        }
        public void initialize()
        {
            this.subcontainer.getChildren().retainAll(this.closeButton);
            int passengerAmount = Integer.parseInt(contextMenuTrigger.getText());
            for(int i = 0 ; i < passengerAmount ; i++)
            {
                Label inputLabel = new Label("Passenger number :" + ((Integer)(i+1)));

                inputLabel.setStyle("-fx-font-family: 'Roboto Light';-fx-font-size: 22.5 ; -fx-font-weight: bold ;");

                TextField fName = new TextField();
                fName.setPromptText("First name");
                fName.setStyle("-fx-font-family: 'Roboto Light';-fx-background-color: white ; -fx-font-size: 22.5 ; -fx-font-weight: bold ;-fx-border-radius: 12  ; -fx-effect: innershadow( gaussian, rgba(0,0,0,0.3), 10, 0, 2, 2); -fx-background-radius: 12 ;");
                fName.setMaxWidth(257);
                fName.setMinWidth(257);


                TextField lName = new TextField();
                lName.setPromptText("Last name");
                lName.setStyle("-fx-font-family: 'Roboto Light';-fx-background-color: white ; -fx-font-size: 22.5 ; -fx-font-weight: bold ;-fx-border-radius: 12  ; -fx-effect: innershadow( gaussian, rgba(0,0,0,0.3), 10, 0, 2, 2); -fx-background-radius: 12 ;");
                lName.setMaxWidth(257);
                lName.setMinWidth(257);

                TextField CIN = new TextField();
                CIN.setPromptText("CIN");
                CIN.setStyle(" -fx-font-family: 'Roboto Light'; -fx-background-color: white ; -fx-font-size: 22.5 ; -fx-font-weight: bold ;-fx-border-radius: 12  ; -fx-effect: innershadow( gaussian, rgba(0,0,0,0.3), 10, 0, 2, 2); -fx-background-radius: 12 ;");
                CIN.setMaxWidth(157);
                CIN.setMinWidth(157);

                HBox inputsContainer = new HBox(fName , lName , CIN);
                inputsContainer.setSpacing(20);
                inputsContainer.setAlignment(Pos.CENTER);

                this.subcontainer.getChildren().addAll(inputLabel , inputsContainer , new Separator());
            }
            this.subcontainer.getChildren().removeLast();

        }
        public void updateId(int newId)
        {
            this.buttonId = newId;
            initialize();
        }
    }

    @FXML
    protected void onBackButton()
    {
        ParallelTransition pt = new ParallelTransition(GeneralUtils.fadeOutAnimation(contentContainer , 500)  ,
                GeneralUtils.fadeOutAnimation(content , 500) ,
                GeneralUtils.fadeOutAnimation(scrollable , 500));
        pt.setOnFinished(_->{
            try{
                GeneralUtils.loadView("landingpage-view.fxml");
            }catch (IOException ioe)
            {
                throw new RuntimeException("An error occured while loading view");
            }
        });
        pt.playFromStart();
    }

    @FXML
    public void initialize()
    {
        Platform.runLater(()->{
            initializePopup();
            initializeClasses();
            GeneralUtils.initializeDatePicker(date);
            populateRows();
            displayRows();

            TranslateTransition closeInPage = new TranslateTransition();

            closeInPage.setFromY(HelloApplication.getScene().getWindow().getHeight() * 0.5 - 100);
            closeInPage.setToY(0);
            closeInPage.setNode(contentContainer);
            closeInPage.setDuration(new Duration(500));

            ParallelTransition pt = new ParallelTransition(closeInPage  ,
                    GeneralUtils.fadeInAnimation(contentContainer , 500)  ,
                    GeneralUtils.fadeInAnimation(content , 500) ,
                    GeneralUtils.fadeInAnimation(scrollable , 500));
            pt.playFromStart();
        });
    }

    private void initializeClasses()
    {
        classes.setItems(FXCollections.observableArrayList("Economy" , "Business" , "First"));
        classes.setValue(className);
    }

    private void initializePopup()
    {
        int isum = 0;

        for(String s : passengers.keySet()){
            isum += passengers.get(s);
        }

        contextMenuTrigger.setText(((Integer)isum).toString());

        StackPane contextMenuContainer = new StackPane();
        contextMenu = new Popup();

        contextMenuContainer.setStyle("-fx-background-color: rgb(255,255,255) ; -fx-border-radius: 12 ; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian , black , 10 , 0 , 0 , 0)");
        contextMenuContainer.setPadding(new Insets(10 , 10, 10, 10));

        VBox rows = new VBox();


        ArrayList<Label> labels = new ArrayList<>();
        ArrayList<Button> counters = new ArrayList<>();
        Set<String> categoriesNames = passengers.keySet();
        ArrayList<HBox> categoriesContainers = new ArrayList<>();


        for (String tmpName : categoriesNames) {
            categoriesContainers.add(new HBox());
            labels.add(new Label(tmpName));
            counters.add(new Button(passengers.get(tmpName).toString()));
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

            Rectangle paddingRect = new Rectangle(0,0, Color.TRANSPARENT);
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

    private void populateRows()
    {
        for(int i = 0 ; i < 200 ; i ++)
        {
            ArrayList<String> Row = new ArrayList<>();
            Row.add("Airline" + i );
            Row.add("\t\tDPT" + i);
            Row.add("\t00:00");
            Row.add("\t00:00");
            Row.add("\tARR" + i);
            Row.add("\t\t100" + i + "$");
            Rows.add(Row);
        }
    }

    private void displayRows()
    {
        int i = 0;
        for(ArrayList<String> row : Rows)
        {
            Button rowButton = new Button();
            StringBuilder buttonLabel = new StringBuilder();
            for(String s : row)
            {
                buttonLabel.append(s);
            }
            rowButton.setText(buttonLabel.toString());
            rowButton.setAlignment(Pos.CENTER);
            rowButton.setStyle("-fx-background-color: rgb(255,255,255) ; -fx-cursor: hand; -fx-font-weight: bold ; -fx-text-fill: #3434af ; -fx-font-family: 'Roboto Light'; -fx-font-size: 20 ; -fx-text-alignment: center ; -fx-min-width: 674 ; -fx-max-width: 674 ; -fx-min-height: 57 ; -fx-max-height: 57 ;");
            rowButton.setOnAction(_ -> {
                int id = buttonDictionnary.get(rowButton);
                if(bp == null)
                {
                    bp = new bookPopup(id);
                }
                else{
                    bp.updateId(id);
                }
                BoxBlur bb = new BoxBlur(5,5,3);
                page.setEffect(bb);
                bp.show(HelloApplication.getScene().getWindow());
            });

            buttonDictionnary.put(rowButton,i++);

            container.getChildren().add(rowButton);
            container.getChildren().add(new Separator());
        }
        container.getChildren().removeLast();
    }
}
