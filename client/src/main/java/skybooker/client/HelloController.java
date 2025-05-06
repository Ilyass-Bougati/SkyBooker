package skybooker.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.io.IOException;

public class HelloController {

    @FXML
    protected void onSignUpButton() throws IOException
    {
        HelloApplication.fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup-view.fxml"));
        HelloApplication.scene.setRoot(HelloApplication.fxmlLoader.load());
    }
}