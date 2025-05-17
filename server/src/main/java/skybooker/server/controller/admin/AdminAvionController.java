package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.entity.Avion;
import skybooker.server.entity.CompanieAerienne;
import skybooker.server.service.AvionService;
import skybooker.server.service.CompanieAerienneService;

import java.util.List;

@Controller
@RequestMapping("/admin/avion")
public class AdminAvionController {

    private final AvionService avionService;
    private final CompanieAerienneService companieAerienneService;

    @Autowired
    public AdminAvionController(AvionService avionService, CompanieAerienneService companieAerienneService) {
        this.avionService = avionService;
        this.companieAerienneService = companieAerienneService;
    }

    @GetMapping
    public String listAvions(Model model) {
        List<Avion> avions = avionService.findAll();
        model.addAttribute("avions", avions);
        model.addAttribute("pageTitle", "Gérer les Avions");
        return "admin/avion";
    }

    @GetMapping("/add")
    public String addAvion(Model model) {
        model.addAttribute("avion", new Avion());
        model.addAttribute("companieAeriennes", companieAerienneService.findAll());
        model.addAttribute("pageTitle", "Ajouter un Avion");
        return "admin/add-edit-avion";
    }

    @PostMapping("/save")
    public String saveAvion(@Valid @ModelAttribute("avion") Avion avion, BindingResult result,
                            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("companieAeriennes", companieAerienneService.findAll());
            model.addAttribute("pageTitle", (avion.getId() == 0 ? "Ajouter" : "Modifier") + " un Avion");
            return "admin/add-edit-avion";
        }
        avionService.create(avion);
        redirectAttributes.addFlashAttribute("successMessage", "Avion sauvegardé avec succès !");
        return "redirect:/admin/avion/edit/" + avion.getId();
    }

    @GetMapping("/edit/{id}")
    public String editAvion(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Avion avion = avionService.findById(id);
        if (avion != null) {
            model.addAttribute("avion", avion);
            model.addAttribute("companieAeriennes", companieAerienneService.findAll());
            model.addAttribute("pageTitle", "Modifier un Avion");
            return "admin/add-edit-avion";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Avion n'existe pas ! :" + id);
            return "redirect:/admin/avion";
        }
    }

    @GetMapping("/details/{id}")
    public String viewAvionDetails(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Avion avion = avionService.findById(id);
        if (avion != null) {
            model.addAttribute("avion", avion);
            model.addAttribute("pageTitle", "Détails de l'Avion");
            return "admin/view-avion";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Avion n'existe pas ! :" + id);
            return "redirect:/admin/avion";
        }
    }


    @GetMapping("/delete/{id}")
    public String deleteAvion(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            avionService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Avion supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression de l'Avion :" + e.getMessage());
        }
        return "redirect:/admin/avion";
    }
}