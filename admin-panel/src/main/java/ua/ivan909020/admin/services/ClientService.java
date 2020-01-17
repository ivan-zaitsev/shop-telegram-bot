package ua.ivan909020.admin.services;

import ua.ivan909020.admin.domain.Client;

public interface ClientService extends CrudService<Client> {

    Client update(Client client);

}
