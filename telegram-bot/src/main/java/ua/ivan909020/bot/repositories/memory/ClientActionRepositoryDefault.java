package ua.ivan909020.bot.repositories.memory;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;

import ua.ivan909020.bot.models.domain.ClientAction;
import ua.ivan909020.bot.repositories.ClientActionRepository;

public class ClientActionRepositoryDefault implements ClientActionRepository {

    private static final Duration MAX_STORAGE_TIME = Duration.ofMinutes(5);

    private final Map<Long, ClientAction> clientsAction = new HashMap<>();

    @Override
    public ClientAction findActionByChatId(Long chatId) {
        ClientAction clientAction = clientsAction.get(chatId);

        if (clientAction != null && LocalDateTime.now().isAfter(clientAction.getCreatedTime().plus(MAX_STORAGE_TIME))) {
            clientAction = null;
        }

        return SerializationUtils.clone(clientAction);
    }

    @Override
    public void updateActionByChatId(Long chatId, ClientAction clientAction) {
        clientsAction.put(chatId, SerializationUtils.clone(clientAction));
    }

    @Override
    public void deleteActionByChatId(Long chatId) {
        clientsAction.remove(chatId);
    }

}
