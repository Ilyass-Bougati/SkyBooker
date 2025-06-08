package skybooker.server.controller;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.PriceDTO;
import skybooker.server.DTO.VolDTO;
import skybooker.server.entity.Client;
import skybooker.server.service.ClientService;
import skybooker.server.service.SearchService;
import skybooker.server.service.VolService;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vol")
public class VolController {

    private final VolService volService;
    private final ClientService clientService;
    private final SearchService searchService;

    public VolController(VolService volService, ClientService clientService, SearchService searchService) {
        this.volService = volService;
        this.clientService = clientService;
        this.searchService = searchService;
    }

    @GetMapping("/getFromVilles/{villeAriveeId}/{villeDepartId}")
    public ResponseEntity<List<VolDTO>> getVols(Principal principal,  @PathVariable Long villeAriveeId, @PathVariable Long villeDepartId) {
        Client client = clientService.getFromPrincipal(principal);
        searchService.addSearch(villeDepartId, villeAriveeId, client);
        return ResponseEntity.ok(volService.getTrajetVols(villeDepartId, villeAriveeId));
    }

    @GetMapping("/price/{volId}/{classeId}")
    public ResponseEntity<PriceDTO> getPrice(@PathVariable long volId, @PathVariable long classeId) {
        Double calculatedPrice = volService.calculatePrice(volId, classeId);
        
        if (calculatedPrice == null) {
            return ResponseEntity.notFound().build();
        }
        
        PriceDTO response = new PriceDTO(calculatedPrice.floatValue());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolDTO> vol(@PathVariable long id){
        return ResponseEntity.ok(volService.findDTOById(id));
    }

    @PostMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<VolDTO> addVol(@RequestBody @Valid VolDTO volDTO){
        return ResponseEntity.ok(volService.createDTO(volDTO));
    }

    @PutMapping("/")
    @Secured("SCOPE_ROLE_ADMIN")
    public ResponseEntity<VolDTO> updateVol(@RequestBody @Valid VolDTO volDTO){
        return ResponseEntity.ok(volService.updateDTO(volDTO));

    }

    @DeleteMapping("/{id}")
    @Secured("SCOPE_ROLE_ADMIN")
    public void deleteVol(@PathVariable long id){
        volService.deleteById(id);
    }
}
