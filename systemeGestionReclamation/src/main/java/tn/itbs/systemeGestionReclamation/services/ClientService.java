package tn.itbs.systemeGestionReclamation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.itbs.systemeGestionReclamation.beans.Client;
import tn.itbs.systemeGestionReclamation.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public Client updateClient(Long id, Client updatedClient) {
        return clientRepository.findById(id).map(client -> {
            client.setNom(updatedClient.getNom());
            client.setEmail(updatedClient.getEmail());
            client.setTelephone(updatedClient.getTelephone());
            return clientRepository.save(client);
        }).orElse(null);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
