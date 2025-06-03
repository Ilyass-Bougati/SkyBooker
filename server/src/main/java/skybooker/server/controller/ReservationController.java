package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.ReservationDTO;
import skybooker.server.entity.Client;
import skybooker.server.entity.Reservation;
import skybooker.server.exception.NotFoundException;
import skybooker.server.service.ClientService;
import skybooker.server.service.ReservationService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final ClientService clientService;

    public ReservationController(ReservationService reservationService, ClientService clientService) {
        this.reservationService = reservationService;
        this.clientService = clientService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ReservationDTO>> getAllReservation(Principal principal) {
        Client client = clientService.getFromPrincipal(principal);
        Set<Reservation> reservations = client.getReservations();
        List<ReservationDTO> reservationDTOs = reservations.stream().map(ReservationDTO::new).toList();
        return ResponseEntity.ok(reservationDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(Principal principal, @PathVariable Long id) {
        Client client =  clientService.getFromPrincipal(principal);
        ReservationDTO reservation = reservationService.findDTOById(id);

        // checking if the reservation was made by the client
        if (reservation.getClientId() != client.getId()) {
            throw new NotFoundException("Reservation not found");
        } else {
            return ResponseEntity.ok(reservation);
        }
    }

    @PostMapping("/")
    public ResponseEntity<ReservationDTO> createReservation(Principal principal, @RequestBody @Valid ReservationDTO reservationDTO) {
        // making sure we're creating a reservation for the logged in client
        Client client =  clientService.getFromPrincipal(principal);
        reservationDTO.setClientId(client.getId());

        // saving the reservation
        ReservationDTO savedReservation = reservationService.createDTO(reservationDTO);
        if (savedReservation == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(savedReservation);
    }

    @PutMapping("/")
    public ResponseEntity<ReservationDTO> updateReservation(Principal principal, @RequestBody @Valid ReservationDTO reservationDTO) {
        // making sure we're creating a reservation for the logged in client
        Client client =  clientService.getFromPrincipal(principal);
        reservationDTO.setClientId(client.getId());

        // updating the reservation
        ReservationDTO updatedReservation = reservationService.updateDTO(reservationDTO);
        if (updatedReservation == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedReservation);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);
    }
}
