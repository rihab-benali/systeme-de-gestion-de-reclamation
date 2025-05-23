package tn.itbs.systemeGestionReclamation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.itbs.systemeGestionReclamation.beans.AgentSAV;

import java.util.Optional;

public interface AgentSAVRepository extends JpaRepository<AgentSAV, Long> {
    Optional<AgentSAV> findByUser_Username(String username);
}
