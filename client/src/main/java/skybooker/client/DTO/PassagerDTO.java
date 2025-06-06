package skybooker.client.DTO;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class PassagerDTO implements Cacheable<PassagerDTO> {
    private long id;
    private String nom;
    private String prenom;
    private String CIN;
    private int age;
    private LocalDate dateOfBirth;
    private Long clientId;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
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

    // for caching
    private final static HashMap<Long, PassagerDTO> cache = new HashMap<>();
    private final static String route = "/passager/";

    @Override
    public Map<Long, PassagerDTO> getCache() {
        return cache;
    }

    @Override
    public String getRoute() {
        return route;
    }
}
