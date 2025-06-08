package skybooker.client.controller;

import javafx.fxml.FXML;
import skybooker.client.requests.Client;
import skybooker.client.utils.GeneralUtils;

public class SideBarController {

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
        Client.logout();
        GeneralUtils.doLogOut();
    }
}
