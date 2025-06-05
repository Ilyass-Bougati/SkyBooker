package skybooker.client.controller;

import javafx.fxml.FXML;
import skybooker.client.utils.GeneralUtils;

public class BookController {

    @FXML
    protected void onReturnButton(){
        GeneralUtils.changeView("flightinfo-view.fxml");
    }

    @FXML
    protected void onCheckoutButton(){
    //TODO : We need to do something
    }

}
