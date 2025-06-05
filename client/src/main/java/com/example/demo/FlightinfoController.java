package com.example.demo;

import javafx.fxml.FXML;

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
