package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.entity.Capacite;
import skybooker.server.entity.Avion;
import skybooker.server.service.CapaciteService;
import skybooker.server.service.AvionService;
import skybooker.server.service.ClasseService;


@Controller
@RequestMapping("/admin/capacite")
public class AdminCapaciteController {

    private final CapaciteService capaciteService;
    private final AvionService avionService;
    private final ClasseService classeService;

    @Autowired
    public AdminCapaciteController(CapaciteService capaciteService, AvionService avionService, ClasseService classeService) {
        this.capaciteService = capaciteService;
        this.avionService = avionService;
        this.classeService = classeService;
    }


    @GetMapping("/add")
    public String addCapacite(@RequestParam(value = "avionId", required = false) Long avionId, Model model) {
        Capacite capacite = new Capacite();
        if (avionId != null) {
            Avion avion = avionService.findById(avionId);
            if (avion != null) {
                capacite.setAvion(avion);
            }
        }
        model.addAttribute("capacite", capacite);
        model.addAttribute("avions", avionService.findAllDTO());
        model.addAttribute("classes", classeService.findAllDTO());
        model.addAttribute("pageTitle", "Ajouter une Capacité");
        return "admin/add-edit-capacite";
    }

    @PostMapping("/save")
    public String saveCapacite(@Valid @ModelAttribute("capacite") Capacite capacite, BindingResult result,
                               Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("avions", avionService.findAllDTO());
            model.addAttribute("classes", classeService.findAllDTO());
            model.addAttribute("pageTitle", (capacite.getId() == 0 ? "Ajouter" : "Modifier") + " une Capacité");
            return "admin/add-edit-capacite";
        }
        capaciteService.create(capacite);
        redirectAttributes.addFlashAttribute("successMessage", "Capacité sauvegardée avec succès !");

        if (capacite.getAvion() != null && capacite.getAvion().getId() != 0) {
            return "redirect:/admin/avion/edit/" + capacite.getAvion().getId();
        }

        return "redirect:/admin/capacite";
    }

    @GetMapping("/edit/{id}")
    public String editCapacite(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Capacite capacite = capaciteService.findById(id);
        if (capacite != null) {
            model.addAttribute("capacite", capacite);
            model.addAttribute("avions", avionService.findAllDTO());
            model.addAttribute("classes", classeService.findAllDTO());
            model.addAttribute("pageTitle", "Modifier une Capacité");
            return "admin/add-edit-capacite";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Capacité n'existe pas ! :" + id);
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
            capaciteService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Capacité supprimée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression de la Capacité :" + e.getMessage());
        }

        if (avionId != null && avionId != 0) {
            return "redirect:/admin/avion/edit/" + avionId;
        }

        return "redirect:/admin/avion";
    }
}