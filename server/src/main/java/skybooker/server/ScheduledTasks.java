package skybooker.server;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import skybooker.server.service.implementation.DataUpdateService;

@Component
public class ScheduledTasks {

    private final DataUpdateService dataUpdateService;

    public ScheduledTasks(DataUpdateService dataUpdateService) {
        this.dataUpdateService = dataUpdateService;
    }

    @Scheduled(cron = "0 0 3 * * *", zone = "Africa/Casablanca")
    public void purge() {
        dataUpdateService.purgeReservations();
        dataUpdateService.purgeVols();
        dataUpdateService.updateCategories();
    }
}
