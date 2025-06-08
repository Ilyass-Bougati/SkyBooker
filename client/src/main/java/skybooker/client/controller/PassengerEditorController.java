package skybooker.client.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import skybooker.client.DTO.PassagerDTO;
import skybooker.client.requests.Client;
import skybooker.client.utils.GeneralUtils;

public class PassengerEditorController {

    @FXML
    private TextField lastNameInput;

    @FXML
    private TextField firstNameInput;

    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private TextField cinInput;

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
        if (mode == Mode.CREATE) {
            PassagerDTO passager = new PassagerDTO();
            passager.setDateOfBirth(birthDatePicker.getValue().toString());
            passager.setCIN(cinInput.getText());
            passager.setNom(lastNameInput.getText());
            passager.setPrenom(firstNameInput.getText());

            try {
                Client.post("/passager/", passager);
                GeneralUtils.loadPreferences();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        window.close();
        window.getOwner().getScene().getRoot().setEffect(null);
    }

    @FXML
    protected void onDeleteButton()
    {
        // TODO : the inside of this function
        window.close();
        window.getOwner().getScene().getRoot().setEffect(null);
    }

    @FXML
    protected void onCloseButton(){
        window.close();
        window.getOwner().getScene().getRoot().setEffect(null);
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
