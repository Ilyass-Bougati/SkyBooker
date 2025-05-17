package skybooker.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import skybooker.server.DTO.PassagerDTO;
import skybooker.server.service.CategorieService;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "passagers")
public class Passager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String nom;

    @NotNull
    private String prenom;

    @Column(unique = true)
    @NotNull
    private String CIN;

    @NotNull
    @Min(0)
    private int age;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    @JsonIgnore
    @OneToMany(mappedBy = "passager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Billet> billets = new HashSet<>();

    public void updateFields(Passager passager) {
        setNom(passager.getNom());
        setPrenom(passager.getPrenom());
        setCIN(passager.getCIN());
        setAge(passager.getAge());
        setCategorie(passager.getCategorie());
    }

    public Passager(PassagerDTO passagerDTO) {
        setNom(passagerDTO.getNom());
        setPrenom(passagerDTO.getPrenom());
        setCIN(passagerDTO.getCIN());
        setAge(passagerDTO.getAge());
        billets = new HashSet<>();
    }

    // TODO : WTF is this????
    /**
     * This function updates the categorie based on the age
     * @param categorieService the categorie service, I hate this
     */
    public void updateCategorie(CategorieService categorieService) {
        // setting the categorie
        if (getAge() < 18) {
            setCategorie(categorieService.findById(1L));
        } else if (getAge() < 65) {
            setCategorie(categorieService.findById(2L));
        } else {
            setCategorie(categorieService.findById(3L));
        }
    }

    /**
     * This function lowercases the fields that needs to be lowercase
     */
    public void lowerCase() {
        setNom(getNom().toLowerCase());
        setPrenom(getPrenom().toLowerCase());
        setCIN(getCIN().toLowerCase());
    }


}
