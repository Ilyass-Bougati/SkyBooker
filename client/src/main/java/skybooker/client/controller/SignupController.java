package skybooker.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import skybooker.client.utils.GeneralUtils;

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
        //TODO: add the conditions please |:B
        if(/*any field is wrong*/false){
            if(/*Email incorrect*/ true){
                eerror.setOpacity(1);
            }
            if(/*Password incorrect*/ true){
                perror.setOpacity(1);
            }
            if(/*Confirmed Password incorrect*/ true){
                cperror.setOpacity(1);
            }
        }else{
            GeneralUtils.changeView("personalinfo-view.fxml" , ()->{
                PersonalInfoController.getLogger().setEmail(email.getText());
                PersonalInfoController.getLogger().setPassword(password.getText());
            });
        }
    }
}
