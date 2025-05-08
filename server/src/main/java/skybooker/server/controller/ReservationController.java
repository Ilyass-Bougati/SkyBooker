package skybooker.server.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skybooker.server.DTO.ReservationDTO;
import skybooker.server.entity.Reservation;
import skybooker.server.service.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/")
    public ResponseEntity<List<Reservation>> getAllReservation() {
        return ResponseEntity.ok(reservationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Reservation reservation = reservationService.findById(id);
        if (reservation == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(reservation);
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
