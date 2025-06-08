package skybooker.server.service;

import org.springframework.http.ResponseEntity;
import skybooker.server.DTO.ClientDTO;
import skybooker.server.DTO.PassagerDTO;
import skybooker.server.DTO.RegisterRequestDTO;
import skybooker.server.DTO.ReservationDTO;
import skybooker.server.entity.Client;
import skybooker.server.entity.Reservation;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface ClientService extends CrudDTO<ClientDTO, Long>{
    void deleteByEmail(String email);
    ClientDTO findByEmail(String email);
    ResponseEntity<ClientDTO> register(RegisterRequestDTO registerRequestDTO);
    Boolean passagerAddedByClient(Long clientId, Long passagerId);
    Client getFromPrincipal(Principal principal);
    ClientDTO findbyIdDTO(Long id);
    void cancelReservation(Long reservationId);
    ClientDTO updateDTO(ClientDTO clientDTO, String email);
    Client findById(Long clientId);
    Client create(Client client);
    List<Client> findAll();
    List<ReservationDTO> getReservations(Long clientId);
    List<ReservationDTO> getReservationsHistory(Long clientId);
    List<PassagerDTO> getPassagers(Long clientId);
    boolean clientMadeReservation(Long clientId, Long reservationId);
}
