package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.ClientDTO;
import skybooker.server.DTO.RegisterRequestDTO;
import skybooker.server.entity.Categorie;
import skybooker.server.entity.Client;
import skybooker.server.entity.Passager;
import skybooker.server.entity.Role;
import skybooker.server.service.CategorieService;
import skybooker.server.service.ClientService;
import skybooker.server.service.PassagerService;
import skybooker.server.service.RoleService;
import skybooker.server.service.implementation.TokenService;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final TokenService tokenService;
    private final ClientService clientService;

    public AuthController(TokenService tokenService, ClientService clientService) {
        this.tokenService = tokenService;
        this.clientService = clientService;
    }

    @GetMapping("/")
    public ResponseEntity<ClientDTO> getClient(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        } else {
            ClientDTO client = clientService.findByEmail(principal.getName());
            return ResponseEntity.ok(client);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> token(Authentication authentication) {
        return ResponseEntity.ok(tokenService.generateToken(authentication));
    }

    @PostMapping("/register")
    public ResponseEntity<ClientDTO> saveClient(Authentication authentication, @RequestBody @Valid RegisterRequestDTO registerRequest) {
        if (authentication!= null) {
            return ResponseEntity.badRequest().build();
        }
        return clientService.register(registerRequest);
    }

}
