package skybooker.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import skybooker.client.DTO.PassagerDTO;
import skybooker.client.DTO.ReservationDTO;
import skybooker.client.requests.Client;
import skybooker.client.requests.ClientCache;

import java.util.ArrayList;
import java.util.List;

public class PassengersInfoController {
    public static Stage window ;
    private static Long reservationId;

    @FXML
    protected void initialize() throws Exception {
        try {
            ReservationDTO reservationDTO = ClientCache.get(reservationId, ReservationDTO.class);
            List<PassagerDTO> passagers = new ArrayList<>(reservationDTO.getPassagers().size());
            reservationDTO.getPassagers().forEach(p -> {
                try {
                    passagers.add(ClientCache.get(p.getPassagerId(), PassagerDTO.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    protected void onCloseButton(){
        window.close();
        window.getOwner().getScene().getRoot().setEffect(null);
    }

    public static void setReservationId(Long reservationId) {
        PassengersInfoController.reservationId = reservationId;
    }
}
