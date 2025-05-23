package tn.itbs.systemeGestionReclamation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tn.itbs.systemeGestionReclamation.beans.AgentSAV;
import tn.itbs.systemeGestionReclamation.beans.SuiviReclamation;
import tn.itbs.systemeGestionReclamation.repositories.SuiviReclamationRepository;
import tn.itbs.systemeGestionReclamation.repositories.AgentSAVRepository;
import tn.itbs.systemeGestionReclamation.util.AuthUtils;

import java.util.List;
import java.util.Optional;

@Service
public class SuiviReclamationService {
    String currentUsername = AuthUtils.getCurrentUsername();

    @Autowired
    private AgentSAVRepository agentSAVRepository;  // note: instance variable, lowercase 'a'


    @Autowired
    private SuiviReclamationRepository suiviReclamationRepository;

    public List<SuiviReclamation> getAllSuivis() {
        return suiviReclamationRepository.findAll();
    }

    public Optional<SuiviReclamation> getSuiviById(Long id) {
        return suiviReclamationRepository.findById(id);
    }

    public SuiviReclamation createSuivi(SuiviReclamation suivi) {
        return suiviReclamationRepository.save(suivi);
    }

    public SuiviReclamation updateSuivi(Long id, SuiviReclamation updatedSuivi) {
        return suiviReclamationRepository.findById(id).map(suivi -> {
            suivi.setMessage(updatedSuivi.getMessage());
            suivi.setReclamation(updatedSuivi.getReclamation());
            suivi.setEmploye(updatedSuivi.getEmploye());
            suivi.setAction(updatedSuivi.getAction());
            suivi.setDate(updatedSuivi.getDate());
            return suiviReclamationRepository.save(suivi);
        }).orElse(null);
    }
//added
    public List<SuiviReclamation> getSuivisForLoggedInAgent() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AgentSAV agent = agentSAVRepository.findByUser_Username(username)
                .orElseThrow(() -> new RuntimeException("Agent not found"));

        return suiviReclamationRepository.findByEmploye_Id(agent.getId());
    }


    public void deleteSuivi(Long id) {
        suiviReclamationRepository.deleteById(id);
    }
}
