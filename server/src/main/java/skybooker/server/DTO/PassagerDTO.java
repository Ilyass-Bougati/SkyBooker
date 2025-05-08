package skybooker.server.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import skybooker.server.entity.Passager;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassagerDTO {

    private long id;

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
    }
}
