package skybooker.client;

import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import requests.Client;
import utils.GeneralUtils;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        } catch (IOException ex) {
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