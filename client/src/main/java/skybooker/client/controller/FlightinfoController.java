package skybooker.client.controller;

import javafx.fxml.FXML;
import skybooker.client.DTO.VolDTO;
import skybooker.client.requests.ClientCache;
import skybooker.client.utils.GeneralUtils;

public class FlightinfoController {

    private static Long volId;

    @FXML
    protected void initialize() {
        try {
            VolDTO vol = ClientCache.get(volId, VolDTO.class);
            // TODO : Show the details
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onReturnButton(){
        GeneralUtils.changeView("searchresults-view.fxml");
    }

    @FXML
    protected void onBookButton(){
        BookController.setVolId(volId);
        GeneralUtils.changeView("book-view.fxml");
    }

    public static void setVolId(Long volId) {
        FlightinfoController.volId = volId;
    }
}
