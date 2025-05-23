package tn.itbs.systemeGestionReclamation.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.itbs.systemeGestionReclamation.beans.Client;

public interface ClientRepository  extends JpaRepository<Client, Long> {

}
