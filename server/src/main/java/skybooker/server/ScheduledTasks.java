package skybooker.server;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import skybooker.server.service.implementation.DataPurgeService;

@Component
public class ScheduledTasks {

    private final DataPurgeService dataPurgeService;

    public ScheduledTasks(DataPurgeService dataPurgeService) {
        this.dataPurgeService = dataPurgeService;
    }

    @Scheduled(cron = "0 0 3 * * *", zone = "Africa/Casablanca")
    public void purge() {
        dataPurgeService.purgeReservations();
        dataPurgeService.purgeVols();
    }
}
