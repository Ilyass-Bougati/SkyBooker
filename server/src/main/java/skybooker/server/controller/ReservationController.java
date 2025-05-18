package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.ReservationDTO;
import skybooker.server.UserDetailsImpl;
import skybooker.server.entity.Reservation;
import skybooker.server.service.ReservationService;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserDetailsService userDetailsService;

    public ReservationController(ReservationService reservationService, UserDetailsService userDetailsService) {
        this.reservationService = reservationService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ReservationDTO>> getAllReservation(Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(principal.getName());
        Set<Reservation> reservations = userDetails.getClient().getReservations();
        List<ReservationDTO> reservationDTOs = reservations.stream().map(ReservationDTO::new).toList();
        return ResponseEntity.ok(reservationDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.findById(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new ReservationDTO(reservation));
        }
    }

    @PostMapping("/")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody @Valid ReservationDTO reservationDTO) {
        Reservation savedReservation = reservationService.createDTO(reservationDTO);
        if (savedReservation == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(new ReservationDTO(savedReservation));
    }

    @PutMapping("/")
    public ResponseEntity<ReservationDTO> updateReservation(@RequestBody @Valid ReservationDTO reservationDTO) {
        Reservation updatedReservation = reservationService.updateDTO(reservationDTO);
        if (updatedReservation == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(new ReservationDTO(updatedReservation));
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);
    }
}
