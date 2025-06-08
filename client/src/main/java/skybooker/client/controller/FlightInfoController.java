package skybooker.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import skybooker.client.DTO.*;
import skybooker.client.requests.Client;
import skybooker.client.requests.ClientCache;
import skybooker.client.utils.GeneralUtils;

public class FlightInfoController {

    @FXML
    private Text arrival ;

    @FXML
    private Text departure ;

    @FXML
    private Text airline ;

    @FXML
    private Text price ;

    private static Long volId;

    private static Double reservationPrice = 0.0;

    @FXML
    protected void initialize() {
        ObjectMapper mapper = new ObjectMapper();

        try {
            VolDTO vol = ClientCache.get(volId, VolDTO.class);
            AvionDTO avion = ClientCache.get(vol.getAvionId(), AvionDTO.class);
            CompanieAerienneDTO companieAerienne = ClientCache.get(avion.getCompanieAerienneId(), CompanieAerienneDTO.class);
            AeroportDTO aeroportDepart = ClientCache.get(vol.getAeroportDepartId(), AeroportDTO.class);
            AeroportDTO aeroportArrive = ClientCache.get(vol.getAeroportArriveId(), AeroportDTO.class);


            airline.setText(companieAerienne.getNom());
            departure.setText(aeroportDepart.getIataCode() + " " + vol.getHeureDepart());
            arrival.setText(aeroportArrive.getIataCode() + " " + vol.getHeureArrive());

            // TODO : This could be optimised by porting the logic to the backend
            for (ReservationDTO.PassagerData passagerData : PreferencesController.getChosenPassagers()) {
                PassagerDTO passagerDTO = ClientCache.get(passagerData.getPassagerId(), PassagerDTO.class);
                CategorieDTO categorieDTO = ClientCache.get(passagerDTO.getCategorieId(), CategorieDTO.class);
                ClassDTO classe = ClientCache.get(passagerData.getClassId(), ClassDTO.class);
                String res = Client.get("/vol/price/" + volId + "/" + classe.getId());
                PriceDTO priceDTO = mapper.readValue(res, new TypeReference<>() {});
                reservationPrice += priceDTO.getPrice() * (1 - categorieDTO.getReduction());
            }

            price.setText(Double.toString(reservationPrice));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onReturnButton(){
        GeneralUtils.changeView("searchresults-view.fxml");
    }

    @FXML
    protected void onBookButton(){
        BookController.setVolId(volId);
        GeneralUtils.changeView("book-view.fxml");
    }

    public static void setVolId(Long volId) {
        FlightInfoController.volId = volId;
    }

    public static Double getReservationPrice() {
        return reservationPrice;
    }

    public static void setReservationPrice(Double reservationPrice) {
        FlightInfoController.reservationPrice = reservationPrice;
    }
}
