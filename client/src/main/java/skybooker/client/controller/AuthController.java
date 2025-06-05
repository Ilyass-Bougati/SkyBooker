package skybooker.client.controller;

import javafx.fxml.FXML;
import skybooker.client.utils.GeneralUtils;

public class AuthController {

    @FXML
    protected void onLogInButton()
    {
        GeneralUtils.loadLandingPage();
    }
}
