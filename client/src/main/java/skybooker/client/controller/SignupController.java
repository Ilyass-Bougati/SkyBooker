package skybooker.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import skybooker.client.utils.GeneralUtils;
import skybooker.client.utils.Validator;

public class SignupController {
    @FXML
    private TextField email ;

    @FXML
    private TextField password ;

    @FXML
    private TextField confirmedPassword ;

    @FXML
    private Text eerror;

    @FXML
    private Text perror;

    @FXML
    private Text cperror;

    @FXML
    protected void onNextButton(){
        if(!Validator.checkEmailValidity(email.getText())){
            eerror.setOpacity(1);
            return;
        }
        if(!Validator.checkPasswordValidity(password.getText())){
            perror.setOpacity(1);
            return;
        }
        if(!password.getText().equals(confirmedPassword.getText())){
            cperror.setOpacity(1);
            return;
        }

        GeneralUtils.changeView("personalinfo-view.fxml" , ()->{
            PersonalInfoController.getRegisterRequestDTO().setEmail(email.getText());
            PersonalInfoController.getRegisterRequestDTO().setPassword(password.getText());
        });
    }
}
