package skybooker.server.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import skybooker.server.DTO.stats.FlightStatsDTO;
import skybooker.server.DTO.stats.RouteRevenueStatsDTO;
import skybooker.server.DTO.stats.RouteStatsDTO;
import skybooker.server.service.AirlineStatsService;

import java.util.List;

@Controller
@RequestMapping("/admin/statistics")
public class AdminStatsController {

    private final AirlineStatsService airlineStatsService;

    @Autowired
    public AdminStatsController(AirlineStatsService airlineStatsService) {
        this.airlineStatsService = airlineStatsService;
    }

    @GetMapping("/occupancy")
    public String showOccupancyStats(Model model) {
        List<FlightStatsDTO> flightStats = airlineStatsService.getFlightOccupancyAndRevenueStats();
        model.addAttribute("flightStats", flightStats);
        model.addAttribute("pageTitle", "Statistiques de Vol (Occupation & Revenue)");
        return "admin/stats/flight-stats";
    }

    @GetMapping("/passengers")
    public String showPassengerStats(Model model) {
        List<RouteStatsDTO> routeStats = airlineStatsService.getPassengerCountPerRoute();
        model.addAttribute("routeStats", routeStats);
        model.addAttribute("pageTitle", "Statistiques Passagers (par Route)");
        return "admin/stats/route-passenger-stats";
    }

    @GetMapping("/revenue")
    public String showRevenueStats(Model model) {
        List<RouteRevenueStatsDTO> routeRevenueStats = airlineStatsService.getTotalRevenuePerRoute();
        model.addAttribute("routeRevenueStats", routeRevenueStats);
        model.addAttribute("pageTitle", "Statistiques Revenue (par Route)");
        return "admin/stats/route-revenue-stats";
    }
}