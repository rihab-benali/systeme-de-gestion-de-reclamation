package tn.itbs.systemeGestionReclamation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.itbs.systemeGestionReclamation.beans.Reclamation;
import tn.itbs.systemeGestionReclamation.services.ReclamationService;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("/reclamations")
@CrossOrigin
public class ReclamationController {

    @Autowired
    private ReclamationService reclamationService;


    @GetMapping
    public List<Reclamation> getAllReclamations() {
        return reclamationService.getAllReclamations();
    }
    // 👤 [Client] Create a new Reclamation
    @PostMapping
    public Reclamation createClientReclamation(@RequestBody Reclamation reclamation) {
        return reclamationService.createReclamation(reclamation);
    }

    // 👤 [Client] Get all reclamations created by the logged-in client
    @GetMapping("/my")
    public List<Reclamation> getClientReclamations() {
        return reclamationService.getClientReclamations();
    }

    // 👤 [Client] Delete a reclamation by ID (only if it belongs to them)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReclamation(@PathVariable Long id) {
        reclamationService.deleteReclamation(id);
        return ResponseEntity.ok().build();
    }

    // 🧑‍💼 [Agent] Get all reclamations assigned to the logged-in agent
    @GetMapping("/assigned")
    public List<Reclamation> getAssignedReclamations() {
        return reclamationService.getAssignedReclamations();
    }

    // 🧑‍💼 [Agent] Update status of an assigned reclamation
    @PutMapping("/{id}/status")
    public Reclamation updateReclamationStatus(@PathVariable Long id, @RequestParam String status) {
        return reclamationService.updateReclamationStatus(id, status);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Reclamation getReclamationById(@PathVariable Long id) {
        return reclamationService.getReclamationById(id).orElse(null);
    }


    @PutMapping("/{id}")
    public Reclamation updateReclamation(@PathVariable Long id, @RequestBody Reclamation updatedReclamation) {
        return reclamationService.updateReclamation(id, updatedReclamation);
    }

    @DeleteMapping("my/{id}")
    public ResponseEntity<?> DDeleteReclamation(@PathVariable Long id) {
        reclamationService.deleteReclamation(id);
        return ResponseEntity.ok().build();
    }

}
