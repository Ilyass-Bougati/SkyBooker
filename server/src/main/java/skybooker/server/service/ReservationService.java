package skybooker.server.service;

import skybooker.server.DTO.BilletDTO;
import skybooker.server.DTO.ReservationDTO;
import skybooker.server.entity.Reservation;

import java.util.List;

public interface ReservationService extends CrudDTO<ReservationDTO, Long>, CrudService<Reservation, Long> {
    void checkInClient(Long reservationId);
    List<BilletDTO> getBillets(Long idReservation);
}
