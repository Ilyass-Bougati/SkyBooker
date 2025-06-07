package skybooker.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import skybooker.client.DTO.VilleDTO;
import skybooker.client.requests.Client;
import skybooker.client.requests.ClientCache;
import skybooker.client.utils.GeneralUtils;

import java.util.*;

public class BookPopupController {
    public static Stage window;

    @FXML
    ChoiceBox<String> departureFlight;

    @FXML
    ChoiceBox<String> arrivalFlight;

    @FXML
    DatePicker departureDate;

    Map<String, Long> cityNameIdMap = new HashMap<>();

    private void populateLists()
    {
        // fetching the cities
        ObjectMapper mapper = new ObjectMapper();
        List<VilleDTO> villes;
        try {
            String res = Client.get("/ville/");
            villes = mapper.readValue(res, new TypeReference<List<VilleDTO>>(){});
            // caching the villes
            for (VilleDTO ville : villes) {
                ClientCache.add(ville);
                cityNameIdMap.put(ville.getNom(), ville.getId());
                departureFlight.getItems().add(ville.getNom());
                arrivalFlight.getItems().add(ville.getNom());
            }

            departureFlight.setOnAction(_ -> {
                List<String> newCityList = new ArrayList<>(cityNameIdMap.keySet());
                newCityList.remove(departureFlight.getValue());

                String currentValue = arrivalFlight.getValue();
                arrivalFlight.setItems(FXCollections.observableArrayList(newCityList));
                arrivalFlight.setValue(currentValue);
            });

            arrivalFlight.setOnAction(_ ->{
                List<String> newCityList = new ArrayList<>(cityNameIdMap.keySet());
                newCityList.remove(arrivalFlight.getValue());

                String currentValue = departureFlight.getValue();
                departureFlight.setItems(FXCollections.observableArrayList(newCityList));
                departureFlight.setValue(currentValue);
            });

        } catch (Exception e) {
            window.close();
            window.getOwner().getScene().getRoot().setEffect(null);
        }
    }

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            populateLists();
        });
    }

    @FXML
    protected void onPreferences()
    {
        if (arrivalFlight.getValue().equals("Arrival") || departureFlight.getValue().equals("Departure") || departureDate.getValue() == null)
        {
            return;
        }

        SearchResultsController.setArrivalFlight(cityNameIdMap.get(arrivalFlight.getValue()));
        SearchResultsController.setDepartureFlight(cityNameIdMap.get(departureFlight.getValue()));
        SearchResultsController.setDepartureDate(departureDate);

        PreferencesController.isComingFromBookPopup = true;
        GeneralUtils.loadPreferences();
        window.close();
        window.getOwner().getScene().getRoot().setEffect(null);
    }

    @FXML
    protected void onSearchButton()
    {
        if (arrivalFlight.getValue().equals("Arrival") || departureFlight.getValue().equals("Departure") || departureDate.getValue() == null)
        {
            return;
        }

        SearchResultsController.setArrivalFlight(cityNameIdMap.get(arrivalFlight.getValue()));
        SearchResultsController.setDepartureFlight(cityNameIdMap.get(departureFlight.getValue()));
        SearchResultsController.setDepartureDate(departureDate);

        GeneralUtils.changeView("searchresults-view.fxml");
        window.close();
        window.getOwner().getScene().getRoot().setEffect(null);
    }

    @FXML
    protected void onCloseButton()
    {
        window.close();
        window.getOwner().getScene().getRoot().setEffect(null);
    }
}
