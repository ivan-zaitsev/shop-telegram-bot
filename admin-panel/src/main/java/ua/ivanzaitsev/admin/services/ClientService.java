package ua.ivanzaitsev.admin.services;

import java.util.List;

import ua.ivanzaitsev.admin.models.entities.Client;

public interface ClientService {

    Client findById(Integer id);

    List<Client> findAll();

    Client update(Client client);

    List<Client> findAllByActive(boolean active);

}
