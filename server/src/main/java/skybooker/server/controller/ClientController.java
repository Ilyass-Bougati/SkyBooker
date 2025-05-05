package skybooker.server.controller;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import skybooker.server.entity.Client;
import skybooker.server.repository.ClientRepository;
import skybooker.server.repository.PassagerRepository;
import skybooker.server.service.ClientService;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    public Client client(@PathVariable long id) {
        return clientService.findById(id);
    }

    @PostMapping("/")
    public Client saveClient(@RequestBody Client client) {
        return clientService.create(client);
    }

    @PutMapping("/")
    public Client updateClient(@RequestBody Client client) {
        return clientService.update(client);
    }

    @DeleteMapping("/{id}")
    public String deleteClient(@PathVariable long id) {
        clientService.deleteById(id);
        return "Client deleted";
    }
}
