package skybooker.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import skybooker.client.DTO.SearchDTO;
import skybooker.client.DTO.VolDTO;
import skybooker.client.requests.Client;
import skybooker.client.utils.GeneralUtils;

import java.util.List;

public class SideBarController {

    @FXML
    protected void initialize() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String res = Client.get("/search/");
            List<SearchDTO> searches = mapper.readValue(res, new TypeReference<>(){});
            System.out.println(searches.size());

            // TODO : show these searches in the history
        } catch (Exception e) {
            e.printStackTrace();
        }
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
