package skybooker.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import skybooker.client.exceptions.UnauthorizedException;
import skybooker.client.requests.Client;
import skybooker.client.utils.GeneralUtils;
import skybooker.client.utils.Validator;

public class AuthController {

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    protected void initialize() {
        // Checking if the user is already logged in
        if (!Client.getToken().isEmpty()) {
            GeneralUtils.loadLandingPage();
        }
    }

    @FXML
    protected void onLogInButton() {
        if (Validator.checkEmailValidity(email.getText())) {
            try {
                Client.login(email.getText(), password.getText());
                GeneralUtils.loadLandingPage();
            } catch (UnauthorizedException e) { // This should change
                System.out.println("Invalid credentials");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Invalid email
            System.out.println("Invalid email");
        }

    }

    @FXML
    protected void onSignUpButton(){
        GeneralUtils.changeView("signup-view.fxml");
    }
}
