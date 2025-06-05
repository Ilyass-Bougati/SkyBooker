package skybooker.client.controller;


import javafx.fxml.FXML;
import skybooker.client.utils.GeneralUtils;

public class SearchresultsController {

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
}
