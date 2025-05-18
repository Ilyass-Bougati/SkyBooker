package skybooker.server.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPanelController {

    @GetMapping
    public String showAdminPanel(Model model) {
        model.addAttribute("pageTitle", "Admin Panel");
        return "admin/admin-panel";
    }
}