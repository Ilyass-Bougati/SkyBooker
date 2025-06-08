package skybooker.client.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import skybooker.client.DTO.CategorieDTO;
import skybooker.client.DTO.VilleDTO;
import skybooker.client.requests.Client;
import skybooker.client.requests.ClientCache;
import skybooker.client.utils.GeneralUtils;

import java.util.ArrayList;
import java.util.List;

public class LandingPageController {

    @FXML
    protected void initialize() throws Exception {
        // loading needed values
        ObjectMapper mapper = new ObjectMapper();
        List<CategorieDTO> categories = new ArrayList<>();
        try {
            // fetching the client's details
            Client.fetchClient();

            String res = Client.get("/categories/");
            categories = mapper.readValue(res, new TypeReference<>(){});

            // Caching the categories
            for (CategorieDTO cat : categories) {
                ClientCache.add(cat);
            }
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
}
