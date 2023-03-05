package ua.ivan909020.admin.services;

import java.util.List;

import ua.ivan909020.admin.models.entities.Client;

public interface ClientService {

    Client findById(Integer id);

    List<Client> findAll();

    Client update(Client client);

    List<Client> findAllByActive(boolean active);

}
