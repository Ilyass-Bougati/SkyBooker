package skybooker.client;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;

import java.util.HashMap;

public class SignUpController {

    @FXML
    private ChoiceBox<String> cardinal;

    @FXML
    public void initialize()
    {
        cardinal.setItems(FXCollections.observableArrayList("MA" , "US" , "CA" , "FR"));
        cardinal.setValue("MA");
    }
}
