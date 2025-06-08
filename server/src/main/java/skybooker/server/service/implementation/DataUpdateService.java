package skybooker.server.service.implementation;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import skybooker.server.entity.Passager;
import skybooker.server.repository.CategorieRepository;
import skybooker.server.repository.PassagerRepository;
import skybooker.server.repository.ReservationRepository;
import skybooker.server.repository.VolRepository;
import skybooker.server.repository.BilletRepository;
import skybooker.server.service.PassagerService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class DataUpdateService {

    private final CacheManager cacheManager;
    Logger logger = LoggerFactory.getLogger(DataUpdateService.class);

    private final PassagerService passagerService;
    private final PassagerRepository passagerRepository;
    private final CategorieRepository categorieRepository;
    private final ReservationRepository reservationRepository;
    private final VolRepository volRepository;
    private final BilletRepository billetRepository;

    public DataUpdateService(ReservationRepository reservationRepository, VolRepository volRepository, PassagerService passagerService, PassagerRepository passagerRepository, CategorieRepository categorieRepository, CacheManager cacheManager, BilletRepository billetRepository) {
        this.reservationRepository = reservationRepository;
        this.volRepository = volRepository;
        this.passagerService = passagerService;
        this.passagerRepository = passagerRepository;
        this.categorieRepository = categorieRepository;
        this.cacheManager = cacheManager;
        this.billetRepository = billetRepository;
    }

    @Async("purgeTask")
    @Transactional
    public void purgeReservations() {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
        reservationRepository.purge(oneYearAgo);
        logger.info("Purged the reservations");
    }

    @Async("purgeTask")
    public void purgeUsedBillets() {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
        billetRepository.purgeUsedBillets(oneYearAgo);
        logger.info("Purged used billets older than one year");
    }

    @Async("purgeTask")
    @Transactional
    public void purgeVols() {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
        volRepository.purge(oneYearAgo);
        logger.info("Purged the vols");
    }


    @Async("purgeTask")
    public void evictAllCaches(){
        cacheManager.getCacheNames().
                forEach(cacheName -> Objects.requireNonNull(cacheManager.getCache(cacheName)).clear());
        logger.info("Evicted all caches");
    }

    /**
     * This function update the ages and categories of all the passagers
     * in case some surpassed the upper age of their categories
     */
    @Async("purgeTask")
    @Transactional
    public void updateCategories() {
        List<Passager> passagers = passagerService.findAll();
        for (Passager passager : passagers) {
            passager.updateCategorie(categorieRepository);
            passagerRepository.save(passager);
        }
        logger.info("Updated the infos of {} passagers", passagers.size());
    }
}
