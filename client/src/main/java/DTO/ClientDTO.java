package DTO;

import java.util.HashSet;
import java.util.Set;
public class ClientDTO {
    private long id;
    private String email;
    private String telephone;
    private String adresse;
    private Set<ReservationDTO> reservations = new HashSet<>();
    private PassagerDTO passager;
    private RoleDTO role;
}
