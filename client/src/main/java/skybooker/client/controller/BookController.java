package skybooker.client.controller;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
    protected void initialize() {

        HashMap<String , ClassDTO> classMap = new HashMap<>();

        try{
            //fetching classes
            ObjectMapper mapper = new ObjectMapper();
            String res = Client.get("/classe/");
            List<ClassDTO> classes = mapper.readValue(res, new TypeReference<>() {});

            for(ClassDTO classe : classes){
                ClientCache.add(classe);
                classMap.put(classe.getNom() , classe);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        for (ReservationDTO.PassagerData passagerData : PreferencesController.getChosenPassagers()) {
            try {
                PassagerDTO passagerDTO = ClientCache.get(passagerData.getPassagerId(), PassagerDTO.class);
                ClassDTO classDTO = ClientCache.get(passagerData.getClassId(), ClassDTO.class);
                CategorieDTO categorieDTO = ClientCache.get(passagerData.getPassagerId() , CategorieDTO.class);

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

                ChoiceBox<String> classesBox = new ChoiceBox<>();
                classesBox.setItems(FXCollections.observableArrayList(new ArrayList<>(classMap.keySet())));
                classesBox.setValue(classDTO.getNom());

                container.getChildren().addAll(fName , lName , category , classesBox );
                scrollpaneBody.getChildren().addAll(container , new Separator());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // TODO : you can get the price from FlightInfoController.getReservationPrice(), show it from here
    }

    @FXML
    protected void onReturnButton(){
        GeneralUtils.changeView("flightinfo-view.fxml");
    }

    @FXML
    protected void onCheckoutButton(){
        //TODO : We need to do something ;; yes ik
    }

    public static void setVolId(Long volId) {
        BookController.volId = volId;
    }
}
