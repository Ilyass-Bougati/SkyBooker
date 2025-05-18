package skybooker.server.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/admin/login")
    public String loginPage(@RequestParam(value = "error", required = false) boolean error,
                            @RequestParam(value = "logout", required = false) boolean logout,
                            Model model) {
        return "admin/login";
    }
}