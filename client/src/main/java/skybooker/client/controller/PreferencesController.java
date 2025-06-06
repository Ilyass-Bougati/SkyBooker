package skybooker.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import skybooker.client.DTO.PassagerDTO;
import skybooker.client.DTO.ReservationDTO;
import skybooker.client.requests.Client;
import skybooker.client.utils.GeneralUtils;
import skybooker.client.HelloApplication;

import java.io.IOException;
import java.util.List;

public class PreferencesController {

    @FXML
    private Button nextButton ;

    @FXML
    private VBox scrollPaneBody ;

    public static boolean isComingFromBookPopup = false;

    @FXML
    protected void onReturnButton()
    {
        GeneralUtils.changeView("landingpage-view.fxml" , () -> isComingFromBookPopup = false);
    }

    @FXML
    protected void onNextButton()
    {
        GeneralUtils.changeView("searchresults-view.fxml" , () -> isComingFromBookPopup = false);
    }

    @FXML
    protected void onPlusButton()
    {
        loadPassengerEditor(PassengereditorController.Mode.CREATE);
    }

    @FXML
    protected void onEditButton()
    {
        loadPassengerEditor(PassengereditorController.Mode.EDIT);
    }

    @FXML
    protected void initialize()
    {
        Platform.runLater(()->{
            initializePassengers();
            if(isComingFromBookPopup)
            {
                nextButton.setDisable(false);
                nextButton.setOpacity(1);
            }
        });
    }

    private void initializePassengers()
    {
        // fetching the passagers
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        List<PassagerDTO> passagers;
        try {
            String res = Client.get("/passager/");
            passagers = mapper.readValue(res, new TypeReference<>() {});
            for (PassagerDTO passager : passagers) {
                System.out.println(passager.getCIN());
            }
            //Show the passagers  ;; Stop ordering me around >:(
            for(PassagerDTO passager : passagers)
            {
                HBox container = new HBox();
                container.setAlignment(Pos.CENTER);
                container.setSpacing(40);

                CheckBox checkBox = new CheckBox();
                checkBox.setStyle("-fx-background-color: #EDEDED");

                Text fName = new Text(passager.getPrenom());
                fName.setFont(new Font("Roboto" , 20));

                Text lName = new Text(passager.getNom());
                lName.setFont(new Font("Roboto" , 20));

                Text category = new Text("Category");
                category.setFont(new Font("Roboto" , 20));

                //TODO : figure out how to get the category of the passenger

                StackPane stackPane = new StackPane();

                ImageView icon = new ImageView(new Image(HelloApplication.class.getResource("/assets/icons/Edit.png").toExternalForm()));
                Button button = new Button();
                button.setMaxWidth(16);
                button.setMinWidth(16);
                button.setMaxHeight(16);
                button.setMinHeight(16);
                button.setOpacity(0);
                button.setOnAction(_ -> loadPassengerEditor(PassengereditorController.Mode.EDIT));

                stackPane.getChildren().addAll(icon , button);
                container.getChildren().addAll(checkBox , fName , lName , category , stackPane);

                scrollPaneBody.getChildren().addAll(container , new Separator());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadPassengerEditor(PassengereditorController.Mode mode)
    {
        try{
            Parent parent = HelloApplication.loadView("passengereditor-view.fxml");
            Stage secondaryStage = new Stage();

            secondaryStage.setWidth(738);
            secondaryStage.setHeight(418);

            secondaryStage.setResizable(false);
            secondaryStage.setScene(new Scene(parent));

            secondaryStage.initOwner(HelloApplication.getScene().getWindow());
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.initStyle(StageStyle.UNDECORATED);

            PassengereditorController.window = secondaryStage;
            PassengereditorController.mode = mode;

            secondaryStage.show();
        }catch (IOException e)
        {
            System.out.println("No such view");
        }
    }
}
