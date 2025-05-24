package tn.itbs.systemeGestionReclamation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tn.itbs.systemeGestionReclamation.beans.AgentSAV;
import tn.itbs.systemeGestionReclamation.beans.Reclamation;
import tn.itbs.systemeGestionReclamation.beans.SuiviReclamation;
import tn.itbs.systemeGestionReclamation.beans.User;
import tn.itbs.systemeGestionReclamation.repositories.AgentSAVRepository;
import tn.itbs.systemeGestionReclamation.repositories.ReclamationRepository;
import tn.itbs.systemeGestionReclamation.repositories.SuiviReclamationRepository;
import tn.itbs.systemeGestionReclamation.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReclamationService {

    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SuiviReclamationRepository suivieRepository;
    @Autowired
    private AgentSAVRepository agentSAVRepository;

    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    public Optional<Reclamation> getReclamationById(Long id) {
        return reclamationRepository.findById(id);
    }
//added
    public List<Reclamation> getClientReclamations() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User client = userRepository.findByUsername(username).orElseThrow();
        return reclamationRepository.findByClientId(client.getId());
    }
//added
    public void deleteReclamation(Long reclamationId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User client = userRepository.findByUsername(username).orElseThrow();

        Reclamation reclamation = reclamationRepository.findById(reclamationId).orElseThrow();
        if (!reclamation.getClient().getId().equals(client.getId())) {
            throw new AccessDeniedException("You can only delete your own reclamations.");
        }

        reclamationRepository.delete(reclamation);
    }

// added
    public Reclamation createReclamation(Reclamation reclamation) {
        return reclamationRepository.save(reclamation);
    }
    public List<Reclamation> getAssignedReclamations() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User agent = userRepository.findByUsername(username).orElseThrow();
        return reclamationRepository.findByAgentId(agent.getId());
    }
//added
    public Reclamation updateReclamationStatus(Long reclamationId, String newStatus) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User agent = userRepository.findByUsername(username).orElseThrow();

        Reclamation reclamation = reclamationRepository.findById(reclamationId).orElseThrow();

        if (!reclamation.getAgent().getId().equals(agent.getId())) {
            throw new AccessDeniedException("You can only edit reclamations assigned to you.");
        }

        reclamation.setStatut(newStatus);
        Reclamation updated = reclamationRepository.save(reclamation);

        // Auto-create a suivi
        SuiviReclamation suivie = new SuiviReclamation();
        suivie.setReclamation(updated);
        suivie.setAction("Status updated to: " + newStatus);
        AgentSAV agentSAV = agentSAVRepository.findByUser_Username(username).orElseThrow();
        suivie.setEmploye(agentSAV);

        suivieRepository.save(suivie);

        return updated;
    }


    public Reclamation updateReclamation(Long id, Reclamation updatedReclamation) {
        return reclamationRepository.findById(id).map(reclamation -> {
            reclamation.setClient(updatedReclamation.getClient());
            reclamation.setProduit(updatedReclamation.getProduit());
            reclamation.setStatut(updatedReclamation.getStatut());
            reclamation.setDescription(updatedReclamation.getDescription());
            reclamation.setDate(updatedReclamation.getDate());
            reclamation.setNote(updatedReclamation.getNote());
            return reclamationRepository.save(reclamation);
        }).orElse(null);
    }
//updated
    public void DDeleteReclamation(Long id) {
        reclamationRepository.deleteById(id);
    }
}
