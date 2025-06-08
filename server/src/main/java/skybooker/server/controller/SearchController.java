package skybooker.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skybooker.server.DTO.SearchDTO;
import skybooker.server.entity.Client;
import skybooker.server.service.ClientService;
import skybooker.server.service.SearchService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final SearchService searchService;
    private final ClientService clientService;

    public SearchController(SearchService searchService, ClientService clientService) {
        this.searchService = searchService;
        this.clientService = clientService;
    }

    @GetMapping("/")
    public ResponseEntity<List<SearchDTO>> getHistory(Principal principal) {
        Client client = clientService.getFromPrincipal(principal);
        return ResponseEntity.ok(searchService.getHistory(client.getId()));
    }
}
