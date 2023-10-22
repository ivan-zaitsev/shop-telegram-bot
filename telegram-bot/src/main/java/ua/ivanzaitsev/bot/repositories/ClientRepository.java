package ua.ivanzaitsev.bot.repositories;

import ua.ivanzaitsev.bot.models.entities.Client;

public interface ClientRepository {

    Client findByChatId(Long chatId);

    void save(Client client);

    void update(Client client);

}
