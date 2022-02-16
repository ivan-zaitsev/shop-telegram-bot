package ua.ivan909020.bot.repositories;

import ua.ivan909020.bot.models.entities.Client;

public interface ClientRepository {

    Client findByChatId(Long chatId);

    void save(Client client);

    void update(Client client);

}
