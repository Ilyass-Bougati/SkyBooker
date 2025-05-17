package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.entity.CompanieAerienne;
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
        List<CompanieAerienne> companieAerienne = companieAerienneService.findAll();
        model.addAttribute("companiesAerienne", companieAerienne);
        model.addAttribute("pageTitle", "Gerer les Companie Aerienne");
        return "admin/companie_aerienne";
    }

    @GetMapping("/add")
    public String addCompanieAerienne(Model model) {
        model.addAttribute("companieAerienne", new CompanieAerienne());
        model.addAttribute("pageTitle", "Ajouter une Companie Aerienne");
        return "admin/add-edit-companie_aerienne";
    }

    @PostMapping("/save")
    public String saveCompanieAerienne(@Valid @ModelAttribute("companieAerienne") CompanieAerienne companieAerienne, BindingResult result,
                                       Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", (companieAerienne.getId() == 0 ? "Ajouter" : "Modifier") +" une Companie Aerienne");
            return "admin/add-edit-companie_aerienne";
        }
        companieAerienneService.create(companieAerienne);
        redirectAttributes.addFlashAttribute("successMessage", "Companie Aerienne sauvegardé avec succès !");
        return "redirect:/admin/companie_aerienne";
    }

    @GetMapping("/edit/{id}")
    public String editCompanieAerienne(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        CompanieAerienne companieAerienne = companieAerienneService.findById(id);
        if (companieAerienne != null) {
            model.addAttribute("companieAerienne", companieAerienne);
            model.addAttribute("pageTitle", "Modifier une Companie Aerienne");
            return "admin/add-edit-companie_aerienne";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Companie Aerienne n'existe ! :" + id);
            return "redirect:/admin/companie_aerienne";
        }
    }

    @GetMapping("/delete/{id}")
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
