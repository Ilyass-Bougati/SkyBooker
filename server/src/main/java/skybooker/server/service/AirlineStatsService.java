package skybooker.server.service;


import skybooker.server.DTO.stats.FlightStatsDTO;
import skybooker.server.DTO.stats.RouteStatsDTO;
import skybooker.server.DTO.stats.RouteRevenueStatsDTO;

import java.util.List;

public interface AirlineStatsService {
    List<FlightStatsDTO> getFlightOccupancyAndRevenueStats();
    List<RouteStatsDTO> getPassengerCountPerRoute();
    List<RouteRevenueStatsDTO> getTotalRevenuePerRoute();
}