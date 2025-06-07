package skybooker.server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import skybooker.server.DTO.ClasseDTO;
import skybooker.server.service.ClasseService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/classe")
public class ClasseController {

    private final ClasseService classeService;

    public ClasseController(ClasseService classeService) {
        this.classeService = classeService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ClasseDTO>> getClasses() {
        return ResponseEntity.ok(classeService.findAllDTO());
    }

}
