package skybooker.client.controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import skybooker.client.DTO.AeroportDTO;
import skybooker.client.DTO.AvionDTO;
import skybooker.client.DTO.CompanieAerienneDTO;
import skybooker.client.DTO.VolDTO;
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

    @FXML
    protected void initialize() {
        try {
            VolDTO vol = ClientCache.get(volId, VolDTO.class);
            AvionDTO avion = ClientCache.get(vol.getAvionId(), AvionDTO.class);
            CompanieAerienneDTO companieAerienne = ClientCache.get(avion.getCompanieAerienneId(), CompanieAerienneDTO.class);
            AeroportDTO aeroportDepart = ClientCache.get(vol.getAeroportDepartId(), AeroportDTO.class);
            AeroportDTO aeroportArrive = ClientCache.get(vol.getAeroportArriveId(), AeroportDTO.class);


            airline.setText(companieAerienne.getNom());
            departure.setText(aeroportDepart.getIataCode() + " " + vol.getHeureDepart());
            arrival.setText(aeroportArrive.getIataCode() + " " + vol.getHeureArrive());
            price.setText(((Double)vol.getPrix()).toString());

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
}
