package skybooker.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import skybooker.client.DTO.SearchDTO;
import skybooker.client.DTO.VilleDTO;
import skybooker.client.DTO.VolDTO;
import skybooker.client.requests.Client;
import skybooker.client.requests.ClientCache;
import skybooker.client.utils.GeneralUtils;

import java.util.HashMap;
import java.util.List;

public class SideBarController {

    @FXML
    private Button destination1;

    @FXML
    private Button destination2;

    @FXML
    private Button destination3;

    @FXML
    private Text destination1text ;

    @FXML
    private Text destination2text ;

    @FXML
    private Text destination3text ;

    @FXML
    protected void initialize() {
        Platform.runLater(() -> {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String res = Client.get("/search/");
                List<SearchDTO> searches = mapper.readValue(res, new TypeReference<>(){});
                System.out.println(searches.size());

                destination1text.setText(ClientCache.get(searches.get(0).getVilleArriveeId() , VilleDTO.class).getNom());
                destination2text.setText(ClientCache.get(searches.get(1).getVilleArriveeId() , VilleDTO.class).getNom());
                destination3text.setText(ClientCache.get(searches.get(2).getVilleArriveeId() , VilleDTO.class).getNom());

                if(searches.size() > 0){
                    destination1.setOnAction(_ ->{
                        GeneralUtils.loadBookPopup();
                        try {
                            BookPopupController.setvA(ClientCache.get(searches.get(0).getVilleArriveeId() , VilleDTO.class).getNom());
                            BookPopupController.setvD(ClientCache.get(searches.get(0).getVilleDepartId() , VilleDTO.class).getNom());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

                    if(searches.size() > 1){
                        destination2.setOnAction(_ ->{
                            GeneralUtils.loadBookPopup();
                            try {
                                BookPopupController.setvA(ClientCache.get(searches.get(1).getVilleArriveeId() , VilleDTO.class).getNom());
                                BookPopupController.setvD(ClientCache.get(searches.get(1).getVilleDepartId() , VilleDTO.class).getNom());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                        if(searches.size() > 2){
                            destination3.setOnAction(_ ->{
                                GeneralUtils.loadBookPopup();
                                try {
                                    BookPopupController.setvA(ClientCache.get(searches.get(2).getVilleArriveeId() , VilleDTO.class).getNom());
                                    BookPopupController.setvD(ClientCache.get(searches.get(2).getVilleDepartId() , VilleDTO.class).getNom());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    protected void onBookButton()
    {
        GeneralUtils.loadBookPopup();
    }

    @FXML
    protected void onHistoryButton()
    {
        GeneralUtils.loadHistoryView();
    }

    @FXML
    protected void onMyBookingsButton()
    {
        GeneralUtils.loadMyBookingsView();
    }

    @FXML
    protected void onPreferencesButton()
    {
        PreferencesController.isComingFromBookPopup = false ;
        GeneralUtils.loadPreferences();
    }

    @FXML
    protected void onLogOut()
    {
        Client.logout();
        GeneralUtils.doLogOut();
    }
}
