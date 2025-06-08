package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.DTO.CompanieAerienneDTO;
import skybooker.server.service.CompanieAerienneService;

import java.util.List;

@Controller
@RequestMapping("/admin/companie_aerienne")
public class AdminCompanieAerienneController {
    private final CompanieAerienneService companieAerienneService;

    @Autowired
    public AdminCompanieAerienneController(CompanieAerienneService companieAerienneService) {
        this.companieAerienneService = companieAerienneService;
    }

    @GetMapping
    public String listCompanieAeriennes(Model model) {
        List<CompanieAerienneDTO> companieAerienne = companieAerienneService.findAllDTO();
        model.addAttribute("companiesAerienne", companieAerienne);
        model.addAttribute("pageTitle", "Gerer les Companie Aerienne");
        return "admin/companie_aerienne";
    }

    @GetMapping("/add")
    public String addCompanieAerienne(Model model) {
        model.addAttribute("companieAerienne", new CompanieAerienneDTO());
        model.addAttribute("pageTitle", "Ajouter une Companie Aerienne");
        return "admin/add-edit-companie_aerienne";
    }

    @PostMapping("/save")
    public String saveCompanieAerienne(@Valid @ModelAttribute("companieAerienne") CompanieAerienneDTO companieAerienneDTO, BindingResult result,
                                       Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", (companieAerienneDTO.getId() == null ? "Ajouter" : "Modifier") +" une Companie Aerienne");
            return "admin/add-edit-companie_aerienne";
        }

        try {
            if (companieAerienneDTO.getId() == null) {
                companieAerienneService.createDTO(companieAerienneDTO);
            } else {
                companieAerienneService.updateDTO(companieAerienneDTO);
            }
            redirectAttributes.addFlashAttribute("successMessage", "Companie Aerienne sauvegardé avec succès !");
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            result.reject("global.error", "A company with similar details might already exist (name, IATA, or ICAO code).");
            model.addAttribute("pageTitle", (companieAerienneDTO.getId() == null ? "Ajouter" : "Modifier") +" une Companie Aerienne");
            return "admin/add-edit-companie_aerienne";
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving Companie Aerienne :" + e.getMessage());
            model.addAttribute("pageTitle", (companieAerienneDTO.getId() == null ? "Ajouter" : "Modifier") +" une Companie Aerienne");
            return "admin/add-edit-companie_aerienne";
        }
        return "redirect:/admin/companie_aerienne";
    }

    @GetMapping("/edit/{id}")
    public String editCompanieAerienne(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        CompanieAerienneDTO companieAerienneDTO = companieAerienneService.findDTOById(id);
        if (companieAerienneDTO != null) {
            model.addAttribute("companieAerienne", companieAerienneDTO);
            model.addAttribute("pageTitle", "Modifier une Companie Aerienne");
            return "admin/add-edit-companie_aerienne";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Companie Aerienne n'existe ! :" + id);
            return "redirect:/admin/companie_aerienne";
        }
    }

    @DeleteMapping("/delete/{id}")
    public String deleteCompanieAerienne(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try{
            companieAerienneService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Companie Aerienne deleted successfully!");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting Companie Aerienne :" + e.getMessage());
        }
        return "redirect:/admin/companie_aerienne";
    }
}