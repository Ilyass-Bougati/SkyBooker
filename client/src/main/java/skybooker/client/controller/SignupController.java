package skybooker.client.controller;

import javafx.application.Platform;
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

    private static String previousEmail ;

    public static void setPreviousEmail(String previousEmail) {
        SignupController.previousEmail = previousEmail;
    }

    @FXML
    protected void onNextButton(){

        if(!Validator.checkEmailValidity(email.getText())){
            eerror.setOpacity(1);
        }else{
            eerror.setOpacity(0);
        }
        if(!Validator.checkPasswordValidity(password.getText())){
            perror.setOpacity(1);
        }else{
            perror.setOpacity(0);
        }
        if(!password.getText().equals(confirmedPassword.getText())){
            cperror.setOpacity(1);
        }else{
            cperror.setOpacity(0);
        }
        if(!Validator.checkEmailValidity(email.getText()) || !Validator.checkPasswordValidity(password.getText()) || !password.getText().equals(confirmedPassword.getText())){
            return;
        }

        GeneralUtils.changeView("personalinfo-view.fxml" , ()->{
            PersonalInfoController.getRegisterRequestDTO().setEmail(email.getText());
            PersonalInfoController.getRegisterRequestDTO().setPassword(password.getText());
        });
    }

    @FXML
    protected void onBackButton(){
        GeneralUtils.changeView("auth-view.fxml");
    }

    @FXML
    protected void initialize(){
        Platform.runLater(() -> {
            if(previousEmail != null){
                email.setText(previousEmail);
                previousEmail = null ;
            }
        });
    }
}
