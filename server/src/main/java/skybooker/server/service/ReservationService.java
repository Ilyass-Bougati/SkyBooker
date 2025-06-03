package skybooker.server.service;

import skybooker.server.DTO.ReservationDTO;
import skybooker.server.entity.Reservation;

public interface ReservationService extends CrudDTO<ReservationDTO, Long>, CrudService<Reservation, Long> {
    void checkInClient(Long reservationId);
}
