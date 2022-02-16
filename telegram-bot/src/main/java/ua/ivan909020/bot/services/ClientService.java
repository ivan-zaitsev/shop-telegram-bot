package ua.ivan909020.bot.services;

import ua.ivan909020.bot.models.entities.Client;

public interface ClientService {

    Client findByChatId(Long chatId);

    void save(Client client);

    void update(Client client);

}
