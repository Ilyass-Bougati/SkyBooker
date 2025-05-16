package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.entity.Ville;
import skybooker.server.service.VilleService;

import java.util.List;

@Controller
@RequestMapping("/admin/villes")
public class AdminVilleController {
    private final VilleService villeService;

    @Autowired
    public AdminVilleController(VilleService villeService) {
        this.villeService = villeService;
    }

    @GetMapping
    public String listVilles(Model model) {
        List<Ville> villes = villeService.findAll();
        model.addAttribute("villes", villes);
        model.addAttribute("pageTitle", "Gerer les Villes");
        return "admin/localisation/villes";
    }

    @GetMapping("/add")
    public String AddVilles(Model model) {
        model.addAttribute("ville", new Ville());
        model.addAttribute("pageTitle", "Ajouter une nouvelle ville");
        return "admin/localisation/add-edit-ville";
    }

    @PostMapping("/save")
    public String saveVille(@Valid @ModelAttribute("ville") Ville ville, BindingResult result,
                            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", (ville.getId() == null ? "Ajouter" : "Modifier") +" une nouvelle ville");
            return "admin/localisation/add-edit-ville";
        }

        villeService.create(ville);
        redirectAttributes.addFlashAttribute("successMessage", "Ville saved successfully!");
        return "redirect:/admin/villes";
    }

    @GetMapping("/edit/{id}")
    public String editVille(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Ville ville = villeService.findById(id);
        if (ville != null) {
            model.addAttribute("ville", ville);
            model.addAttribute("pageTitle", "Modifier une nouvelle ville");
            return "admin/localisation/add-edit-ville";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Ville not found with ID: " + id);
            return "redirect:/admin/villes";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteVille(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try{
            villeService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Ville deleted successfully!");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage","Error deleting ville: " + e.getMessage());
        }
        return "redirect:/admin/villes";
    }
}
