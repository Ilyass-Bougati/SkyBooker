package com.example.demo;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

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
