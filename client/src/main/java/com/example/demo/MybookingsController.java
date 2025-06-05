package com.example.demo;

import javafx.fxml.FXML;

public class MybookingsController {

    @FXML
    protected void onReturnButton() {
        GeneralUtils.changeView("landingpage-view.fxml");
    }

    @FXML
    protected void onBookingSelected()
    {
        toBookingDetails();
    }

    private void toBookingDetails()
    {
        GeneralUtils.changeView("bookingdetails-view.fxml");
    }
}
