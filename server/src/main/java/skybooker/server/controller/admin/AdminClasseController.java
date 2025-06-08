package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.DTO.ClasseDTO;
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
        List<ClasseDTO> classes = classeService.findAllDTO();
        model.addAttribute("classes", classes);
        model.addAttribute("pageTitle", "Gerer les Classes");
        return "admin/classes";
    }

    @GetMapping("/add")
    public String addClass(Model model) {
        model.addAttribute("classe", new ClasseDTO());
        model.addAttribute("pageTitle", "Ajouter un Classe");
        return "admin/add-edit-classe";
    }

    @PostMapping("/save")
    public String saveClass(@Valid @ModelAttribute("classe") ClasseDTO classeDTO, BindingResult result,
                            Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", (classeDTO.getId() == null ? "Ajouter" : "Modifier") + " un Classe");
            return "admin/add-edit-classe";
        }

        try {
            if (classeDTO.getId() == null) {
                classeService.createDTO(classeDTO);
            } else {
                classeService.updateDTO(classeDTO);
            }
            redirectAttributes.addFlashAttribute("successMessage", "Classe sauvegardé avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error saving classe: " + e.getMessage());
            model.addAttribute("pageTitle", (classeDTO.getId() == null ? "Ajouter" : "Modifier") + " un Classe");
            return "admin/add-edit-classe";
        }
        return "redirect:/admin/classes";
    }

    @GetMapping("/edit/{id}")
    public String editClasse(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        ClasseDTO classeDTO = classeService.findDTOById(id);
        if (classeDTO != null) {
            model.addAttribute("classe", classeDTO);
            model.addAttribute("pageTitle", "Modifier un Classe");
            return "admin/add-edit-classe";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage","Classe not found with id " + id);
            return "redirect:/admin/classes";
        }
    }

    @DeleteMapping("/delete/{id}")
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