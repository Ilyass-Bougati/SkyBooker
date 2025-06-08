package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.DTO.AeroportDTO;
import skybooker.server.DTO.AvionDTO;
import skybooker.server.DTO.VolDTO;
import skybooker.server.enums.EtatVol;
import skybooker.server.service.VolService;
import skybooker.server.service.AeroportService;
import skybooker.server.service.AvionService;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Time;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        timeFormat.setLenient(false);
        binder.registerCustomEditor(Time.class, new CustomDateEditor(timeFormat, true));
    }


    private void addCommonDataToModel(Model model) {
        model.addAttribute("aeroports", aeroportService.findAllDTO());
        model.addAttribute("avions", avionService.findAllDTO());
        model.addAttribute("etatsVol", EtatVol.values());
    }

    private void addLookupMapsToModel(Model model) {
        Map<Long, AeroportDTO> aeroportsMap = aeroportService.findAllDTO().stream()
                .collect(Collectors.toMap(AeroportDTO::getId, a -> a));
        model.addAttribute("aeroportsMap", aeroportsMap);

        Map<Long, AvionDTO> avionsMap = avionService.findAllDTO().stream()
                .collect(Collectors.toMap(AvionDTO::getId, a -> a));
        model.addAttribute("avionsMap", avionsMap);
    }

    @GetMapping
    public String listVols(Model model) {
        List<VolDTO> volsDTO = volService.findAllDTO();
        model.addAttribute("vols", volsDTO);
        addLookupMapsToModel(model);
        model.addAttribute("pageTitle", "Gérer les Vols");
        return "admin/vol";
    }

    @GetMapping("/add")
    public String addVol(Model model) {
        model.addAttribute("volDTO", new VolDTO());
        addCommonDataToModel(model);
        model.addAttribute("pageTitle", "Ajouter un Vol");
        return "admin/add-edit-vol";
    }

    @PostMapping("/save")
    public String saveVol(@Valid @ModelAttribute("volDTO") VolDTO volDTO, BindingResult result,
                          Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            addCommonDataToModel(model);
            model.addAttribute("pageTitle", (volDTO.getId() == null ? "Ajouter" : "Modifier") + " un Vol");
            return "admin/add-edit-vol";
        }
        try {
            if (volDTO.getId() == null) {
                volService.createDTO(volDTO);
            } else {
                volService.updateDTO(volDTO);
            }
            redirectAttributes.addFlashAttribute("successMessage", "Vol sauvegardé avec succès !");
            return "redirect:/admin/vol";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la sauvegarde du Vol: " + e.getMessage());
            addCommonDataToModel(model);
            model.addAttribute("pageTitle", (volDTO.getId() == null ? "Ajouter" : "Modifier") + " un Vol");
            return "admin/add-edit-vol";
        }
    }

    @GetMapping("/edit/{id}")
    public String editVol(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            VolDTO volDTO = volService.findDTOById(id);
            model.addAttribute("volDTO", volDTO);
            addCommonDataToModel(model);
            model.addAttribute("pageTitle", "Modifier un Vol");
            return "admin/add-edit-vol";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vol non trouvé : " + e.getMessage());
            return "redirect:/admin/vol";
        }
    }

    @DeleteMapping("/delete/{id}")
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