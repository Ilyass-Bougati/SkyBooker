package skybooker.client.controller;

import javafx.fxml.FXML;
import skybooker.client.utils.GeneralUtils;

public class FlightinfoController {

    @FXML
    protected void onReturnButton(){
        GeneralUtils.changeView("searchresults-view.fxml");
    }

    @FXML
    protected void onBookButton(){
        GeneralUtils.changeView("book-view.fxml");
    }
}
