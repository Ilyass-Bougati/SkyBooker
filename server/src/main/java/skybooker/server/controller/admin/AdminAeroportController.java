package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.DTO.AeroportDTO;
import skybooker.server.DTO.VilleDTO;
import skybooker.server.service.AeroportService;
import skybooker.server.service.VilleService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<VilleDTO> villes = villeService.findAllDTO();
        model.addAttribute("villes", villes);
    }

    private void addVilleMapToModel(Model model) {
        Map<Long, VilleDTO> villesMap = villeService.findAllDTO().stream()
                .collect(Collectors.toMap(VilleDTO::getId, v -> v));
        model.addAttribute("villesMap", villesMap);
    }

    @GetMapping
    public String listAeroports(Model model) {
        List<AeroportDTO> aeroportsDTO = aeroportService.findAllDTO();
        model.addAttribute("aeroports", aeroportsDTO);
        addVilleMapToModel(model);
        model.addAttribute("pageTitle", "Gérer les Aéroports");
        return "admin/aeroports";
    }

    @GetMapping("/add")
    public String addAeroport(Model model) {
        model.addAttribute("aeroportDTO", new AeroportDTO());
        addVilleListToModel(model);
        model.addAttribute("pageTitle", "Ajouter un Aéroport");
        return "admin/add-edit-aeroport";
    }

    @PostMapping("/save")
    public String saveAeroport(@Valid @ModelAttribute("aeroportDTO") AeroportDTO aeroportDTO, BindingResult result,
                               Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            addVilleListToModel(model);
            model.addAttribute("pageTitle", (aeroportDTO.getId() == 0 ? "Ajouter" : "Modifier") + " un Aéroport");
            return "admin/add-edit-aeroport";
        }
        if (aeroportDTO.getId() == 0) {
            aeroportService.createDTO(aeroportDTO);
        } else {
            aeroportService.updateDTO(aeroportDTO);
        }
        redirectAttributes.addFlashAttribute("successMessage", "Aéroport sauvegardé avec succès !");
        return "redirect:/admin/aeroports";
    }

    @GetMapping("/edit/{id}")
    public String editAeroport(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            AeroportDTO aeroportDTO = aeroportService.findDTOById(id);
            model.addAttribute("aeroportDTO", aeroportDTO);
            addVilleListToModel(model);
            model.addAttribute("pageTitle", "Modifier un Aéroport");
            return "admin/add-edit-aeroport";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Aéroport non trouvé avec ID: " + id);
            return "redirect:/admin/aeroports";
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAeroport(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try{
            aeroportService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage","Aéroport supprimé avec succès !");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression de l'aéroport: " + e.getMessage());
        }
        return "redirect:/admin/aeroports";
    }
}