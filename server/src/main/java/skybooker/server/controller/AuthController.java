package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skybooker.server.DTO.ClientDTO;
import skybooker.server.DTO.RegisterRequestDTO;
import skybooker.server.entity.Categorie;
import skybooker.server.entity.Client;
import skybooker.server.entity.Passager;
import skybooker.server.service.CategorieService;
import skybooker.server.service.ClientService;
import skybooker.server.service.PassagerService;
import skybooker.server.service.implementation.TokenService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final TokenService tokenService;
    private final ClientService clientService;
    private final PassagerService passagerService;
    private final CategorieService categorieService;

    public AuthController(TokenService tokenService, ClientService clientService, PassagerService passagerService, CategorieService categorieService) {
        this.tokenService = tokenService;
        this.clientService = clientService;
        this.passagerService = passagerService;
        this.categorieService = categorieService;
    }

    @PostMapping("/")
    public ResponseEntity<String> token(Authentication authentication) {
        return ResponseEntity.ok(tokenService.generateToken(authentication));
    }

    @PostMapping("/register")
    public ResponseEntity<ClientDTO> saveClient(Authentication authentication, @RequestBody @Valid RegisterRequestDTO registerRequest) {
        if (authentication!= null) {
            return ResponseEntity.badRequest().build();
        }
        Passager passager = registerRequest.passager();

        // giving the user the default category
        Categorie defaultCategorie = categorieService.findById(1L);
        if (defaultCategorie == null) {
            return ResponseEntity.badRequest().body(null);
        }
        passager.setCategorie(defaultCategorie);
        passagerService.create(passager);

        Client client = registerRequest.client();
        client.setPassager(passager);
        clientService.create(client);
        return ResponseEntity.ok(new ClientDTO(client));
    }

}
