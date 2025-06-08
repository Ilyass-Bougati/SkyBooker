package skybooker.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import skybooker.client.DTO.ReservationDTO;
import skybooker.client.DTO.SearchDTO;
import skybooker.client.requests.Client;
import skybooker.client.utils.GeneralUtils;

import java.util.List;

public class HistoryController {

    @FXML
    protected void initialize() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String res = Client.get("/reservation/history");
            List<ReservationDTO> reservations = mapper.readValue(res, new TypeReference<>(){});

            // TODO : show these reservations
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onReturnButton() {
        GeneralUtils.changeView("landingpage-view.fxml");
    }

    @FXML
    protected void onBookingSelected()
    {
        toBookingDetails();
    }

    private void toBookingDetails()
    {
        GeneralUtils.changeView("bookingdetails-view.fxml");
    }
}
