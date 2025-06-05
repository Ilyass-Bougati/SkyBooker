package com.example.demo;

import javafx.fxml.FXML;

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
