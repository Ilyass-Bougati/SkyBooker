package skybooker.server.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.entity.Client;
import skybooker.server.service.ClientService;
import skybooker.server.service.RoleService;

import java.util.List;

@Controller
@RequestMapping("/admin/client")
public class AdminClientController {

    private final ClientService clientService;
    private final RoleService roleService;

    @Autowired
    public AdminClientController(ClientService clientService, RoleService roleService) {
        this.clientService = clientService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listClients(Model model) {
        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        model.addAttribute("pageTitle", "Gérer les Clients");
        return "admin/client";
    }

    @GetMapping("/add")
    public String addClient(Model model) {
        model.addAttribute("client", new Client());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("pageTitle", "Ajouter un Client");
        return "admin/add-edit-client";
    }

    @PostMapping("/save")
    public String saveClient(@Valid @ModelAttribute("client") Client client, BindingResult result,
                             Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("pageTitle", (client.getId() == 0 ? "Ajouter" : "Modifier") + " un Client");
            return "admin/add-edit-client";
        }
        clientService.create(client);
        redirectAttributes.addFlashAttribute("successMessage", "Client sauvegardé avec succès !");
        return "redirect:/admin/client";
    }

    @GetMapping("/edit/{id}")
    public String editClient(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Client client = clientService.findById(id);
        if (client != null) {
            model.addAttribute("client", client);
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("pageTitle", "Modifier un Client");
            return "admin/add-edit-client";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Client n'existe pas ! :" + id);
            return "redirect:/admin/client";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            clientService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Client supprimé avec succès !");
        } catch (Exception e) {
            System.err.println("Error deleting client: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de la suppression du Client : " + e.getMessage());
        }
        return "redirect:/admin/client";
    }
}