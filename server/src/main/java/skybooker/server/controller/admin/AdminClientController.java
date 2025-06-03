package skybooker.server.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import skybooker.server.DTO.ClientDTO;
import skybooker.server.DTO.ClasseDTO;
import skybooker.server.DTO.PassagerDTO;
import skybooker.server.DTO.ReservationDTO;
import skybooker.server.service.ClientService;
import skybooker.server.service.PassagerService;
import skybooker.server.service.ClasseService;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/client")
public class AdminClientController {

    private final ClientService clientService;
    private final PassagerService passagerService;
    private final ClasseService classeService;

    @Autowired
    public AdminClientController(ClientService clientService, PassagerService passagerService, ClasseService classeService) {
        this.clientService = clientService;
        this.passagerService = passagerService;
        this.classeService = classeService;
    }

    @GetMapping
    public String listClients(Model model) {
        List<ClientDTO> clients = clientService.findAllDTO();
        model.addAttribute("clients", clients);
        model.addAttribute("pageTitle", "Gérer les Clients");
        return "admin/client";
    }

    @GetMapping("/details/{id}")
    public String viewClientDetails(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            ClientDTO client = clientService.findClientDetailsDTO(id);
            model.addAttribute("client", client);

            Set<ReservationDTO> reservationDTOs = client.getReservations().stream()
                    .map(ReservationDTO::new)
                    .collect(Collectors.toSet());
            model.addAttribute("reservations", reservationDTOs);

            Set<Long> allPassagerIds = new HashSet<>();
            Set<Long> allClasseIds = new HashSet<>();
            for (ReservationDTO resDTO : reservationDTOs) {
                if (resDTO.getPassagers() != null) {
                    for (ReservationDTO.PassagerData passagerData : resDTO.getPassagers()) {
                        allPassagerIds.add(passagerData.getPassagerId());
                        allClasseIds.add(passagerData.getClassId());
                    }
                }
            }

            Map<Long, PassagerDTO> passagersMap = passagerService.findDTOsByIds(allPassagerIds).stream()
                    .collect(Collectors.toMap(PassagerDTO::getId, p -> p));

            Map<Long, ClasseDTO> classesMap = classeService.findDTOsByIds(allClasseIds).stream()
                    .collect(Collectors.toMap(ClasseDTO::getId, c -> c));

            model.addAttribute("passagersMap", passagersMap);
            model.addAttribute("classesMap", classesMap);

            model.addAttribute("pageTitle", "Détails Client: " + client.getEmail());
            return "admin/client-details";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Impossible de charger les détails du client: " + e.getMessage());
            return "redirect:/admin/client";
        }
    }

    @PostMapping("/reservation/cancel/{id}")
    public String cancelReservation(@PathVariable("id") Long id, @RequestParam("clientId") Long clientId, RedirectAttributes redirectAttributes) {
        try {
            clientService.cancelReservation(id);
            redirectAttributes.addFlashAttribute("successMessage", "Réservation annulée avec succès !");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur lors de l'annulation de la réservation: " + e.getMessage());
        }
        return "redirect:/admin/client/details/" + clientId;
    }

    @DeleteMapping("/delete/{id}")
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