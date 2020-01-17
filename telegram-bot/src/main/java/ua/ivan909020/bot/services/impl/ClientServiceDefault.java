package ua.ivan909020.bot.services.impl;

import ua.ivan909020.bot.domain.entities.Client;
import ua.ivan909020.bot.repositories.ClientRepository;
import ua.ivan909020.bot.repositories.impl.ClientRepositoryDefault;
import ua.ivan909020.bot.services.ClientService;

import java.util.List;

public class ClientServiceDefault implements ClientService {

    private static final ClientService INSTANCE = new ClientServiceDefault();

    private ClientRepository repository = new ClientRepositoryDefault();

    private ClientServiceDefault() {
    }

    public static ClientService getInstance() {
        return INSTANCE;
    }

    @Override
    public String findActionByChatId(Long chatId) {
        if (chatId == null) {
            throw new IllegalArgumentException("ChatId should not be NULL");
        }
        return repository.findActionByChatId(chatId);
    }

    @Override
    public void setActionForChatId(Long chatId, String action) {
        if (chatId == null) {
            throw new IllegalArgumentException("ChatId should not be NULL");
        }
        repository.setActionForChatId(chatId, action);
    }

    @Override
    public Client findByChatId(Long chatId) {
        if (chatId == null) {
            throw new IllegalArgumentException("ChatId of Client should not be NULL");
        }
        return repository.findByChatId(chatId);
    }

    @Override
    public void save(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client should not be NULL");
        }
        Client receivedClient = findByChatId(client.getChatId());
        if (receivedClient == null) {
            repository.save(client);
        }
    }

    @Override
    public void update(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client should not be NULL");
        }
        repository.update(client);
    }

    @Override
    public List<Client> findAll() {
        return repository.findAll();
    }

}
