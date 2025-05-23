package tn.itbs.systemeGestionReclamation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.itbs.systemeGestionReclamation.beans.SuiviReclamation;
import tn.itbs.systemeGestionReclamation.services.SuiviReclamationService;

import java.util.List;

@RestController
@RequestMapping("/suivi-reclamations")
@CrossOrigin
public class SuiviReclamationController {

    @Autowired
    private SuiviReclamationService suiviReclamationService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<SuiviReclamation> getAllSuivis() {
        return suiviReclamationService.getAllSuivis();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public SuiviReclamation getSuiviById(@PathVariable Long id) {
        return suiviReclamationService.getSuiviById(id).orElse(null);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public SuiviReclamation createSuivi(@RequestBody SuiviReclamation suivi) {
        return suiviReclamationService.createSuivi(suivi);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public SuiviReclamation updateSuivi(@PathVariable Long id, @RequestBody SuiviReclamation updatedSuivi) {
        return suiviReclamationService.updateSuivi(id, updatedSuivi);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteSuivi(@PathVariable Long id) {
        suiviReclamationService.deleteSuivi(id);
    }

    @GetMapping("/agent")
    public List<SuiviReclamation> getSuivisForLoggedInAgent() {
        return suiviReclamationService.getSuivisForLoggedInAgent();
    }
}
