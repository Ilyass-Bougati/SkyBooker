package skybooker.server.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestController {
    @GetMapping
    public String test(Principal principal) {
        return "name = " + principal.getName();
    }
}
