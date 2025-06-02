package skybooker.server.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import skybooker.server.entity.Client;
import skybooker.server.entity.Passager;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PassagerDTO {

    private long id;

    Long clientId;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    @NotNull
    private String CIN;

    @NotNull
    @Min(0)
    private int age;

    public PassagerDTO(Passager passager) {
        setId(passager.getId());
        setNom(passager.getNom());
        setPrenom(passager.getPrenom());
        setCIN(passager.getCIN());
        setAge(passager.getAge());
        setClientId(passager.getClient().getId());
    }
}
