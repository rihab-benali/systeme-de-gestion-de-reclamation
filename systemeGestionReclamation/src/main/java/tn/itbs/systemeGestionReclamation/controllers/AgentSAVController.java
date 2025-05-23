package tn.itbs.systemeGestionReclamation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.itbs.systemeGestionReclamation.DTOs.AddAgentRequest;
import tn.itbs.systemeGestionReclamation.beans.AgentSAV;
import tn.itbs.systemeGestionReclamation.services.AgentSAVService;

import java.util.List;

@RestController
@RequestMapping("/admin/agentsav")
@CrossOrigin
public class AgentSAVController {

    @Autowired
    private AgentSAVService agentSAVService;
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<AgentSAV> getAllAgents() {
        return agentSAVService.getAllAgents();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public AgentSAV getAgentById(@PathVariable Long id) {
        return agentSAVService.getAgentById(id).orElse(null);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public AgentSAV createAgent(@RequestBody AddAgentRequest req) {
        return agentSAVService.createAgentWithUser(req);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public AgentSAV updateAgent(@PathVariable Long id, @RequestBody AgentSAV updatedAgent) {
        return agentSAVService.updateAgent(id, updatedAgent);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteAgent(@PathVariable Long id) {
        agentSAVService.deleteAgent(id);
    }
}
