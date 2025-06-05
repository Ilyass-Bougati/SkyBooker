package com.example.demo;

import javafx.fxml.FXML;

public class LandingpageController {
    @FXML
    protected void onBookButton()
    {
        GeneralUtils.loadBookPopup();
    }

    @FXML
    protected void onHistoryButton()
    {
        GeneralUtils.loadHistoryView();
    }

    @FXML
    protected void onMyBookingsButton()
    {
        GeneralUtils.loadMyBookingsView();
    }
}
