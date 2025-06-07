package skybooker.client.controller;

import javafx.fxml.FXML;
import skybooker.client.utils.GeneralUtils;

public class LandingPageController {
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
