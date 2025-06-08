package skybooker.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import skybooker.client.DTO.CategorieDTO;
import skybooker.client.DTO.ClassDTO;
import skybooker.client.DTO.PassagerDTO;
import skybooker.client.DTO.ReservationDTO;
import skybooker.client.requests.Client;
import skybooker.client.requests.ClientCache;

import java.util.ArrayList;
import java.util.List;

public class PassengersInfoController {
    public static Stage window ;
    private static Long reservationId;

    @FXML
    private VBox scrollpaneBody ;

    @FXML
    protected void initialize() throws Exception {
        Platform.runLater(() -> {
            try {
                ReservationDTO reservationDTO = ClientCache.get(reservationId, ReservationDTO.class);
                List<PassagerDTO> passagers = new ArrayList<>(reservationDTO.getPassagers().size());
                reservationDTO.getPassagers().forEach(p -> {
                    try {
                        passagers.add(ClientCache.get(p.getPassagerId(), PassagerDTO.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                for(PassagerDTO passager : passagers){
                    HBox container = new HBox();
                    container.setAlignment(Pos.CENTER);
                    container.setSpacing(40);


                    Text fName = new Text(passager.getPrenom());
                    fName.setFont(new Font("Roboto" , 20));
                    Text lName = new Text(passager.getNom());
                    lName.setFont(new Font("Roboto" , 20));
                    Text category = new Text(ClientCache.get(passager.getCategorieId() , CategorieDTO.class).getNom());
                    category.setFont(new Font("Roboto", 20));
                    //TODO : replace this temp dto with the real one pwease .W.
                    ReservationDTO.PassagerData data = reservationDTO.getPassagers()
                            .stream().filter(p -> p.getPassagerId().equals(passager.getId()))
                            .toList().get(0);
                    assert data != null;
                    Text classe = new Text(ClientCache.get(data.getClassId(), ClassDTO.class).getNom());
                    classe.setFont(new Font("Roboto" , 20));


                    container.getChildren().addAll(fName,lName,category,classe);
                    scrollpaneBody.getChildren().addAll(container , new Separator());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }

    @FXML
    protected void onCloseButton(){
        window.close();
        window.getOwner().getScene().getRoot().setEffect(null);
    }

    public static void setReservationId(Long reservationId) {
        PassengersInfoController.reservationId = reservationId;
    }
}
