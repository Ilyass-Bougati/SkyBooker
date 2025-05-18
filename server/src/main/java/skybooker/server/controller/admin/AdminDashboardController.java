package skybooker.server.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import skybooker.server.service.AirlineStatsService;
import skybooker.server.DTO.stats.FlightStatsDTO;
import skybooker.server.DTO.stats.RouteStatsDTO;
import skybooker.server.DTO.stats.RouteRevenueStatsDTO;

import java.util.List;

@Controller
@RequestMapping("/admin/dashboard")
public class AdminDashboardController {

    private final AirlineStatsService airlineStatsService;

    @Autowired
    public AdminDashboardController(AirlineStatsService airlineStatsService) {
        this.airlineStatsService = airlineStatsService;
    }

    @GetMapping
    public String showDashboard(Model model) {
        model.addAttribute("pageTitle", "Dashboard");

        List<FlightStatsDTO> flightStats = airlineStatsService.getFlightOccupancyAndRevenueStats();
        List<RouteStatsDTO> routeStats = airlineStatsService.getPassengerCountPerRoute();
        List<RouteRevenueStatsDTO> routeRevenueStats = airlineStatsService.getTotalRevenuePerRoute();

        model.addAttribute("flightStats", flightStats);
        model.addAttribute("routeStats", routeStats);
        model.addAttribute("routeRevenueStats", routeRevenueStats);

        return "admin/dashboard";
    }
}