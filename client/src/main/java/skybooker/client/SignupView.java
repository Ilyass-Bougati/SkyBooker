package skybooker.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.io.IOException;

public class SignupView {
    @FXML
    private ImageView button_icon;

    @FXML
    protected void onContinueButton() throws IOException
    {
        HelloApplication.fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("personalinfo-view.fxml"));
        HelloApplication.scene.setRoot(HelloApplication.fxmlLoader.load());
    }

    @FXML
    public void initialize()
    {
        //Sets the icon on the button to white
        Blend blend = new Blend(
                BlendMode.SRC_ATOP,
                null,
                new ColorInput(0, 0, button_icon.getFitWidth(), button_icon.getFitHeight(), Color.WHITE)
        );
        button_icon.setEffect(blend);
    }
}
