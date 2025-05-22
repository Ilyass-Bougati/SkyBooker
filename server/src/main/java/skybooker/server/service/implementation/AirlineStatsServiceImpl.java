package skybooker.server.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skybooker.server.DTO.stats.FlightStatsDTO;
import skybooker.server.DTO.stats.RouteStatsDTO;
import skybooker.server.DTO.stats.RouteRevenueStatsDTO;
import skybooker.server.DTO.stats.PlaneOccupancyStatsDTO;
import skybooker.server.DTO.stats.FlightThresholdStatsDTO;
import skybooker.server.DTO.stats.ClientStatsDTO;

import skybooker.server.entity.Vol;
import skybooker.server.entity.Avion;
import skybooker.server.repository.VolRepository;
import skybooker.server.repository.ClientRepository;
import skybooker.server.repository.AvionRepository;
import skybooker.server.service.AirlineStatsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AirlineStatsServiceImpl implements AirlineStatsService {

    private final VolRepository volRepository;
    private final ClientRepository clientRepository;
    private final AvionRepository avionRepository;


    @Autowired
    public AirlineStatsServiceImpl(VolRepository volRepository,
                                   ClientRepository clientRepository, AvionRepository avionRepository) {
        this.volRepository = volRepository;
        this.clientRepository = clientRepository;
        this.avionRepository = avionRepository;
    }


    @Override
    public List<FlightStatsDTO> getFlightOccupancyAndRevenueStats() {
        List<Object[]> bookedCountAndRevenueResults = volRepository.findVolBookedCountAndTotalRevenue();
        Map<Long, FlightStatsDTO> statsMap = new HashMap<>();

        for (Object[] result : bookedCountAndRevenueResults) {
            Long volId = (Long) result[0];
            Long bookedCount = (Long) result[1];
            Double totalRevenue = ((Number) result[2]).doubleValue();


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
                    String depCode = (vol.getAeroportDepart() != null ? vol.getAeroportDepart().getIataCode() : "N/A");
                    String arrCode = (vol.getAeroportArrive() != null ? vol.getAeroportArrive().getIataCode() : "N/A");
                    stat.setVolIdentifier("Vol ID: " + vol.getId() + " (" + depCode + "-" + arrCode + ")");
                } else {
                    stat.setVolIdentifier("Vol ID: " + stat.getVolId() + " (Not Found)");
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
            Double totalRevenue = ((Number) result[2]).doubleValue();

            routeRevenueStats.add(new RouteRevenueStatsDTO(depAirportCode, arrAirportCode, totalRevenue));
        }
        return routeRevenueStats;
    }


    @Override
    public PlaneOccupancyStatsDTO getPlaneOccupancyStats(Long avionId, Integer year, Integer quarter) {
        List<Object[]> results;

        if (quarter == null) {
            results = volRepository.countPassengersAndSumCapacityByAvionAndYear(avionId, year);
        } else {
            results = volRepository.countPassengersAndSumCapacityByAvionAndYearAndQuarter(avionId, year, quarter);
        }


        PlaneOccupancyStatsDTO stats = new PlaneOccupancyStatsDTO();
        stats.setAvionId(avionId);
        stats.setTotalPassengers(0L);
        stats.setTotalCapacity(0.0);
        stats.setOccupancyRate(0.0);

        if (!results.isEmpty()) {
            Object[] result = results.get(0);
            Long totalPassengers = (Long) result[1];
            Double totalCapacity = ((Number) result[2]).doubleValue();


            stats.setTotalPassengers(totalPassengers);
            stats.setTotalCapacity(totalCapacity);

            if (totalCapacity != null && totalCapacity > 0) {
                stats.setOccupancyRate((totalPassengers.doubleValue() / totalCapacity) * 100.0);
            } else {
                stats.setOccupancyRate(0.0);
            }
        }

        Avion avion = avionRepository.findById(avionId).orElse(null);
        if (avion != null) {
            stats.setAvionIdentifier("Avion ID: " + avion.getId() + " (" + (avion.getModel() != null ? avion.getModel() : "N/A") + ")");
        } else {
            stats.setAvionIdentifier("Avion ID: " + avionId + " (Not Found)");
        }


        return stats;
    }

    @Override
    public List<FlightThresholdStatsDTO> getFlightsBelowOccupancyThreshold(Integer year, Integer quarter, Double threshold) {
        List<Object[]> results;

        if (quarter == null) {
            results = volRepository.countPassengersAndSumCapacityByYear(year);
        } else {
            results = volRepository.countPassengersAndSumCapacityByYearAndQuarter(year, quarter);
        }


        List<FlightThresholdStatsDTO> flightsBelowThreshold = new ArrayList<>();

        for (Object[] result : results) {
            Long volId = (Long) result[0];
            Long bookedCount = (Long) result[1];
            Double totalCapacity = ((Number) result[2]).doubleValue();

            Double occupancyRate = 0.0;
            if (totalCapacity != null && totalCapacity > 0) {
                occupancyRate = (bookedCount.doubleValue() / totalCapacity) * 100.0;
            }

            if (threshold != null && occupancyRate < threshold) {
                FlightThresholdStatsDTO dto = new FlightThresholdStatsDTO();
                dto.setVolId(volId);
                dto.setBookedCount(bookedCount);
                dto.setTotalCapacity(totalCapacity);
                dto.setOccupancyRate(occupancyRate);

                Vol vol = volRepository.findById(volId).orElse(null);
                if (vol != null) {
                    dto.setVolIdentifier("Vol ID: " + vol.getId());
                    String depCode = (vol.getAeroportDepart() != null ? vol.getAeroportDepart().getIataCode() : "N/A");
                    String arrCode = (vol.getAeroportArrive() != null ? vol.getAeroportArrive().getIataCode() : "N/A");
                    dto.setRoute(depCode + "-" + arrCode);
                    dto.setDate(vol.getDateDepart() != null ? vol.getDateDepart().toString() : "N/A");
                } else {
                    dto.setVolIdentifier("Vol ID: " + volId + " (Not Found)");
                    dto.setRoute("N/A");
                    dto.setDate("N/A");
                }


                flightsBelowThreshold.add(dto);
            }
        }

        if (!flightsBelowThreshold.isEmpty()) {
            flightsBelowThreshold.sort((s1, s2) -> Double.compare(s1.getOccupancyRate(), s2.getOccupancyRate()));
        }


        return flightsBelowThreshold;
    }


    @Override
    public List<ClientStatsDTO> getClientStatistics() {
        List<Object[]> reservationStatsResults = clientRepository.countReservationsAndTotalSpentPerClient();
        Map<Long, ClientStatsDTO> clientStatsMap = new HashMap<>();

        for (Object[] result : reservationStatsResults) {
            Long clientId = (Long) result[0];
            Long reservationCount = (Long) result[1];
            Double totalSpent;

            if (result[2] != null) {
                totalSpent = ((Number) result[2]).doubleValue();
            } else {
                totalSpent = 0.0;
            }

            String clientEmail = (String) result[3];

            ClientStatsDTO dto = clientStatsMap.getOrDefault(clientId, new ClientStatsDTO());
            dto.setClientId(clientId);
            dto.setClientEmail(clientEmail);
            dto.setReservationCount(reservationCount);
            dto.setTotalSpent(totalSpent);

            clientStatsMap.put(clientId, dto);
        }

        List<Object[]> passengerStatsResults = clientRepository.countPassengersPerClient();
        for (Object[] result : passengerStatsResults) {
            Long clientId = (Long) result[0];
            Long passengerCount = (Long) result[1];
            String clientEmail = (String) result[2];

            ClientStatsDTO dto = clientStatsMap.getOrDefault(clientId, new ClientStatsDTO());
            dto.setClientId(clientId);
            dto.setClientEmail(clientEmail);
            dto.setPassengerCount(passengerCount);

            clientStatsMap.put(clientId, dto);
        }

        List<ClientStatsDTO> clientStats = new ArrayList<>(clientStatsMap.values());


        return clientStats;
    }
}