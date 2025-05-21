package skybooker.server.service;

import skybooker.server.DTO.stats.FlightStatsDTO;
import skybooker.server.DTO.stats.RouteStatsDTO;
import skybooker.server.DTO.stats.RouteRevenueStatsDTO;
import skybooker.server.DTO.stats.PlaneOccupancyStatsDTO;
import skybooker.server.DTO.stats.FlightThresholdStatsDTO;
import skybooker.server.DTO.stats.ClientStatsDTO;


import java.util.List;

public interface AirlineStatsService {
    List<FlightStatsDTO> getFlightOccupancyAndRevenueStats();
    List<RouteStatsDTO> getPassengerCountPerRoute();
    List<RouteRevenueStatsDTO> getTotalRevenuePerRoute();
    PlaneOccupancyStatsDTO getPlaneOccupancyStats(Long avionId, Integer year, Integer quarter);
    List<FlightThresholdStatsDTO> getFlightsBelowOccupancyThreshold(Integer year, Integer quarter, Double threshold);
    List<ClientStatsDTO> getClientStatistics();
}