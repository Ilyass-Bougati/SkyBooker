package skybooker.server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import skybooker.server.DTO.ClasseDTO;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "classes")
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private int prixParKm;

    @JsonIgnore
    @OneToMany(mappedBy = "classe", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Billet> billets = new HashSet<>();

    public Classe(ClasseDTO classeDTO) {
        setNom(classeDTO.getNom());
        setPrixParKm(classeDTO.getPrixParKm());
    }
}
