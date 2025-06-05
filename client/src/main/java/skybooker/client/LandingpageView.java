package skybooker.client;

import DTO.VilleDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.UnauthorizedException;
import javafx.animation.ParallelTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
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
import okhttp3.ResponseBody;
import requests.Client;
import requests.ClientCache;
import utils.GeneralUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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

    @FXML
    private StackPane container;

    @FXML
    private VBox imageContainer;

    @FXML
    private VBox contentContainer;

    private Popup contextMenu;

    List<VilleDTO> villes;

    @FXML
    protected void onFindButton() {
        String arrivalCityName = arrival.getValue();
        String departureCityName = departure.getValue();

        if(arrivalCityName.equals("Departure") || departureCityName.equals("Arrival")) {
            // TODO : add some error handling here
            return;
        }

        SearchresultsView.className = classes.getValue();

        // TODO : this could be refactored but I'm not very familliar with this code
        // getting the ids of the cities
        Optional<VilleDTO> arrivalCity = villes.stream().filter(v -> v.getNom().equals(arrivalCityName)).findFirst();
        Optional<VilleDTO> departureCity = villes.stream().filter(v -> v.getNom().equals(departureCityName)).findFirst();

        if (arrivalCity.isPresent() && departureCity.isPresent()) {
            SearchresultsView.arrival = arrivalCity.get().getId();
            SearchresultsView.departure = departureCity.get().getId();
        } else {
            // TODO : add some error handling
            return;
        }



        ParallelTransition pt = new ParallelTransition(GeneralUtils.fadeOutAnimation(imageContainer , 500) ,
                GeneralUtils.fadeOutAnimation(contentContainer , 500));


        pt.setOnFinished(_-> {
            try {
                GeneralUtils.loadView("searchresults-view.fxml");
            } catch (IOException e) {
                throw new RuntimeException("An error occurred while loading view");
            }
        });

        pt.playFromStart();

    }

    @FXML
    public void initialize() {
        Platform.runLater(() ->
        {
            GeneralUtils.fadeInAnimation(container , 500).play();
            initializeClasses();
            try {
                initializeLocations();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            initializePopup();
            GeneralUtils.initializeDatePicker(date, new GeneralUtils.DateVerifier() {
                @Override
                public Boolean call() {
                    if(getCell() != null){
                        return getCell().getItem().isAfter(LocalDate.now());
                    }
                    throw new RuntimeException();
                }
            });
        });
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

        categoriesNames.add("Adults" );
        categoriesNames.add("Children" );
        categoriesNames.add("Seniors" );


        for (String categoriesName : categoriesNames) {
            categoriesContainers.add(new HBox());
            labels.add(new Label(categoriesName));
            counters.add(new Button("0"));
            SearchresultsView.passengers.put(categoriesName, 0);
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

            String key = categoriesNames.get(i);

            Button plusButton = new Button("+");
            plusButton.setStyle("-fx-background-color: white ;-fx-font-family: 'Roboto Light' ;-fx-border-radius: 12  ; -fx-effect: dropshadow( gaussian, rgba(0,0,0,0.3), 10, 0, 0, 0); -fx-background-radius: 12 ; -fx-cursor: hand");
            plusButton.setOnAction(e->{
                int pass = Integer.parseInt(tmpTF.getText());
                pass++;
                tmpTF.setText(String.valueOf(pass));
                SearchresultsView.passengers.replace(key , pass);
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
                SearchresultsView.passengers.replace(key , pass);
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
        // TODO : fetch the classes
        classes.setItems(FXCollections.observableArrayList("Economy" , "Business" , "First"));
    }

    private void initializeLocations() throws IOException {
        // Fetching the cities
        ObjectMapper mapper = new ObjectMapper();
        try {
            String res = Client.get("/ville/");
            villes = mapper.readValue(res, new TypeReference<List<VilleDTO>>(){});
            // caching the villes
            for (VilleDTO ville : villes) {
                ClientCache.add(ville);
            }
        } catch (Exception e) {
            /*
             TODO : here we should redirect the user to a page that tells them
             that we're out of service for now, since that's the only reason for
             an exception to show up here, maybe we can add another route
             that checks the health of the backend
             */
            return;
        }

        ArrayList<String> locations = new ArrayList<>();
        villes.forEach(v -> locations.add(v.getNom()));

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
