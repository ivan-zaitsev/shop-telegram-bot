package ua.ivan909020.admin.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.ivan909020.admin.domain.Client;
import ua.ivan909020.admin.exceptions.ValidationException;
import ua.ivan909020.admin.repositories.ClientRepository;
import ua.ivan909020.admin.services.ClientService;

import java.util.List;

@Service
public class ClientServiceDefault implements ClientService {

    private final ClientRepository repository;

    @Autowired
    public ClientServiceDefault(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public Client findById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id of Client should not be NULL");
        }
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

    @Override
    public Client update(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client should not be NULL");
        }
        if (client.getId() == null) {
            throw new ValidationException("Id of Client should not be NULL");
        }
        if (client.getChatId() == null) {
            throw new ValidationException("ChatId if Client should not be NULL");
        }
        return repository.save(client);
    }

    @Override
    public List<Client> findAllByActive(boolean active) {
        return repository.findAllByActive(active);
    }

}
