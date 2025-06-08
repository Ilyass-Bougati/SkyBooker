package skybooker.client.controller;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import skybooker.client.DTO.CategorieDTO;
import skybooker.client.DTO.ClassDTO;
import skybooker.client.DTO.PassagerDTO;
import skybooker.client.DTO.ReservationDTO;
import skybooker.client.requests.Client;
import skybooker.client.requests.ClientCache;
import skybooker.client.utils.GeneralUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookController {

    private static Long volId;

    @FXML
    private VBox scrollpaneBody ;

    @FXML
    private Button checkoutButton;

    @FXML
    protected void initialize() {

        Platform.runLater(() -> {
            for (ReservationDTO.PassagerData passagerData : PreferencesController.getChosenPassagers()) {
                try {
                    PassagerDTO passagerDTO = ClientCache.get(passagerData.getPassagerId(), PassagerDTO.class);
                    ClassDTO classDTO = ClientCache.get(passagerData.getClassId(), ClassDTO.class);
                    CategorieDTO categorieDTO = ClientCache.get(passagerDTO.getCategorieId() , CategorieDTO.class);

                    HBox container = new HBox();
                    container.setSpacing(40);
                    container.setAlignment(Pos.CENTER);

                    Text fName = new Text();
                    fName.setFont(new Font("Roboto" , 20));
                    fName.setText(passagerDTO.getPrenom());

                    Text lName = new Text();
                    lName.setFont(new Font("Roboto" , 20));
                    lName.setText(passagerDTO.getNom());

                    Text category = new Text();
                    category.setFont(new Font("Roboto" , 20));
                    category.setText(categorieDTO.getNom());

                    Text classe = new Text();
                    category.setFont(new Font("Roboto" , 20));
                    classe.setText(classDTO.getNom());

                    container.getChildren().addAll(fName , lName , category , classe);
                    scrollpaneBody.getChildren().addAll(container , new Separator());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            checkoutButton.setText("Checkout : " + FlightInfoController.getReservationPrice().toString());

        });

    }

    @FXML
    protected void onReturnButton(){
        GeneralUtils.changeView("flightinfo-view.fxml");
    }

    @FXML
    protected void onCheckoutButton(){
        // Creating the reservations
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setVolId(volId);
        // TODO : THIS CAN'T STAY ;; f you wan me to do
        reservationDTO.setPrixTotal(FlightInfoController.getReservationPrice());
        reservationDTO.setPassagers(PreferencesController.getChosenPassagers());

        try {
            Client.post("/reservation/", reservationDTO);
            GeneralUtils.loadLandingPage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setVolId(Long volId) {
        BookController.volId = volId;
    }
}
