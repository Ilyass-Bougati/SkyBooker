package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.entity.Categorie;
import skybooker.server.service.CategorieService;

import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategorieController {

    private final CategorieService categorieService;

    @Autowired
    public AdminCategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping
    public String listCategories(Model model) {
        List<Categorie> categories = categorieService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("pageTitle","Manage Passenger Categories");
        return "admin/categories";
    }

    @GetMapping("/add")
    public String AddCategory(Model model) {
        model.addAttribute("categorie", new Categorie());
        model.addAttribute("pageTitle","Add New Passenger Category");
        return "admin/add-edit-categorie";
    }

    @PostMapping("/save")
    public String saveCategorie(@Valid @ModelAttribute("categorie") Categorie categorie, BindingResult result,
                                Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("pageTitle", (categorie.getId() == 0 ? "Add" : "Edit") + " Passenger Categorie");
            return "admin/add-edit-categorie";
        }

        categorieService.create(categorie);
        redirectAttributes.addFlashAttribute("successMessage", "Category saved successfully!");
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategorie(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Categorie categorie = categorieService.findById(id);
        if (categorie != null) {
            model.addAttribute("categorie", categorie);
            model.addAttribute("pageTitle", "Edit Passenger Category");
            return "admin/add-edit-categorie";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Category not found with ID: " + id);
            return "redirect:/admin/categories";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try{
            categorieService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Category deleted successfully!");
        } catch (Exception e){
            redirectAttributes.addFlashAttribute("errorMessage","Error deleting category: " + e.getMessage());
        }
        return "redirect:/admin/categories";
    }
}
