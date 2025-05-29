package skybooker.server.service;

import skybooker.server.DTO.ReservationDTO;
import skybooker.server.entity.Reservation;

public interface ReservationService extends CrudService<Reservation, Long>, CrudDTO<Reservation, ReservationDTO> {
    void checkInClient(Long reservationId);
}
