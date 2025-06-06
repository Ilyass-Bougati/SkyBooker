package skybooker.client.controller;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import skybooker.client.DTO.AeroportDTO;
import skybooker.client.DTO.AvionDTO;
import skybooker.client.DTO.CompanieAerienneDTO;
import skybooker.client.DTO.VolDTO;
import skybooker.client.requests.Client;
import skybooker.client.requests.ClientCache;
import skybooker.client.utils.GeneralUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchresultsController {

    private static Long departureFlight;
    private static Long arrivalFlight;
    private static DatePicker departureDate;

    @FXML
    private ScrollPane searchResultsScrollPane;

    @FXML
    private VBox searchResultsContainer;

    @FXML
    protected void initialize() {
        // fetching the vols
        List<VolDTO> vols;
        ObjectMapper mapper = new ObjectMapper();
        try {
            String res = Client.get("/vol/getFromVilles/" + departureFlight + "/" + arrivalFlight);
            vols = mapper.readValue(res, new TypeReference<List<VolDTO>>(){});
            for (VolDTO v : vols) {
                // this is building on the way @Amine did it in the past
                // Note that this can't throw an exception (I guess...)
                // Here I'm fetching the values and putting them in variables
                // so that if we want to change the layout later we don't have to rewrite
                // the fetching logic
                ArrayList<String> Row = new ArrayList<>();
                AvionDTO avion = ClientCache.get(v.getAvionId(), AvionDTO.class);
                CompanieAerienneDTO companieAerienne = ClientCache.get(avion.getCompanieAerienneId(), CompanieAerienneDTO.class);
                AeroportDTO aeroportDepart = ClientCache.get(v.getAeroportDepartId(), AeroportDTO.class);
                AeroportDTO aeroportArrive = ClientCache.get(v.getAeroportArriveId(), AeroportDTO.class);

                // Here's the layout, change here
                Row.add("Airline " + companieAerienne.getNom());
                Row.add("\t\tDPT " + aeroportDepart.getNom());
                Row.add("\t" + v.getHeureDepart());
                Row.add("\t" + v.getHeureArrive());
                Row.add("\tARR " + aeroportArrive.getNom());
                Row.add("\t\t100$");

                Button rowButton = new Button();
                rowButton.setText(Row.toString());
                rowButton.setAlignment(Pos.CENTER);

                searchResultsContainer.getChildren().add(rowButton);
            }
        } catch (Exception e) {
            /*
             TODO : here we should redirect the user to a page that tells them
             that we're out of service for now, since that's the only reason for
             an exception to show up here, maybe we can add another route
             that checks the health of the backend
             */
            e.printStackTrace();
            return;
        }
    }

    @FXML
    protected void onReturnButton() {
        GeneralUtils.loadLandingPage();
    }

    @FXML
    protected void onFlightSelected()
    {
        loadFlightInfo();
    }

    private void loadFlightInfo(){
        GeneralUtils.changeView("flightinfo-view.fxml");
    }

    public static void setDepartureFlight(Long departureFlight) {
        SearchresultsController.departureFlight = departureFlight;
    }

    public static void setArrivalFlight(Long arrivalFlight) {
        SearchresultsController.arrivalFlight = arrivalFlight;
    }

    public static void setDepartureDate(DatePicker departureDate) {
        SearchresultsController.departureDate = departureDate;
    }
}
