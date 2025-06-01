package skybooker.server.service.implementation;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import skybooker.server.repository.ReservationRepository;
import skybooker.server.repository.VolRepository;

import java.time.LocalDateTime;

@Service
public class DataPurgeService {

    Logger logger = LoggerFactory.getLogger(DataPurgeService.class);
    private final ReservationRepository reservationRepository;
    private final VolRepository volRepository;

    public DataPurgeService(ReservationRepository reservationRepository, VolRepository volRepository) {
        this.reservationRepository = reservationRepository;
        this.volRepository = volRepository;
    }

    @Async("purgeTask")
    @Transactional
    public void purgeReservations() {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
        reservationRepository.purge(oneYearAgo);
        logger.info("Purged the reservations");
    }

    @Async("purgeTask")
    @Transactional
    public void purgeVols() {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
        volRepository.purge(oneYearAgo);
        logger.info("Purged the vols");
    }
}
