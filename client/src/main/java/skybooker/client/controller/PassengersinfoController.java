package skybooker.client.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class PassengersinfoController {
    public static Stage window ;

    @FXML
    protected void onCloseButton(){
        window.close();
    }
}
