package skybooker.client;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class HelloController {

    @FXML
    private HBox container;

    @FXML
    private VBox graphic;

    @FXML
    private HBox inputs;

    @FXML
    protected void onSignUpButton() throws IOException
    {
        HelloApplication.fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup-view.fxml"));
        HelloApplication.scene.setRoot(HelloApplication.fxmlLoader.load());
    }

    @FXML
    protected void onLogInButton() throws IOException
    {
        HelloApplication.fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Landingpage-view.fxml"));
        HelloApplication.scene.setRoot(HelloApplication.fxmlLoader.load());
    }

    private void fadeInAnimation()
    {
        FadeTransition fadeIn = new FadeTransition();
        fadeIn.setNode(container);
        fadeIn.setDuration(new Duration(1500));
        fadeIn.setAutoReverse(false);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }


    @FXML
    protected void initialize()
    {
        Platform.runLater(()-> ((Stage)HelloApplication.scene.getWindow()).setTitle("Authentication for SkyBooker"));
        Platform.runLater(()->{
                            Bounds graphicBounds = graphic.localToScreen(graphic.getBoundsInLocal());
                            TranslateTransition closeInGraphic = new TranslateTransition();
                            closeInGraphic.setAutoReverse(false);
                            closeInGraphic.setFromX(graphicBounds.getMinX() - 100);
                            closeInGraphic.setToX(graphicBounds.getMinX());
                            closeInGraphic.setDuration(new Duration(1500));
                            closeInGraphic.setNode(graphic);

                            fadeInAnimation();
                            closeInGraphic.play();
        });
    }
}