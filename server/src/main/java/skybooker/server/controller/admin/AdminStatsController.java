package skybooker.server.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import skybooker.server.service.AirlineStatsService;
import skybooker.server.service.AvionService;
import skybooker.server.DTO.stats.FlightStatsDTO;
import skybooker.server.DTO.stats.RouteStatsDTO;
import skybooker.server.DTO.stats.RouteRevenueStatsDTO;
import skybooker.server.DTO.stats.PlaneOccupancyStatsDTO;
import skybooker.server.DTO.stats.FlightThresholdStatsDTO;
import skybooker.server.DTO.stats.ClientStatsDTO;

import java.util.List;

@Controller
@RequestMapping("/admin/statistics")
public class AdminStatsController {

    private final AirlineStatsService airlineStatsService;
    private final AvionService avionService;

    @Autowired
    public AdminStatsController(AirlineStatsService airlineStatsService, AvionService avionService) {
        this.airlineStatsService = airlineStatsService;
        this.avionService = avionService;
    }

    @GetMapping("/occupancy")
    public String showFlightStats(Model model) {
        List<FlightStatsDTO> flightStats = airlineStatsService.getFlightOccupancyAndRevenueStats();
        model.addAttribute("flightStats", flightStats);
        model.addAttribute("pageTitle", "Statistiques de Vol (Occupation & Revenue)");
        return "admin/stats/flight-stats";
    }

    @GetMapping("/passengers")
    public String showRoutePassengerStats(Model model) {
        List<RouteStatsDTO> routeStats = airlineStatsService.getPassengerCountPerRoute();
        model.addAttribute("routeStats", routeStats);
        model.addAttribute("pageTitle", "Statistiques Passagers (par Route)");
        return "admin/stats/route-passenger-stats";
    }

    @GetMapping("/revenue")
    public String showRouteRevenueStats(Model model) {
        List<RouteRevenueStatsDTO> routeRevenueStats = airlineStatsService.getTotalRevenuePerRoute();
        model.addAttribute("routeRevenueStats", routeRevenueStats);
        model.addAttribute("pageTitle", "Statistiques Revenue (par Route)");
        return "admin/stats/route-revenue-stats";
    }

    @GetMapping("/plane-occupancy")
    public String showPlaneOccupancyStats(
            @RequestParam(value = "avionId", required = false) Long avionId,
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "quarter", required = false) Integer quarter,
            Model model) {

        model.addAttribute("pageTitle", "Statistiques Occupation par Avion");
        model.addAttribute("avions", avionService.findAll());

        if (avionId != null && year != null) {
            PlaneOccupancyStatsDTO stats = airlineStatsService.getPlaneOccupancyStats(avionId, year, quarter);
            model.addAttribute("planeStats", stats);
        }

        model.addAttribute("selectedAvionId", avionId);
        model.addAttribute("selectedYear", year);
        model.addAttribute("selectedQuarter", quarter);


        return "admin/stats/plane-occupancy";
    }

    @GetMapping("/flights-below-threshold")
    public String showFlightsBelowThreshold(
            @RequestParam(value = "year", required = false) Integer year,
            @RequestParam(value = "quarter", required = false) Integer quarter,
            @RequestParam(value = "threshold", required = false) Double threshold,
            Model model) {

        model.addAttribute("pageTitle", "Vols en dessous du seuil d'occupation");

        if (year != null && threshold != null) {
            List<FlightThresholdStatsDTO> flights = airlineStatsService.getFlightsBelowOccupancyThreshold(year, quarter, threshold);
            model.addAttribute("flightsBelowThreshold", flights);
        }

        model.addAttribute("selectedYear", year);
        model.addAttribute("selectedQuarter", quarter);
        model.addAttribute("selectedThreshold", threshold);


        return "admin/stats/flights-below-threshold";
    }

    @GetMapping("/client-stats")
    public String showClientStats(Model model) {
        List<ClientStatsDTO> clientStats = airlineStatsService.getClientStatistics();
        model.addAttribute("clientStats", clientStats);
        model.addAttribute("pageTitle", "Statistiques Clients");
        return "admin/stats/client-stats";
    }

}