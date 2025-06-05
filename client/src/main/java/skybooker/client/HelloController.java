package skybooker.client;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import requests.Client;
import utils.GeneralUtils;

import java.io.IOException;

public class HelloController {

    @FXML
    private HBox container;

    @FXML
    private VBox graphic;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    protected void backdoor() throws IOException
    {
        GeneralUtils.loadView("Landingpage-view.fxml");
    }

    @FXML
    protected void onSignUpButton() throws IOException
    {
        GeneralUtils.loadView("signup-view.fxml");
    }

    @FXML
    protected void onLogInButton() throws IOException
    {
        if (email.getText().isEmpty() || password.getText().isEmpty()) {
            return;
        }

        try {
            Client.login(email.getText(), password.getText());
        } catch (Exception ex) {
            /*
            * TODO : Implement some error handling in the front end
            * for example showing an error message showing "Incorrect Credentials"
            * @Amine, this is for you to do
            *
            * it'll also be preferable to change the icon in the login button
            * to indicate that it's loading, while we got a response
            */

            // these two lines can change, just wanted to indicate that the login
            // was finished incorrectly for now
            email.setText("");
            password.setText("");
            return;
        }

        GeneralUtils.loadView("landingpage-view.fxml");
    }


    @FXML
    protected void initialize()
    {
        Platform.runLater(()->{
                TranslateTransition closeInGraphic = new TranslateTransition();
                closeInGraphic.setAutoReverse(false);
                closeInGraphic.setFromX(-100);
                closeInGraphic.setToX(0);
                closeInGraphic.setDuration(new Duration(800));
                closeInGraphic.setNode(graphic);

                ParallelTransition pt = new ParallelTransition(GeneralUtils.fadeInAnimation(container , 500),closeInGraphic);

                pt.playFromStart();
        });
    }
}