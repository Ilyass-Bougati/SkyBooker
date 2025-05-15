package skybooker.server.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/admin")
public class AdminDashboardController {

    @GetMapping("/dashboard")
    public String AdminDashboard(Model model) {
        model.addAttribute("pagetitle", "Admin Dashboard");
        return "admin/dashboard";
    }

    @GetMapping("/login")
    public String AdminLoginPage() {
        return "admin/login";
    }
}
