package skybooker.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import skybooker.client.DTO.VilleDTO;
import skybooker.client.requests.Client;
import skybooker.client.requests.ClientCache;
import skybooker.client.utils.GeneralUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookpopupController {
    public static Stage window;

    @FXML
    ChoiceBox<String> departureFlight;

    @FXML
    ChoiceBox<String> arrivalFlight;

    @FXML
    DatePicker departureDate;

    Map<String, Long> cityNameIdMap = new HashMap<>();

    @FXML
    private void initialize() {
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
        } catch (Exception e) {
            window.close();
        }
    }

    @FXML
    protected void onPreferences()
    {
        PreferencesController.isComingFromBookPopup = true;
        GeneralUtils.loadPreferences();
        window.close();
    }

    @FXML
    protected void onSearchButton()
    {
        if (arrivalFlight.getValue().equals("Arrival") || departureFlight.getValue().equals("Departure") || departureDate.getValue() == null)
        {
            return;
        }

        SearchresultsController.setArrivalFlight(cityNameIdMap.get(arrivalFlight.getValue()));
        SearchresultsController.setDepartureFlight(cityNameIdMap.get(departureFlight.getValue()));
        SearchresultsController.setDepartureDate(departureDate);

        GeneralUtils.changeView("searchresults-view.fxml");
        window.close();
    }

    @FXML
    protected void onCloseButton()
    {
        window.close();
    }
}
