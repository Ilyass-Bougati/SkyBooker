package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.entity.Reservation;
import skybooker.server.service.ReservationService;
import skybooker.server.service.ClientService;
import skybooker.server.service.VolService;
import skybooker.server.enums.EtatReservation;


import java.util.List;

@Controller
@RequestMapping("/admin/reservation")
public class AdminReservationController {

    private final ReservationService reservationService;
    private final ClientService clientService;
    private final VolService volService;

    @Autowired
    public AdminReservationController(ReservationService reservationService, ClientService clientService, VolService volService) {
        this.reservationService = reservationService;
        this.clientService = clientService;
        this.volService = volService;
    }

    @GetMapping
    public String listReservations(Model model) {
        List<Reservation> reservations = reservationService.findAll();
        model.addAttribute("reservations", reservations);
        model.addAttribute("pageTitle", "Gérer les Réservations");
        return "admin/reservation";
    }

    @GetMapping("/add")
    public String addReservation(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("vols", volService.findAll());
        model.addAttribute("etatsReservation", EtatReservation.values());
        model.addAttribute("pageTitle", "Ajouter une Réservation");
        return "admin/add-edit-reservation";
    }

    @PostMapping("/save")
    public String saveReservation(@Valid @ModelAttribute("reservation") Reservation reservation, BindingResult result,
                                  Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("clients", clientService.findAll());
            model.addAttribute("vols", volService.findAll());
            model.addAttribute("etatsReservation", EtatReservation.values());
            model.addAttribute("pageTitle", (reservation.getId() == 0 ? "Ajouter" : "Modifier") + " une Réservation");
            return "admin/add-edit-reservation";
        }
        reservationService.create(reservation);
        redirectAttributes.addFlashAttribute("successMessage", "Réservation sauvegardée avec succès !");
        return "redirect:/admin/reservation";
    }

    @GetMapping("/edit/{id}")
    public String editReservation(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Reservation reservation = reservationService.findById(id);
        if (reservation != null) {
            model.addAttribute("reservation", reservation);
            model.addAttribute("clients", clientService.findAll());
            model.addAttribute("vols", volService.findAll());
            model.addAttribute("etatsReservation", EtatReservation.values());
            model.addAttribute("pageTitle", "Modifier une Réservation");
            return "admin/add-edit-reservation";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Réservation n'existe pas ! :" + id);
            return "redirect:/admin/reservation";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteReservation(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            reservationService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Réservation supprimé avec succès !");
        } catch (Exception e) {
            System.err.println("Error deleting reservation: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression de la Réservation : " + e.getMessage());
        }
        return "redirect:/admin/reservation";
    }
}