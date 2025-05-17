package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.entity.Capacite;
import skybooker.server.entity.Avion; // Need Avion entity
import skybooker.server.entity.Classe; // Need Classe entity
import skybooker.server.service.CapaciteService;
import skybooker.server.service.AvionService; // Need Avion Service for dropdown
import skybooker.server.service.ClasseService; // Need Classe Service for dropdown

import java.util.List;

@Controller
@RequestMapping("/admin/capacite")
public class AdminCapaciteController {

    private final CapaciteService capaciteService;
    private final AvionService avionService; // To populate Avion dropdown
    private final ClasseService classeService; // To populate Classe dropdown

    @Autowired
    public AdminCapaciteController(CapaciteService capaciteService, AvionService avionService, ClasseService classeService) {
        this.capaciteService = capaciteService;
        this.avionService = avionService;
        this.classeService = classeService;
    }

    // We might not need a list all capacities view if managed via Avion
    // @GetMapping
    // public String listCapacites(Model model) {
    //     List<Capacite> capacites = capaciteService.findAll(); // Assuming findAll
    //     model.addAttribute("capacites", capacites);
    //     model.addAttribute("pageTitle", "Gérer les Capacités");
    //     return "admin/capacite"; // Thymeleaf template for listing capacities
    // }

    @GetMapping("/add")
    public String addCapacite(@RequestParam(value = "avionId", required = false) Long avionId, Model model) {
        Capacite capacite = new Capacite();
        if (avionId != null) {
            Avion avion = avionService.findById(avionId);
            if (avion != null) {
                capacite.setAvion(avion); // Pre-select the avion if ID is provided
            }
        }
        model.addAttribute("capacite", capacite);
        model.addAttribute("avions", avionService.findAll()); // For Avion dropdown
        model.addAttribute("classes", classeService.findAll()); // For Classe dropdown
        model.addAttribute("pageTitle", "Ajouter une Capacité");
        return "admin/add-edit-capacite"; // Thymeleaf template for add/edit form
    }

    @PostMapping("/save")
    public String saveCapacite(@Valid @ModelAttribute("capacite") Capacite capacite, BindingResult result,
                               Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("avions", avionService.findAll()); // Repopulate dropdowns on error
            model.addAttribute("classes", classeService.findAll());
            model.addAttribute("pageTitle", (capacite.getId() == 0 ? "Ajouter" : "Modifier") + " une Capacité");
            return "admin/add-edit-capacite";
        }
        capaciteService.create(capacite); // Assuming a save method in your service
        redirectAttributes.addFlashAttribute("successMessage", "Capacité sauvegardée avec succès !");

        // Redirect back to the Avion edit page if this capacity is linked to an avion
        if (capacite.getAvion() != null && capacite.getAvion().getId() != 0) {
            return "redirect:/admin/avion/edit/" + capacite.getAvion().getId();
        }

        return "redirect:/admin/capacite"; // Fallback redirect if not linked to an avion
    }

    @GetMapping("/edit/{id}")
    public String editCapacite(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Capacite capacite = capaciteService.findById(id); // Assuming findById
        if (capacite != null) {
            model.addAttribute("capacite", capacite);
            model.addAttribute("avions", avionService.findAll()); // For Avion dropdown
            model.addAttribute("classes", classeService.findAll()); // For Classe dropdown
            model.addAttribute("pageTitle", "Modifier une Capacité");
            return "admin/add-edit-capacite";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Capacité n'existe pas ! :" + id);
            // Redirect to a relevant page, maybe list of avions or capacities
            return "redirect:/admin/avion";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCapacite(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Long avionId = null;
        Capacite capaciteToDelete = capaciteService.findById(id);
        if (capaciteToDelete != null && capaciteToDelete.getAvion() != null) {
            avionId = capaciteToDelete.getAvion().getId();
        }

        try {
            capaciteService.deleteById(id); // Assuming deleteById
            redirectAttributes.addFlashAttribute("successMessage", "Capacité supprimée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression de la Capacité :" + e.getMessage());
        }

        // Redirect back to the Avion edit page if this capacity was linked to an avion
        if (avionId != null && avionId != 0) {
            return "redirect:/admin/avion/edit/" + avionId;
        }

        return "redirect:/admin/avion"; // Fallback redirect
    }
}