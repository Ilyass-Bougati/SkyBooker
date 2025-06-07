package skybooker.client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class PersonalInfoController {
    public static class Logger {
        private String email ;
        private String password ;
        private String phoneNumber ;
        private String CIN ;
        private String address ;
        private String fName ;
        private String lName ;

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
        }

        public String getCIN() {
            return CIN;
        }

        public void setCIN(String CIN) {
            this.CIN = CIN;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        private LocalDate birthDate ;
    }

    private static Logger logger = new Logger() ;

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        PersonalInfoController.logger = logger;
    }

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
        //TODO : add the conditions please |:B
        if(/*any field is Wrong*/true){
            if(/*first/last Name is wrong*/ true){
                nameError.setOpacity(1);
            }

            if(/*number is wrong*/ true){
                numberError.setOpacity(1);
            }
        }else{

        }
    }
}
