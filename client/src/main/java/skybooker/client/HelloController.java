package skybooker.client;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
    private HBox inputs;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    protected void onSignUpButton() throws IOException
    {
        GeneralUtils.loadView("signup-view.fxml");
    }

    @FXML
    protected void onLogInButton() throws IOException
    {
//        if (email.getText().isEmpty() || password.getText().isEmpty()) {return;}
//
//        try {
//            Client.login(email.getText(), password.getText());
//        } catch (IOException ex) {
//           Logger.getLogger(HelloController.class.getName()).log(Level.SEVERE, null, ex);
//            return;
//        }

        GeneralUtils.loadView("Landingpage-view.fxml");
    }


    @FXML
    protected void initialize()
    {
        Platform.runLater(()-> GeneralUtils.changeWindowTitle("Authentication for SkyBooker"));
        Platform.runLater(()->{
                            Bounds graphicBounds = graphic.localToScene(graphic.getBoundsInLocal());
                            TranslateTransition closeInGraphic = new TranslateTransition();
                            closeInGraphic.setAutoReverse(false);
                            closeInGraphic.setFromX(graphicBounds.getMinX() - 100);
                            closeInGraphic.setToX(graphicBounds.getMinX());
                            closeInGraphic.setDuration(new Duration(800));
                            closeInGraphic.setNode(graphic);

                            GeneralUtils.fadeInAnimation(container , 500).play();
                            closeInGraphic.play();
        });
    }
}