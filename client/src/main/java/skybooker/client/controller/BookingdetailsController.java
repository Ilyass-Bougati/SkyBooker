package skybooker.client.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import skybooker.client.DTO.AeroportDTO;
import skybooker.client.DTO.AvionDTO;
import skybooker.client.DTO.CompanieAerienneDTO;
import skybooker.client.DTO.VolDTO;
import skybooker.client.requests.ClientCache;
import skybooker.client.utils.GeneralUtils;
import skybooker.client.HelloApplication;

import java.io.IOException;

public class BookingdetailsController {

    @FXML
    private Text airlineValue ;

    @FXML
    private Text departureValue ;

    @FXML
    private Text arrivalValue ;

    @FXML
    private Text dateValue ;

    private static VolDTO vol;

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
            System.out.println("Passager info : No such view");
        }
    }

    @FXML
    protected void initialize(){
        Platform.runLater(() -> {
            initializeText();
        });
    }

    private void initializeText()
    {
        try{
            AvionDTO avion = ClientCache.get(vol.getAvionId(), AvionDTO.class);
            CompanieAerienneDTO companieAerienne = ClientCache.get(avion.getCompanieAerienneId(), CompanieAerienneDTO.class);
            AeroportDTO aeroportDepart = ClientCache.get(vol.getAeroportDepartId(), AeroportDTO.class);
            AeroportDTO aeroportArrive = ClientCache.get(vol.getAeroportArriveId(), AeroportDTO.class);


            airlineValue.setText(companieAerienne.getNom());
            departureValue.setText(aeroportDepart.getIataCode() + " " + vol.getHeureDepart());
            arrivalValue.setText(aeroportArrive.getIataCode() + " " + vol.getHeureArrive());
            dateValue.setText(vol.getDateDepart().toString());

        }catch(Exception e)
        {
            return;
        }
    }

    public static void setVol(VolDTO vol) {
        BookingdetailsController.vol = vol;
    }
}
