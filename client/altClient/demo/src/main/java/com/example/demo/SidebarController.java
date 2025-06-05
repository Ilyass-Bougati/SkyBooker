package com.example.demo;

import javafx.fxml.FXML;

public class SidebarController {

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

    @FXML
    protected void onPreferencesButton()
    {
        PreferencesController.isComingFromBookPopup = false ;
        GeneralUtils.loadPreferences();
    }

    @FXML
    protected void onLogOut()
    {
        GeneralUtils.doLogOut();
    }
}
