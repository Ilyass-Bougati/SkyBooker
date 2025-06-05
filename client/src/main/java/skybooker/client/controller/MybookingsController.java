package skybooker.client.controller;

import javafx.fxml.FXML;
import skybooker.client.utils.GeneralUtils;

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
