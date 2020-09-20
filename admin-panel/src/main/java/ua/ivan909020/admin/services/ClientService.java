package ua.ivan909020.admin.services;

import ua.ivan909020.admin.domain.Client;

import java.util.List;

public interface ClientService extends CrudService<Client> {

    Client update(Client client);

    List<Client> findAllByActive(boolean active);

}
