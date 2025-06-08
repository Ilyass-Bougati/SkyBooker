package skybooker.client.controller;

import javafx.fxml.FXML;
import skybooker.client.DTO.ClassDTO;
import skybooker.client.DTO.PassagerDTO;
import skybooker.client.DTO.ReservationDTO;
import skybooker.client.requests.ClientCache;
import skybooker.client.utils.GeneralUtils;

public class BookController {

    private static Long volId;

    @FXML
    protected void initialize() {
        for (ReservationDTO.PassagerData passagerData : PreferencesController.getChosenPassagers()) {
            try {
                PassagerDTO passagerDTO = ClientCache.get(passagerData.getPassagerId(), PassagerDTO.class);
                ClassDTO classDTO = ClientCache.get(passagerData.getClassId(), ClassDTO.class);
                // TODO : show these details man
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // TODO : you can get the price from FlightInfoController.getReservationPrice(), show it from here
    }

    @FXML
    protected void onReturnButton(){
        GeneralUtils.changeView("flightinfo-view.fxml");
    }

    @FXML
    protected void onCheckoutButton(){
        //TODO : We need to do something ;; yes ik
    }

    public static void setVolId(Long volId) {
        BookController.volId = volId;
    }
}
