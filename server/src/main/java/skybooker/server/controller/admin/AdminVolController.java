package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.entity.Vol;
import skybooker.server.enums.EtatVol;
import skybooker.server.service.VolService;
import skybooker.server.service.AeroportService;
import skybooker.server.service.AvionService;

import java.util.List;

@Controller
@RequestMapping("/admin/vol")
public class AdminVolController {

    private final VolService volService;
    private final AeroportService aeroportService;
    private final AvionService avionService;

    @Autowired
    public AdminVolController(VolService volService, AeroportService aeroportService, AvionService avionService) {
        this.volService = volService;
        this.aeroportService = aeroportService;
        this.avionService = avionService;
    }

    @GetMapping
    public String listVols(Model model) {
        List<Vol> vols = volService.findAll();
        model.addAttribute("vols", vols);
        model.addAttribute("pageTitle", "Gérer les Vols");
        return "admin/vol";
    }

    @GetMapping("/add")
    public String addVol(Model model) {
        model.addAttribute("vol", new Vol());
        model.addAttribute("aeroports", aeroportService.findAll());
        model.addAttribute("avions", avionService.findAll());
        model.addAttribute("etatsVol", EtatVol.values());
        model.addAttribute("pageTitle", "Ajouter un Vol");
        return "admin/add-edit-vol";
    }

    @PostMapping("/save")
    public String saveVol(@Valid @ModelAttribute("vol") Vol vol, BindingResult result,
                          Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("aeroports", aeroportService.findAll());
            model.addAttribute("avions", avionService.findAll());
            model.addAttribute("pageTitle", (vol.getId() == 0 ? "Ajouter" : "Modifier") + " un Vol");
            return "admin/add-edit-vol";
        }
        volService.create(vol);
        redirectAttributes.addFlashAttribute("successMessage", "Vol sauvegardé avec succès !");
        return "redirect:/admin/vol";
    }

    @GetMapping("/edit/{id}")
    public String editVol(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Vol vol = volService.findById(id);
        if (vol != null) {
            model.addAttribute("vol", vol);
            model.addAttribute("aeroports", aeroportService.findAll());
            model.addAttribute("avions", avionService.findAll());
            model.addAttribute("etatsVol", EtatVol.values());
            model.addAttribute("pageTitle", "Modifier un Vol");
            return "admin/add-edit-vol";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Vol n'existe pas ! :" + id);
            return "redirect:/admin/vol";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteVol(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            volService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Vol supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression du Vol :" + e.getMessage());
        }
        return "redirect:/admin/vol";
    }
}