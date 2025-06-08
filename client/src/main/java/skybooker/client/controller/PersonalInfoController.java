package skybooker.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import okhttp3.internal.Util;
import skybooker.client.DTO.RegisterRequestDTO;
import skybooker.client.requests.Client;
import skybooker.client.utils.GeneralUtils;
import skybooker.client.utils.Validator;

import java.time.LocalDate;

public class PersonalInfoController {

    private static RegisterRequestDTO registerRequestDTO = new RegisterRequestDTO() ;

    @FXML
    private TextField fName;

    @FXML
    private TextField lName;

    @FXML
    private TextField address;

    @FXML
    private ChoiceBox<String> countryIndicator ;

    @FXML
    private TextField phoneNumber ;

    @FXML
    private TextField CIN ;

    @FXML
    private DatePicker birthdate ;

    @FXML
    private Text nameError;

    @FXML
    private Text numberError;

    @FXML
    protected void onFinishButton(){

        if(!Validator.checkNameValidity(fName.getText()) || !Validator.checkNameValidity(lName.getText())){
            System.out.println("Invalid name!");
            nameError.setOpacity(1);
        }else{
            nameError.setOpacity(0);
        }

        if(!Validator.checkPhoneNumberValidity(phoneNumber.getText())){
            System.out.println("Phone number is not valid");
            numberError.setOpacity(1);
        }else{
            numberError.setOpacity(0);
        }
        if(!Validator.checkNameValidity(fName.getText()) || !Validator.checkNameValidity(lName.getText()) || !Validator.checkPhoneNumberValidity(phoneNumber.getText())){return;}

        registerRequestDTO.setAdresse(address.getText());
        registerRequestDTO.setCin(CIN.getText());
        registerRequestDTO.setNom(lName.getText());
        registerRequestDTO.setPrenom(registerRequestDTO.getNom());
        registerRequestDTO.setPassword(fName.getText());
        registerRequestDTO.setTelephone(phoneNumber.getText());
        registerRequestDTO.setDateOfBirth(birthdate.getValue().toString());

        try {
            String token = Client.unAuthorizedPost("/auth/register", registerRequestDTO);
            Client.login(registerRequestDTO.getEmail(), registerRequestDTO.getPassword());
            GeneralUtils.loadLandingPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onBackButton(){
        SignupController.setPreviousEmail(registerRequestDTO.getEmail());
        GeneralUtils.changeView("signup-view.fxml");
    }

    public static RegisterRequestDTO getRegisterRequestDTO() {
        return registerRequestDTO;
    }

    public static void setRegisterRequestDTO(RegisterRequestDTO registerRequestDTO) {
        PersonalInfoController.registerRequestDTO = registerRequestDTO;
    }
}
