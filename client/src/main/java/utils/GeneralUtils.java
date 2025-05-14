package utils;

import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.Duration;
import skybooker.client.HelloApplication;

import java.io.IOException;

public class GeneralUtils {

    public static void loadView(String viewFileName)throws IOException
    {
        HelloApplication.fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(viewFileName));
        HelloApplication.scene.setRoot(HelloApplication.fxmlLoader.load());
    }

    public static FadeTransition fadeInAnimation(Node node , int milliseconds)
    {
        FadeTransition fadeIn = new FadeTransition();
        fadeIn.setNode(node);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setDuration(new Duration(milliseconds));
        fadeIn.setAutoReverse(false);
        return fadeIn;
    }

    public static FadeTransition fadeOutAnimation(Node node , int milliseconds)
    {
        FadeTransition fadeOut = new FadeTransition();
        fadeOut.setNode(node);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setDuration(new Duration(milliseconds));
        fadeOut.setAutoReverse(false);
        return fadeOut;
    }

    public static void initializeDatePicker(DatePicker date)
    {
        date.getEditor().setDisable(true);
    }

    public static void changeWindowTitle(String newTitle) { ((Stage)HelloApplication.scene.getWindow()).setTitle(newTitle); }
}
