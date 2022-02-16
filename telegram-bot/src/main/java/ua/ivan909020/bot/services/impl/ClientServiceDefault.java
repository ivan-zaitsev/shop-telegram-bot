package ua.ivan909020.bot.services.impl;

import ua.ivan909020.bot.models.entities.Client;
import ua.ivan909020.bot.exceptions.ValidationException;
import ua.ivan909020.bot.repositories.ClientRepository;
import ua.ivan909020.bot.repositories.database.ClientRepositoryDefault;
import ua.ivan909020.bot.services.ClientService;

public class ClientServiceDefault implements ClientService {

    private static final ClientService INSTANCE = new ClientServiceDefault();

    private final ClientRepository repository = new ClientRepositoryDefault();

    private ClientServiceDefault() {
    }

    public static ClientService getInstance() {
        return INSTANCE;
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
        if (client.getChatId() == null) {
            throw new ValidationException("ChatId of Client should not be NULL");
        }

        repository.save(client);
    }

    @Override
    public void update(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client should not be NULL");
        }

        repository.update(client);
    }

}
