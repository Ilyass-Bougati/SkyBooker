package skybooker.client.controller;

import javafx.fxml.FXML;
import skybooker.client.utils.GeneralUtils;

public class BookController {

    private static Long volId;

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
