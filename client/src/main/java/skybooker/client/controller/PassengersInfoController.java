package skybooker.client.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class PassengersInfoController {
    public static Stage window ;

    @FXML
    protected void onCloseButton(){
        window.close();
        window.getOwner().getScene().getRoot().setEffect(null);
    }
}
