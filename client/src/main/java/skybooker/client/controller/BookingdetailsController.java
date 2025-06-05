package skybooker.client.controller;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import skybooker.client.utils.GeneralUtils;
import skybooker.client.HelloApplication;

import java.io.IOException;

public class BookingdetailsController {

    @FXML
    protected void onReturnButton() {
        GeneralUtils.changeView("mybookings-view.fxml");
    }

    @FXML
    protected void onPassengerDetailsButton(){
        try{
            Parent parent = HelloApplication.loadView("passengersinfo-view.fxml");
            Stage secondaryStage = new Stage();

            secondaryStage.setWidth(572);
            secondaryStage.setHeight(311);

            secondaryStage.setResizable(false);
            secondaryStage.setScene(new Scene(parent));

            secondaryStage.initOwner(HelloApplication.getScene().getWindow());
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.initStyle(StageStyle.UNDECORATED);

            PassengersinfoController.window = secondaryStage;

            secondaryStage.show();
        }catch (IOException e)
        {
            System.out.println("No such view");
        }
    }
}
