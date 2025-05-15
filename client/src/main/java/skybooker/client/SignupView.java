package skybooker.client;

import DTO.RegisterRequestDTOBuilder;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorInput;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.GeneralUtils;
import utils.Validator;

import java.io.IOException;

public class SignupView {
    @FXML
    private ImageView button_icon;

    @FXML
    private TextField fName;

    @FXML
    private TextField lName;

    @FXML
    private TextField email;

    @FXML
    private StackPane container;


    private interface controlCheck {
        boolean check();
    }

    @FXML
    protected void onContinueButton() throws RuntimeException
    {

        if(Validator.checkNameValidity(fName.getText()) && Validator.checkNameValidity(lName.getText()) && Validator.checkEmailValidity(email.getText()))
        {
            // storing the data to the RegisterRequestDTO
            RegisterRequestDTOBuilder.setPrenom(fName.getText());
            RegisterRequestDTOBuilder.setNom(lName.getText());
            RegisterRequestDTOBuilder.setEmail(email.getText());

            FadeTransition fadeOut = GeneralUtils.fadeOutAnimation(container , 500);

            fadeOut.setOnFinished( e -> {
                try{
                    GeneralUtils.loadView("personalinfo-view.fxml");
                }catch(IOException ioe)
                {
                    throw new RuntimeException("An error occured while loading view");
                }
            });

            fadeOut.play();
        }
    }

    @FXML
    protected void onBackButton() throws IOException
    {
        GeneralUtils.loadView("hello-view.fxml");
    }

    @FXML
    public void initialize()
    {
        //Changes the window title
        GeneralUtils.changeWindowTitle("Sign up to SkyBooker");

        //Sets the icon on the button to white
        Blend blend = new Blend(
                BlendMode.SRC_ATOP,
                null,
                new ColorInput(0, 0, button_icon.getFitWidth(), button_icon.getFitHeight(), Color.WHITE)
        );
        button_icon.setEffect(blend);

        Platform.runLater(()-> GeneralUtils.fadeInAnimation(container , 500).play());

        initializeErrorPopup(fName, "Name should only contain characters ", ()->{
            String text = fName.getText() ;
            return Validator.checkNameValidity(text);
        });

        initializeErrorPopup(lName, "Name should only contain characters ", ()->{
            String text = lName.getText() ;
            return Validator.checkNameValidity(text);
        });

        initializeErrorPopup(email, "Invalid Email", ()->{
            String text = email.getText() ;
            return Validator.checkEmailValidity(text);
        });

    }

    private void initializeErrorPopup(Control c , String Error , controlCheck chk)
    {
        Popup errorPopup = new Popup();

        Label errorMessage = new Label(Error);
        errorMessage.setStyle("-fx-text-fill: red ; -fx-font-family: 'Roboto Light' ; -fx-font-size: 14 ; -fx-font-weight: bold ;");

        VBox errorMessageContainer = new VBox();
        errorMessageContainer.setAlignment(Pos.CENTER);

        errorMessageContainer.getChildren().add(errorMessage);
        errorPopup.getContent().add(errorMessageContainer);

        c.setOnKeyTyped(e ->{
            boolean b = chk.check();
            if(!errorPopup.isShowing() && !b)
            {
                Bounds bounds = c.localToScene(c.getBoundsInLocal());
                HelloApplication.scene.getWindow().xProperty().addListener(_->{
                    errorPopup.setX(bounds.getMinX() + HelloApplication.scene.getWindow().getX());
                });
                HelloApplication.scene.getWindow().yProperty().addListener(_->{
                    errorPopup.setY(bounds.getMaxY() + 30 + HelloApplication.scene.getWindow().getY());
                });
                errorPopup.show(c , bounds.getMinX() + HelloApplication.scene.getWindow().getX() , bounds.getMaxY() + 30 + HelloApplication.scene.getWindow().getY());
            }
            else if(errorPopup.isShowing() && b)
            {
                errorPopup.hide();
            }
        });
    }
}
