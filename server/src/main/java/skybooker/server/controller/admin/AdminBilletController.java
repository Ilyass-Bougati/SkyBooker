package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.entity.Billet;
import skybooker.server.service.BilletService;
import skybooker.server.service.ClasseService;
import skybooker.server.service.PassagerService;
import skybooker.server.service.ReservationService;

import java.util.List;

@Controller
@RequestMapping("/admin/billet")
public class AdminBilletController {

    private final BilletService billetService;
    private final ClasseService classeService;
    private final PassagerService passagerService;
    private final ReservationService reservationService;

    @Autowired
    public AdminBilletController(BilletService billetService, ClasseService classeService, PassagerService passagerService, ReservationService reservationService) {
        this.billetService = billetService;
        this.classeService = classeService;
        this.passagerService = passagerService;
        this.reservationService = reservationService;
    }

    @GetMapping
    public String listBillets(Model model) {
        List<Billet> billets = billetService.findAll();
        model.addAttribute("billets", billets);
        model.addAttribute("pageTitle", "Gérer les Billets");
        return "admin/billet";
    }

    @GetMapping("/add")
    public String addBillet(Model model) {
        model.addAttribute("billet", new Billet());
        model.addAttribute("classes", classeService.findAll());
        model.addAttribute("passagers", passagerService.findAll());
        model.addAttribute("reservations", reservationService.findAll());
        model.addAttribute("pageTitle", "Ajouter un Billet");
        return "admin/add-edit-billet";
    }

    @PostMapping("/save")
    public String saveBillet(@Valid @ModelAttribute("billet") Billet billet, BindingResult result,
                             Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("classes", classeService.findAll());
            model.addAttribute("passagers", passagerService.findAll());
            model.addAttribute("reservations", reservationService.findAll());
            model.addAttribute("pageTitle", (billet.getId() == 0 ? "Ajouter" : "Modifier") + " un Billet");
            return "admin/add-edit-billet";
        }
        billetService.create(billet);
        redirectAttributes.addFlashAttribute("successMessage", "Billet sauvegardé avec succès !");
        return "redirect:/admin/billet";
    }

    @GetMapping("/edit/{id}")
    public String editBillet(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Billet billet = billetService.findById(id);
        if (billet != null) {
            model.addAttribute("billet", billet);
            model.addAttribute("classes", classeService.findAll());
            model.addAttribute("passagers", passagerService.findAll());
            model.addAttribute("reservations", reservationService.findAll());
            model.addAttribute("pageTitle", "Modifier un Billet");
            return "admin/add-edit-billet";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Billet n'existe pas ! :" + id);
            return "redirect:/admin/billet";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteBillet(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            billetService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Billet supprimé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression du Billet :" + e.getMessage());
        }
        return "redirect:/admin/billet";
    }
}