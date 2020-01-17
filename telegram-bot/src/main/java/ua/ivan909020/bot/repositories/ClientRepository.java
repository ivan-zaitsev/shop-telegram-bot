package ua.ivan909020.bot.repositories;

import ua.ivan909020.bot.domain.entities.Client;

import java.util.List;

public interface ClientRepository {

    String findActionByChatId(Long chatId);

    void setActionForChatId(Long chatId, String action);

    Client findByChatId(Long chatId);

    void save(Client client);

    void update(Client client);

    List<Client> findAll();

}
