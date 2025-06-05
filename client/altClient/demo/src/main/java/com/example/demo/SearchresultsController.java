package com.example.demo;


import javafx.fxml.FXML;

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
