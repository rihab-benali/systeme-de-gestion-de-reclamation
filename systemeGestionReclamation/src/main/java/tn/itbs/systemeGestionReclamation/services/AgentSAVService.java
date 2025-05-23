package tn.itbs.systemeGestionReclamation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.itbs.systemeGestionReclamation.DTOs.AddAgentRequest;
import tn.itbs.systemeGestionReclamation.beans.AgentSAV;
import tn.itbs.systemeGestionReclamation.beans.User;
import tn.itbs.systemeGestionReclamation.repositories.AgentSAVRepository;
import tn.itbs.systemeGestionReclamation.beans.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import tn.itbs.systemeGestionReclamation.repositories.UserRepository;


import java.util.List;
import java.util.Optional;

@Service
public class AgentSAVService {

    @Autowired
    private AgentSAVRepository agentSAVRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


    public List<AgentSAV> getAllAgents() {
        return agentSAVRepository.findAll();
    }

    public Optional<AgentSAV> getAgentById(Long id) {
        return agentSAVRepository.findById(id);
    }

    public AgentSAV createAgentWithUser(AddAgentRequest req) {
        User user = new User();
        user.setUsername(req.getUsername());
        user.setPassword(passwordEncoder.encode(req.getPassword())); // Use default or provided password
        user.setRole(Role.AGENT_SAV);

        AgentSAV agent = new AgentSAV();
        agent.setNom(req.getNom());
        agent.setCompetence(req.getCompetence());
        agent.setUser(user);

        user.setAgentSAV(agent);

        userRepository.save(user); // saves both user and agent due to cascade

        return agent;
    }

    public AgentSAV updateAgent(Long id, AgentSAV updatedAgent) {
        return agentSAVRepository.findById(id).map(agent -> {
            agent.setNom(updatedAgent.getNom());
            agent.setCompetence(updatedAgent.getCompetence());
            return agentSAVRepository.save(agent);
        }).orElse(null);
    }

    public void deleteAgent(Long id) {
        agentSAVRepository.deleteById(id);
    }
}

