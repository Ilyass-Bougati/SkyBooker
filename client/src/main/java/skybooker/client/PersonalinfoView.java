package skybooker.client;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
    public void initialize()
    {
        //Sets the icon on the button to white
        Blend blend = new Blend(
                BlendMode.SRC_ATOP,
                null,
                new ColorInput(0, 0, button_icon.getFitWidth(), button_icon.getFitHeight(), Color.WHITE)
        );
        button_icon.setEffect(blend);

        initializeCardinals();
        addPhoneNumberConstraint();
        addToolTip(password , "Password must be at least 8 characters , \n contain lower and upper case characters ,  \n as well as numerals and a special character");
        addToolTip(password , "Passwords must match");
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
        return confirmedPassword.getText().equals(password.getText()) ;
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
}

