package skybooker.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
            // TODO : Show the passagers
        } catch (Exception e) {
            e.printStackTrace();
        }


        Platform.runLater(()->{
            if(isComingFromBookPopup)
            {
                nextButton.setDisable(false);
                nextButton.setOpacity(1);
            }
        });
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
