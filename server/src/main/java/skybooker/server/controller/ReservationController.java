package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.ReservationDTO;
import skybooker.server.entity.Reservation;
import skybooker.server.service.ReservationService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ReservationDTO>> getAllReservation() {
        List<Reservation> reservations = reservationService.findAll();
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
    public ResponseEntity<Reservation> createReservation(@RequestBody @Valid ReservationDTO reservationDTO) {
        return ResponseEntity.ok(reservationService.createDTO(reservationDTO));
    }

    @PutMapping("/")
    public ResponseEntity<Reservation> updateReservation(@RequestBody @Valid ReservationDTO reservationDTO) {
        Reservation reservation = reservationService.updateDTO(reservationDTO);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(reservation);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteById(id);
    }
}
