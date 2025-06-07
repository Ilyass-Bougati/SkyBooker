package skybooker.server.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import skybooker.server.entity.Client;
import skybooker.server.entity.Passager;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
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
    private LocalDate dateOfBirth;

    public Passager passager() {
        Passager passager = new Passager();
        passager.setNom(nom);
        passager.setPrenom(prenom);
        passager.setCIN(CIN);
        passager.setDateOfBirth(dateOfBirth);
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
