package skybooker.client.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PassengereditorController {

    @FXML
    private Button deleteButton ;

    public enum Mode{
        EDIT , CREATE
    }

    public static Stage window;
    public static Mode mode;

    @FXML
    protected void onConfirmButton()
    {
        // TODO : the inside of this function
        window.close();
    }

    @FXML
    protected void onDeleteButton()
    {
        // TODO : the inside of this function
        window.close();
    }

    @FXML
    protected void onCloseButton(){
        window.close();
    }

    @FXML
    protected void initialize()
    {
        Platform.runLater(()->{
            if(mode.equals(Mode.EDIT))
            {
                deleteButton.setDisable(false);
                deleteButton.setOpacity(1);
            }
        });
    }

}
