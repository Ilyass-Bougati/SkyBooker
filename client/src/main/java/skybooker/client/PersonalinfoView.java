package skybooker.client;

import DTO.register.RegisterRequestDTO;
import DTO.register.RegisterRequestDTOBuilder;
import exceptions.UnauthorizedException;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import okhttp3.Response;
import requests.Client;
import utils.GeneralUtils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.UnaryOperator;


public class PersonalinfoView {
    @FXML
    private ImageView button_icon;

    @FXML
    private ChoiceBox<String> cardinals;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmedPassword;

    @FXML
    private TextField phone;

    @FXML
    private TextField adresse;

    @FXML
    private TextField cin;

    @FXML
    private DatePicker birthDate;

    @FXML
    private HBox container;


    private interface controlCheck {
        boolean check();
    }

    @FXML
    protected void onBackButton() throws IOException
    {
        GeneralUtils.loadView("signup-view.fxml");
    }

    @FXML
    public void onRegisterButton() {
        // checking the inputs
        if (password.getText().equals(confirmedPassword.getText()) && !cin.getText().isEmpty() && !password.getText().isEmpty() && !phone.getText().isEmpty() && !adresse.getText().isEmpty() && birthDate.getValue() != null) {
            RegisterRequestDTOBuilder.setAdresse(adresse.getText());
            RegisterRequestDTOBuilder.setTelephone(phone.getText());
            RegisterRequestDTOBuilder.setPassword(password.getText());
            RegisterRequestDTOBuilder.setCIN(cin.getText());
            // this is fiiiiiiine
            RegisterRequestDTOBuilder.setAge((int) ChronoUnit.YEARS.between(birthDate.getValue(), LocalDate.now()));

            try {
                RegisterRequestDTO request = RegisterRequestDTOBuilder.build();
                Client.unAuthorizedPost("/auth/register", request);
                Client.login(request.getEmail(), request.getPassword());
                GeneralUtils.loadView("landingpage-view.fxml");
            } catch (UnauthorizedException e) {
                System.out.println("Unauthorized");
                /*
                    The user is already logged in
                */
                return;
            } catch (Exception e) {
                // TODO : Take the user to an error page...
                e.printStackTrace();
                return;
            }
        }

    }

    @FXML
    public void initialize()
    {
        //Sets the icon on the button to white
        Blend blend = new Blend(
                BlendMode.SRC_ATOP,
                null,
                new ColorInput(0, 0, button_icon.getFitWidth(), button_icon.getFitHeight(), Color.WHITE)
        );
        button_icon.setEffect(blend);

        Platform.runLater(() ->
        {
            GeneralUtils.fadeInAnimation(container , 500).play();
            GeneralUtils.initializeDatePicker(birthDate, new GeneralUtils.DateVerifier() {
                @Override
                public Boolean call() {
                    if(getCell() != null){
                        return getCell().getItem().isBefore(LocalDate.now());
                    }
                    throw new RuntimeException();
                }
            });
            initializeCardinals();
            addPhoneNumberConstraint();
            addToolTip(password , "Password must be at least 8 characters , \n contain lower and upper case characters ,  \n as well as numerals and a special character");
            addToolTip(confirmedPassword , "Passwords must match");

            initializeErrorPopup(password , "Password must be at least 8 characters , contain upper and lower case , numeral and special characters" , this::verifyPasswordValidity);
            initializeErrorPopup(confirmedPassword , "Passwords must match" , this::verifyConfirmedPasswordValidity);
        });



    }
    private boolean verifyPasswordValidity()
    {
        if(password.getText().length() < 8)
        {
            return false;
        }

        boolean containsUpperCase = false , containsLowerCase = false , containsNumeral = false , containsSpecialChar = false;

        for(char c : password.getText().toCharArray())
        {
            if( c >= 'a' && c <= 'z')
            {
                containsLowerCase = true;
            }
            else if( c >= 'A' && c <= 'Z' )
            {
                containsUpperCase = true;
            }
            else if( c >= '0' && c <= '9' )
            {
                containsNumeral = true;
            }
            else{
                containsSpecialChar = true;
            }
        }

        return containsUpperCase && containsLowerCase && containsNumeral && containsSpecialChar ;
    }

    private boolean verifyConfirmedPasswordValidity()
    {
        return !verifyPasswordValidity() || confirmedPassword.getText().equals(password.getText()) ;
    }

    private void initializeCardinals()
    {
        cardinals.setItems(FXCollections.observableArrayList("CA" , "FR" ,"MA" , "US"));
        cardinals.setValue("MA");
    }

    private void addPhoneNumberConstraint()
    {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if(!change.isDeleted() &&( change.getText().matches("[^0-9]") || phone.getText().length() == 9)){
                return null;
            }
            return change;
        };

        phone.setTextFormatter(new TextFormatter<>(filter));
    }

    private void addToolTip(Control control , String tip)
    {
        Tooltip tt = new Tooltip(tip);
        tt.setWrapText(true);
        tt.setFont(new Font("Roboto Light" , 17));
        control.tooltipProperty().setValue(tt);
    }

    private void initializeErrorPopup(Control c , String Error , controlCheck chk)
    {
        Popup errorPopup = new Popup();

        Label errorMessage = new Label(Error);
        errorMessage.setStyle("-fx-text-fill: red ; -fx-font-family: 'Roboto Light' ; -fx-font-size: 13 ; -fx-font-weight: bold ;");

        VBox errorMessageContainer = new VBox();
        errorMessageContainer.setAlignment(Pos.CENTER);

        errorMessageContainer.getChildren().add(errorMessage);
        errorPopup.getContent().add(errorMessageContainer);

        if(c.equals(confirmedPassword))
        {
            errorPopup.setAutoHide(true);
        }

        c.setOnKeyTyped(e ->{
            boolean b = chk.check();
            if(!errorPopup.isShowing() && !b)
            {
                Bounds bounds = c.localToScene(c.getBoundsInLocal());
                HelloApplication.getScene().getWindow().xProperty().addListener(_->{
                    errorPopup.setX(bounds.getMinX() + HelloApplication.getScene().getWindow().getX());
                });
                HelloApplication.getScene().getWindow().yProperty().addListener(_->{
                    errorPopup.setY(bounds.getMaxY() + 30 + HelloApplication.getScene().getWindow().getY());
                });
                errorPopup.show(c , bounds.getMinX() + HelloApplication.getScene().getWindow().getX() , bounds.getMaxY() + 30 + HelloApplication.getScene().getWindow().getY());

            }
            else if(errorPopup.isShowing() && b)
            {
                errorPopup.hide();
            }
        });
    }
}

