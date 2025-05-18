package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import skybooker.server.DTO.stats.FlightStatsDTO;
import skybooker.server.DTO.stats.RouteStatsDTO;
import skybooker.server.DTO.stats.RouteRevenueStatsDTO;
import skybooker.server.entity.Vol;
import skybooker.server.repository.BilletRepository;
import skybooker.server.repository.VolRepository;
import skybooker.server.service.AirlineStatsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AirlineStatsServiceImpl implements AirlineStatsService {

    private final VolRepository volRepository;
    private final BilletRepository billetRepository;

    @Autowired
    public AirlineStatsServiceImpl(VolRepository volRepository, BilletRepository billetRepository) {
        this.volRepository = volRepository;
        this.billetRepository = billetRepository;
    }

    @Override
    public List<FlightStatsDTO> getFlightOccupancyAndRevenueStats() {
        List<Object[]> bookedCountAndRevenueResults = volRepository.findVolBookedCountAndTotalRevenue();
        Map<Long, FlightStatsDTO> statsMap = new HashMap<>();

        for (Object[] result : bookedCountAndRevenueResults) {
            Long volId = (Long) result[0];
            Long bookedCount = (Long) result[1];
            Double totalRevenue = (Double) result[2];

            FlightStatsDTO dto = statsMap.getOrDefault(volId, new FlightStatsDTO());
            dto.setVolId(volId);
            dto.setBookedCount(bookedCount);
            dto.setRevenue(totalRevenue);

            statsMap.put(volId, dto);
        }

        List<Object[]> capacityResults = volRepository.findVolCapacities();
        for (Object[] result : capacityResults) {
            Long volId = (Long) result[0];
            Double totalCapacity = ((Number) result[1]).doubleValue();

            FlightStatsDTO dto = statsMap.getOrDefault(volId, new FlightStatsDTO());
            dto.setVolId(volId);

            dto.setTotalCapacity(totalCapacity);

            if (dto.getBookedCount() != null && dto.getTotalCapacity() != null && dto.getTotalCapacity() > 0) {
                dto.setOccupancyRate((dto.getBookedCount().doubleValue() / dto.getTotalCapacity()) * 100.0);
            } else {
                dto.setOccupancyRate(0.0);
            }

            statsMap.put(volId, dto);
        }

        List<FlightStatsDTO> flightStats = new ArrayList<>(statsMap.values());

        for(FlightStatsDTO stat : flightStats) {
            if (stat.getVolId() != null) {
                Vol vol = volRepository.findById(stat.getVolId()).orElse(null);
                if (vol != null) {
                    stat.setVolIdentifier("Vol ID: " + vol.getId());
                }
            }
        }

        return flightStats;
    }

    @Override
    public List<RouteStatsDTO> getPassengerCountPerRoute() {
        List<Object[]> results = volRepository.findPassengerCountPerRoute();
        List<RouteStatsDTO> routeStats = new ArrayList<>();

        for (Object[] result : results) {
            String depAirportCode = (String) result[0];
            String arrAirportCode = (String) result[1];
            Long passengerCount = (Long) result[2];
            routeStats.add(new RouteStatsDTO(depAirportCode, arrAirportCode, passengerCount));
        }
        return routeStats;
    }


    @Override
    public List<RouteRevenueStatsDTO> getTotalRevenuePerRoute() {
        List<Object[]> results = volRepository.findTotalRevenuePerRoute();
        List<RouteRevenueStatsDTO> routeRevenueStats = new ArrayList<>();

        for (Object[] result : results) {
            String depAirportCode = (String) result[0];
            String arrAirportCode = (String) result[1];
            Double totalRevenue = (Double) result[2];
            routeRevenueStats.add(new RouteRevenueStatsDTO(depAirportCode, arrAirportCode, totalRevenue));
        }
        return routeRevenueStats;
    }
}