package skybooker.client.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
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
import java.text.SimpleDateFormat;

public class BookingDetailsController {

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

            Scene parentScene = HelloApplication.getScene();
            Scene scene = new Scene(parent);
            scene.setFill(Color.TRANSPARENT);
            secondaryStage.setResizable(false);
            secondaryStage.setScene(scene);

            ColorAdjust dim = new ColorAdjust();
            dim.setBrightness(-0.5);
            dim.setInput(new GaussianBlur(10));

            parentScene.getRoot().setEffect(dim);
            secondaryStage.initOwner(parentScene.getWindow());
            secondaryStage.initModality(Modality.WINDOW_MODAL);
            secondaryStage.initStyle(StageStyle.TRANSPARENT);
            secondaryStage.setX(parentScene.getWindow().getX() + (parentScene.getWidth()- secondaryStage.getWidth())*0.5 );
            secondaryStage.setY(parentScene.getWindow().getY()  + (parentScene.getHeight()- secondaryStage.getHeight())*0.5);

            PassengersInfoController.window = secondaryStage;

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
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            dateValue.setText(formatter.format(vol.getDateDepart()));

        }catch(Exception e)
        {
            return;
        }
    }

    public static void setVol(VolDTO vol) {
        BookingDetailsController.vol = vol;
    }
}
