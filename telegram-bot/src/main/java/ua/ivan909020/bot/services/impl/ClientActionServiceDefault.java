package ua.ivan909020.bot.services.impl;

import ua.ivan909020.bot.models.domain.ClientAction;
import ua.ivan909020.bot.repositories.ClientActionRepository;
import ua.ivan909020.bot.repositories.memory.ClientActionRepositoryDefault;
import ua.ivan909020.bot.services.ClientActionService;

public class ClientActionServiceDefault implements ClientActionService {

    private static final ClientActionService INSTANCE = new ClientActionServiceDefault();

    private final ClientActionRepository repository = new ClientActionRepositoryDefault();

    private ClientActionServiceDefault() {
    }

    public static ClientActionService getInstance() {
        return INSTANCE;
    }

    @Override
    public ClientAction findActionByChatId(Long chatId) {
        if (chatId == null) {
            throw new IllegalArgumentException("ChatId should not be NULL");
        }

        return repository.findActionByChatId(chatId);
    }

    @Override
    public void updateActionByChatId(Long chatId, ClientAction clientAction) {
        if (chatId == null) {
            throw new IllegalArgumentException("ChatId should not be NULL");
        }
        if (clientAction == null) {
            throw new IllegalArgumentException("ClientAction should not be NULL");
        }
        if (clientAction.getAction() == null) {
            throw new IllegalArgumentException("Action of ClientAction should not be NULL");
        }

        repository.updateActionByChatId(chatId, clientAction);
    }

    @Override
    public void deleteActionByChatId(Long chatId) {
        if (chatId == null) {
            throw new IllegalArgumentException("ChatId should not be NULL");
        }

        repository.deleteActionByChatId(chatId);
    }

}
