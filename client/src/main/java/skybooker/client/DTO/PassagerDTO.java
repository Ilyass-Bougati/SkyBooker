package skybooker.client.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PassagerDTO implements Cacheable<PassagerDTO> {
    private long id;
    private String nom;
    private String prenom;
    private String CIN;
    private int age;
    private String dateOfBirth;
    private Long clientId;
    private Long categorieId;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getCIN() {
        return CIN;
    }

    public void setCIN(String CIN) {
        this.CIN = CIN;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Long getCategorieId() {
        return categorieId;
    }

    public void setCategorieId(Long categorieId) {
        this.categorieId = categorieId;
    }

    // for caching
    @JsonIgnore
    private final static HashMap<Long, PassagerDTO> cache = new HashMap<>();

    @JsonIgnore
    private final static String route = "/passager/";

    @Override
    @JsonIgnore
    public Map<Long, PassagerDTO> getCache() {
        return cache;
    }

    @Override
    public String getRoute() {
        return route;
    }
}
