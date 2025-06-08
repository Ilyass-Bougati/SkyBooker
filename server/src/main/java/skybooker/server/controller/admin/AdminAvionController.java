package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.DTO.AvionDTO;
import skybooker.server.DTO.CompanieAerienneDTO;
import skybooker.server.DTO.CapaciteDTO;
import skybooker.server.DTO.ClasseDTO;
import skybooker.server.service.AvionService;
import skybooker.server.service.CompanieAerienneService;
import skybooker.server.service.CapaciteService;
import skybooker.server.service.ClasseService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/avion")
public class AdminAvionController {
    private final AvionService avionService;
    private final CompanieAerienneService companieAerienneService;
    private final CapaciteService capaciteService;
    private final ClasseService classeService;

    @Autowired
    public AdminAvionController(AvionService avionService, CompanieAerienneService companieAerienneService,
                                CapaciteService capaciteService, ClasseService classeService) {
        this.avionService = avionService;
        this.companieAerienneService = companieAerienneService;
        this.capaciteService = capaciteService;
        this.classeService = classeService;
    }

    private void addCompanieAerienneListToModel(Model model) {
        List<CompanieAerienneDTO> companies = companieAerienneService.findAllDTO();
        model.addAttribute("companieAeriennes", companies);
    }

    private void addClasseMapToModel(Model model) {
        Map<Long, ClasseDTO> classesMap = classeService.findAllDTO().stream()
                .collect(Collectors.toMap(ClasseDTO::getId, c -> c));
        model.addAttribute("classesMap", classesMap);
    }

    @GetMapping
    public String listAvions(Model model) {
        List<AvionDTO> avionsDTO = avionService.findAllDTO();
        model.addAttribute("avions", avionsDTO);

        Map<Long, CompanieAerienneDTO> companieAeriennesMap = companieAerienneService.findAllDTO().stream()
                .collect(Collectors.toMap(CompanieAerienneDTO::getId, c -> c));
        model.addAttribute("companieAeriennesMap", companieAeriennesMap);

        model.addAttribute("pageTitle", "Gérer les Avions");
        return "admin/avion";
    }

    @GetMapping("/add")
    public String addAvion(Model model) {
        model.addAttribute("avionDTO", new AvionDTO());
        addCompanieAerienneListToModel(model);
        model.addAttribute("pageTitle", "Ajouter un Avion");
        return "admin/add-edit-avion";
    }

    @PostMapping("/save")
    public String saveAvion(@Valid @ModelAttribute("avionDTO") AvionDTO avionDTO, BindingResult result,
                            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            addCompanieAerienneListToModel(model);
            model.addAttribute("pageTitle", (avionDTO.getId() == null ? "Ajouter" : "Modifier") + " un Avion");
            return "admin/add-edit-avion";
        }
        if (avionDTO.getId() == null) {
            avionService.createDTO(avionDTO);
        } else {
            avionService.updateDTO(avionDTO);
        }
        redirectAttributes.addFlashAttribute("successMessage", "Avion sauvegardé avec succès !");
        return "redirect:/admin/avion";
    }

    @GetMapping("/edit/{id}")
    public String editAvion(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            AvionDTO avionDTO = avionService.findDTOById(id);
            model.addAttribute("avionDTO", avionDTO);
            addCompanieAerienneListToModel(model);

            List<CapaciteDTO> capacites = capaciteService.findDTOsByAvionId(id);
            model.addAttribute("capacites", capacites);
            addClasseMapToModel(model);

            model.addAttribute("pageTitle", "Modifier un Avion");
            return "admin/add-edit-avion";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Avion non trouvé avec ID: " + id);
            return "redirect:/admin/avion";
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAvion(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try{
            avionService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage","Avion supprimé avec succès !");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression de l'avion: " + e.getMessage());
        }
        return "redirect:/admin/avion";
    }
}