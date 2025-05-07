package skybooker.server.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import skybooker.server.entity.Client;
import skybooker.server.entity.Passager;

@Data
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

    @JsonProperty("CIN")
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
