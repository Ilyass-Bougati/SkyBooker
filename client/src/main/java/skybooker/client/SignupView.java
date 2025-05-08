package skybooker.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class SignupView {
    @FXML
    private ImageView button_icon;

    @FXML
    private TextField fName;

    @FXML
    private TextField lName;

    @FXML
    private TextField email;




    @FXML
    protected void onContinueButton() throws IOException
    {
        if(checkNameValidity(fName.getText()) && checkNameValidity(lName.getText()) && checkEmailValidity(email.getText()))
        {
            HelloApplication.fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("personalinfo-view.fxml"));
            HelloApplication.scene.setRoot(HelloApplication.fxmlLoader.load());
        }
    }

    @FXML
    public void initialize()
    {
        //Changes the window title
        Stage stage = (Stage)(HelloApplication.scene.getWindow());
        stage.setTitle("Sign up to SkyBooker");
        //Sets the icon on the button to white
        Blend blend = new Blend(
                BlendMode.SRC_ATOP,
                null,
                new ColorInput(0, 0, button_icon.getFitWidth(), button_icon.getFitHeight(), Color.WHITE)
        );
        button_icon.setEffect(blend);
    }


    private boolean checkNameValidity(String Name)
    {
        if(Name.isEmpty() || Name.length() > 50)
        {
            return false;
        }

        String lower = Name.toLowerCase();

        for(int i = 0 ; i < Name.length() ; i++)
        {
            if(lower.charAt(i) > 'z' || lower.charAt(i) < 'a')
            {
                return false;
            }
        }

        return true;
    }

    private boolean checkEmailValidity(String email)
    {
        return email.length() <= 320 && email.contains("@") && email.split("@")[1].contains(".");
    }
}
