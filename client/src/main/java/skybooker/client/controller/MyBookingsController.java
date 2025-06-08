package skybooker.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;
import skybooker.client.DTO.*;
import skybooker.client.requests.Client;
import skybooker.client.requests.ClientCache;
import skybooker.client.utils.GeneralUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;


public class MyBookingsController {

    @FXML
    private VBox scrollPaneContainer;

    @FXML
    protected void onReturnButton() {
        GeneralUtils.changeView("landingpage-view.fxml");
    }

    @FXML
    protected void onBookingSelected()
    {
        toBookingDetails(null);
    }

    @FXML
    private void initialize() {
        Platform.runLater(() ->{
           initializeBookings();
        });
    }

    private void initializeBookings(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.findAndRegisterModules();
        try {
            String res = Client.get("/reservation/");
            List<ReservationDTO> reservations = mapper.readValue(res, new TypeReference<>() {});
            for(ReservationDTO reservation : reservations) {
                // adding the reservation to cache
                ClientCache.add(reservation);

                VolDTO vol = ClientCache.get(reservation.getVolId(), VolDTO.class);
                AeroportDTO aeroportDepart = ClientCache.get(vol.getAeroportDepartId(), AeroportDTO.class);
                AeroportDTO aeroportArrive = ClientCache.get(vol.getAeroportArriveId(), AeroportDTO.class);

                StackPane stackPane = new StackPane();
                HBox globalContainer =  new HBox();

                globalContainer.setAlignment(Pos.CENTER);
                globalContainer.setSpacing(50);
                globalContainer.setMinWidth(540);
                globalContainer.setMaxWidth(540);

                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                Text date = new Text(formatter.format(vol.getDateDepart()));
                date.setFont(new Font("Roboto" , 15));

                HBox container = new HBox();
                container.setAlignment(Pos.CENTER);
                container.setSpacing(20);

                Text DEP = new Text(aeroportDepart.getIataCode());
                DEP.setFont(new Font("Roboto" , 15));

                Text DEPTIME = new Text(vol.getHeureDepart().toString());
                DEPTIME.setFont(new Font("Roboto" , 15));

                Text ARRTIME = new Text(vol.getHeureArrive().toString());
                ARRTIME.setFont(new Font("Roboto" , 15));

                Text ARR = new Text(aeroportArrive.getIataCode());
                ARR.setFont(new Font("Roboto" , 15));

                container.getChildren().addAll(DEP , DEPTIME , ARRTIME , ARR);

                Text passengersAmount = new Text("Passengers : " + reservation.getPassagers().size());
                passengersAmount.setFont(new Font("Roboto" , 15));

                globalContainer.getChildren().addAll(date , container , passengersAmount);

                Button button = new Button();
                button.setOpacity(0);
                button.setMinHeight(50);
                button.setMaxHeight(50);
                button.setMinWidth(540);
                button.setMaxWidth(540);
                button.setStyle("-fx-cursor: hand");
                button.setOnAction(_ -> {
                    BookingDetailsController.setReservation(reservation);
                    toBookingDetails(vol);
                });

                stackPane.getChildren().addAll(globalContainer , button);

                scrollPaneContainer.getChildren().addAll(stackPane , new Separator());
            }
        } catch(Exception e) {
            e.printStackTrace();
            return;
        }
    }

    private void toBookingDetails(VolDTO vol)
    {
        BookingDetailsController.setVol(vol);
        GeneralUtils.changeView("bookingdetails-view.fxml");
    }
}
