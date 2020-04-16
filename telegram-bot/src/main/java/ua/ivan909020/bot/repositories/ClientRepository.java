package ua.ivan909020.bot.repositories;

import ua.ivan909020.bot.domain.entities.Client;

public interface ClientRepository {

    String findActionByChatId(Long chatId);

    void setActionForChatId(Long chatId, String action);

    Client findByChatId(Long chatId);

    void save(Client client);

    void update(Client client);

}
