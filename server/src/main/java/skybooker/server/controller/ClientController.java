package skybooker.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import skybooker.server.entity.Client;
import skybooker.server.service.ClientService;

@RestController
@RequestMapping("/api/v1")
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
