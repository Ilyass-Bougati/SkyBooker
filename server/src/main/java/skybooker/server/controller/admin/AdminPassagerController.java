package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.entity.Client;
import skybooker.server.entity.Passager;
import skybooker.server.service.CategorieService;
import skybooker.server.service.ClientService;
import skybooker.server.service.PassagerService;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Controller
@RequestMapping("/admin/passager")
public class AdminPassagerController {

    private final PassagerService passagerService;
    private final CategorieService categorieService;
    private final ClientService clientService;

    @Autowired
    public AdminPassagerController(PassagerService passagerService, CategorieService categorieService, ClientService clientService) {
        this.passagerService = passagerService;
        this.categorieService = categorieService;
        this.clientService = clientService;
    }

    @GetMapping
    public String listPassagers(Model model) {
        List<Passager> passagers = passagerService.findAll();
        model.addAttribute("passagers", passagers);
        model.addAttribute("pageTitle", "Gérer les Passagers");
        return "admin/passager";
    }

    @GetMapping("/add")
    public String addPassager(Model model, @RequestParam(value = "clientId", required = false) Long clientId) {
        Passager passager = new Passager();
        if (clientId != null) {
            Client client = clientService.findById(clientId);
            if (client != null) {
                passager.setClient(client);
            }
        }
        model.addAttribute("passager", passager);
        model.addAttribute("categories", categorieService.findAll());
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("pageTitle", "Ajouter un Passager");
        return "admin/add-edit-passager";
    }

    @PostMapping("/save")
    public String savePassager(@Valid @ModelAttribute("passager") Passager passager, BindingResult result,
                               Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categorieService.findAll());
            model.addAttribute("clients", clientService.findAll());
            model.addAttribute("pageTitle", (passager.getId() == null ? "Ajouter" : "Modifier") + " un Passager");
            return "admin/add-edit-passager";
        }

        if (passager.getDateOfBirth() != null) {
            passager.setAge(Period.between(passager.getDateOfBirth(), LocalDate.now()).getYears());
        }

        boolean isNew = passager.getId() == null;
        if (isNew) {
            passagerService.create(passager);
            redirectAttributes.addFlashAttribute("successMessage", "Passager ajouté avec succès !");
        } else {
            passagerService.update(passager);
            redirectAttributes.addFlashAttribute("successMessage", "Passager modifié avec succès !");
        }

        return "redirect:/admin/passager";
    }

    @GetMapping("/edit/{id}")
    public String editPassager(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Passager passager = passagerService.findById(id);
        if (passager != null) {
            model.addAttribute("passager", passager);
            model.addAttribute("categories", categorieService.findAll());
            model.addAttribute("clients", clientService.findAll());
            model.addAttribute("pageTitle", "Modifier un Passager");
            return "admin/add-edit-passager";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Passager n'existe pas ! :" + id);
            return "redirect:/admin/passager";
        }
    }

    @GetMapping("/delete/{id}")
    public String deletePassager(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            passagerService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Passager supprimé avec succès !");
        } catch (Exception e) {
            System.err.println("Error deleting passager: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression du Passager : " + e.getMessage());
        }
        return "redirect:/admin/passager";
    }
}