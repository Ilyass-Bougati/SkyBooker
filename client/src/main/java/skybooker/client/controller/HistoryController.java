package skybooker.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import skybooker.client.DTO.CompanieAerienneDTO;
import skybooker.client.DTO.ReservationDTO;
import skybooker.client.DTO.SearchDTO;
import skybooker.client.DTO.VolDTO;
import skybooker.client.HelloApplication;
import skybooker.client.requests.Client;
import skybooker.client.requests.ClientCache;
import skybooker.client.utils.GeneralUtils;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

public class HistoryController {

    @FXML
    private VBox scrollpaneBody ;

    @FXML
    protected void initialize() {
        Platform.runLater(() -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String res = Client.get("/reservation/history");
                List<ReservationDTO> reservations = mapper.readValue(res, new TypeReference<>(){});
                System.out.println(reservations);

                // TODO : show these reservations

                for(ReservationDTO resDTO : reservations){
                    StackPane stackpane = new StackPane();

                    HBox globalContainer = new HBox();
                    globalContainer.setAlignment(Pos.CENTER);
                    globalContainer.setSpacing(50);
                    globalContainer.setMinWidth(540);
                    globalContainer.setMaxWidth(540);

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/YYYY");

                    Text date = new Text(formatter.format(resDTO.getReservedAt()));

                    HBox container = new HBox();
                    container.setSpacing(20);
                    container.setAlignment(Pos.CENTER);

                    VolDTO vol = ClientCache.get(resDTO.getVolId() , VolDTO.class) ;

                    Text DEP = new Text(ClientCache.get(vol.getAeroportDepartId() , CompanieAerienneDTO.class).getIataCode());

                    Text depTime = new Text(vol.getHeureDepart().toString());

                    Text arrTime = new Text(vol.getHeureArrive().toString());

                    Text ARR = new Text(ClientCache.get(vol.getAeroportDepartId() , CompanieAerienneDTO.class).getIataCode());

                    container.getChildren().addAll(DEP , depTime , arrTime , ARR);
                    URL imageurl = switch (resDTO.getEtat()) {
                        case CANCELLED_BY_USER, CANCELLED_BY_AIRLINE, REFUNDED ->
                                HelloApplication.class.getResource("assets/icons/Cancelled.png");
                        case CHECKED_IN -> HelloApplication.class.getResource("assets/icons/Departed.png");
                        default -> null;
                    };
                    if(imageurl == null){
                        continue;
                    }

                    Image icon = new Image(imageurl.toString());
                    ImageView imageView = new ImageView(icon);

                    globalContainer.getChildren().addAll(date , container , imageView);

                    Button rowButton = new Button();
                    rowButton.setMinHeight(50);
                    rowButton.setMaxHeight(50);
                    rowButton.setMinWidth(540);
                    rowButton.setMaxWidth(540);
                    rowButton.setOnAction(_ -> toBookingDetails(vol));

                    stackpane.getChildren().addAll(globalContainer , rowButton);

                    scrollpaneBody.getChildren().addAll(stackpane , new Separator());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    protected void onReturnButton() {
        GeneralUtils.changeView("landingpage-view.fxml");
    }

    private void toBookingDetails(VolDTO vol)
    {
        BookingDetailsController.setVol(vol);
        GeneralUtils.changeView("bookingdetails-view.fxml");
    }
}
