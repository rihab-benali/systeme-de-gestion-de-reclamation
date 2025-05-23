package tn.itbs.systemeGestionReclamation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.itbs.systemeGestionReclamation.beans.Client;
import tn.itbs.systemeGestionReclamation.services.ClientService;

import java.util.List;

@RestController
@RequestMapping("/admin/clients")
@CrossOrigin
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id) {
        return clientService.getClientById(id).orElse(null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.createClient(client);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client updatedClient) {
        return clientService.updateClient(id, updatedClient);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
    }
}


