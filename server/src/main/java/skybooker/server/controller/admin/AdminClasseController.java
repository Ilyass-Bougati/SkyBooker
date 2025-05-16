package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.entity.Classe;
import skybooker.server.service.ClasseService;

import java.util.List;

@Controller
@RequestMapping("/admin/classes")
public class AdminClasseController {
    private final ClasseService classeService;

    @Autowired
    public AdminClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    @GetMapping
    public String listClasses(Model model) {
        List<Classe> classes = classeService.findAll();
        model.addAttribute("classes", classes);
        model.addAttribute("pageTitle", "Gerer les Classes");
        return "admin/classes";
    }

    @GetMapping("/add")
    public String addClass(Model model) {
        model.addAttribute("classe", new Classe());
        model.addAttribute("pageTitle", "Ajouter un Classe");
        return "admin/add-edit-classe";
    }

    @PostMapping("/save")
    public String saveClass(@Valid @ModelAttribute("classe") Classe classe, BindingResult result,
                            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", (classe.getId() == 0 ? "Ajouter" : "Modifier") + " un Classe");
            return "admin/add-edit-classe";
        }
        classeService.create(classe);
        redirectAttributes.addFlashAttribute("successMessage", "Classe sauvegardé avec succès !");
        return "redirect:/admin/classes";
    }

    @GetMapping("/edit/{id}")
    public String editClasse(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Classe classe = classeService.findById(id);
        if (classe != null) {
            model.addAttribute("classe", classe);
            model.addAttribute("pageTitle", "Modifier un Classe");
            return "admin/add-edit-classe";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage","Classe not found with id " + id);
            return "redirect:/admin/classes";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteClasse(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try{
            classeService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Classe deleted successfully!");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage", "Error deleting classe: " + e.getMessage());
        }
        return "redirect:/admin/classes";
    }
}
