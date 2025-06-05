package com.example.demo;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class BookpopupController {
    public static Stage window;

    @FXML
    protected void onPreferences()
    {
        PreferencesController.isComingFromBookPopup = true;
        GeneralUtils.loadPreferences();
        window.close();
    }

    @FXML
    protected void onSearchButton()
    {
        GeneralUtils.changeView("searchresults-view.fxml");
        window.close();
    }

    @FXML
    protected void onCloseButton()
    {
        window.close();
    }
}
