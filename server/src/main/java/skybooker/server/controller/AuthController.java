package skybooker.server.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skybooker.server.entity.Categorie;
import skybooker.server.entity.Client;
import skybooker.server.entity.Passager;
import skybooker.server.repository.CategorieRepository;
import skybooker.server.repository.PassagerRepository;
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
    public String token(Authentication authentication) {
        return tokenService.generateToken(authentication);
    }

    // This inner class is for creating new Clients
    @Data
    public static class RegisterRequest {
        @Email
        private String email;

        @NotNull
        private String password;

        @NotNull
        private String telephone;

        @NotNull
        private String adresse;

        @NotNull
        private String nom;

        @NotNull
        private String prenom;

        @NotNull
        private String CIN;

        @NotNull
        @Min(0)
        private int age;

        public Passager passager() {
            Passager passager = new Passager();
            passager.setNom(nom);
            passager.setPrenom(prenom);
            passager.setCIN(CIN);
            passager.setAge(age);
            return passager;
        }

        public Client client() {
            Client client = new Client();
            client.setEmail(email);
            client.setPassword(password);
            client.setTelephone(telephone);
            client.setAdresse(adresse);
            return client;
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Client> saveClient(@RequestBody RegisterRequest registerRequest) {
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
        return ResponseEntity.ok(client);
    }

}
