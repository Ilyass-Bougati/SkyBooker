package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.entity.Aeroport;
import skybooker.server.entity.Ville;
import skybooker.server.service.AeroportService;
import skybooker.server.service.VilleService;

import java.util.List;

@Controller
@RequestMapping("/admin/aeroports")
public class AdminAeroportController {
    private final AeroportService aeroportService;
    private final VilleService villeService;

    @Autowired
    public AdminAeroportController(AeroportService aeroportService, VilleService villeService) {
        this.aeroportService = aeroportService;
        this.villeService = villeService;
    }

    private void addVilleListToModel(Model model) {
        List<Ville> villes = villeService.findAll();
        model.addAttribute("villes", villes);
    }

    @GetMapping
    public String listAeroports(Model model) {
        List<Aeroport> aeroports = aeroportService.findAll();
        model.addAttribute("aeroports", aeroports);
        model.addAttribute("pageTitle", "Gerer les Aeroports");
        return "admin/localisation/aeroports";
    }

    @GetMapping("/add")
    public String addAeroport(Model model) {
        model.addAttribute("aeroport", new Aeroport());
        addVilleListToModel(model);
        model.addAttribute("pageTitle", "Ajouter un Aéroport");
        return "admin/localisation/add-edit-aeroport";
    }

    @PostMapping("/save")
    public String saveAeroport(@Valid @ModelAttribute("aeroport") Aeroport aeroport, BindingResult result,
                               Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            addVilleListToModel(model);
            model.addAttribute("pageTitle", (aeroport.getId() == 0 ? "Ajouter" : "Modifier") + " un Aéroport");
            return "admin/localisation/add-edit-aeroport";
        }
        aeroportService.create(aeroport);
        redirectAttributes.addFlashAttribute("successMessage", "Aéroport sauvegardé avec succès !");
        return "redirect:/admin/aeroports";
    }

    @GetMapping("/edit/{id}")
    public String editAeroport(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Aeroport aeroport = aeroportService.findById(id);
        if (aeroport != null) {
            if (aeroport.getVille() == null) {
                aeroport.setVille(new Ville());
            }
            model.addAttribute("aeroport", aeroport);
            addVilleListToModel(model);
            model.addAttribute("pageTitle", "Modifier un Aeroport");
            return "admin/localisation/add-edit-aeroport";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Aeroport not found with ID: " + id);
            return "redirect:/admin/aeroports";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteAeroport(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try{
            aeroportService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage","Aeroport deleted successfully!");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting ville: " + e.getMessage());
        }
        return "redirect:/admin/aeroports";
    }
}
