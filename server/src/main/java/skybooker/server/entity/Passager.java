package skybooker.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import skybooker.server.DTO.PassagerDTO;
import skybooker.server.exception.NotFoundException;
import skybooker.server.repository.CategorieRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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

    @ManyToOne(optional = false)
    @JoinColumn(name = "categorie_id", nullable = false)
    private Categorie categorie;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

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

    /**
     * This function updates the categorie based on the age
     * @param categorieRepository the categorie repository, I hate this (yes I still do)
     */
    public void updateCategorie(CategorieRepository categorieRepository) {
        // setting the categorie
        Optional<Categorie> categorie;
        if (getAge() < 18) {
            categorie = categorieRepository.findByNom("Junior");
        } else if (getAge() < 65) {
            categorie = categorieRepository.findByNom("Standard");
        } else {
            categorie = categorieRepository.findByNom("Senior");
        }

        if (categorie.isPresent()) {
            setCategorie(categorie.get());
        } else {
            throw new NotFoundException("Categorie not found");
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
