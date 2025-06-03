package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.DTO.VilleDTO;
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
        List<VilleDTO> villes = villeService.findAllDTO();
        model.addAttribute("villes", villes);
        model.addAttribute("pageTitle", "Gerer les Villes");
        return "admin/villes";
    }

    @GetMapping("/add")
    public String AddVilles(Model model) {
        model.addAttribute("ville", new VilleDTO());
        model.addAttribute("pageTitle", "Ajouter une nouvelle ville");
        return "admin/add-edit-ville";
    }

    @PostMapping("/save")
    public String saveVille(@Valid @ModelAttribute("ville") VilleDTO villeDTO, BindingResult result,
                            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", (villeDTO.getId() == 0 ? "Ajouter" : "Modifier") + " une nouvelle ville");
            return "admin/add-edit-ville";
        }

        try {
            if (villeDTO.getId() == 0) {
                villeService.createDTO(villeDTO);
            } else {
                villeService.updateDTO(villeDTO);
            }
            redirectAttributes.addFlashAttribute("successMessage", "Ville saved successfully!");
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            result.rejectValue("nom", "duplicate.villeDTO.nom", "A city with this name already exists.");
            model.addAttribute("pageTitle", (villeDTO.getId() == 0 ? "Ajouter" : "Modifier") + " une nouvelle ville");
            return "admin/add-edit-ville";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving ville: " + e.getMessage());
            model.addAttribute("pageTitle", (villeDTO.getId() == 0 ? "Ajouter" : "Modifier") + " une nouvelle ville");
            return "admin/add-edit-ville";
        }
        return "redirect:/admin/villes";
    }

    @GetMapping("/edit/{id}")
    public String editVille(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        VilleDTO villeDTO = villeService.findDTOById(id);
        if (villeDTO != null) {
            model.addAttribute("ville", villeDTO);
            model.addAttribute("pageTitle", "Modifier une nouvelle ville");
            return "admin/add-edit-ville";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Ville not found with ID: " + id);
            return "redirect:/admin/villes";
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteVille(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            villeService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Ville deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting ville: " + e.getMessage());
        }
        return "redirect:/admin/villes";
    }
}