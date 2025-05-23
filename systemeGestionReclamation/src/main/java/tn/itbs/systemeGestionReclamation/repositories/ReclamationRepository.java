package tn.itbs.systemeGestionReclamation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.itbs.systemeGestionReclamation.beans.Reclamation;

import java.util.List;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    List<Reclamation> findByClientId(Long clientId);
    List<Reclamation> findByAgentId(Long agentId);
    List<Reclamation> findByAgent_Id(Long agentId);
}
