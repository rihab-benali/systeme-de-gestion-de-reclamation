package tn.itbs.systemeGestionReclamation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.itbs.systemeGestionReclamation.beans.AgentSAV;
import tn.itbs.systemeGestionReclamation.beans.SuiviReclamation;

import java.util.List;
import java.util.Optional;

public interface SuiviReclamationRepository extends JpaRepository<SuiviReclamation, Long> {
    List<SuiviReclamation> findByEmploye_Id(Long employeId);

}
