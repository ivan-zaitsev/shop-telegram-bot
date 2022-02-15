package ua.ivan909020.bot.repositories.memory;

import ua.ivan909020.bot.models.domain.ClientAction;
import ua.ivan909020.bot.repositories.ClientActionRepository;
import ua.ivan909020.bot.utils.CloneUtils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ClientActionRepositoryDefault implements ClientActionRepository {

    private static final Duration MAX_STORAGE_TIME = Duration.ofMinutes(5);

    private final Map<Long, ClientAction> clientsAction = new HashMap<>();

    @Override
    public ClientAction findActionByChatId(Long chatId) {
        ClientAction clientAction = clientsAction.get(chatId);

        if (clientAction != null && LocalDateTime.now().isAfter(clientAction.getCreatedTime().plus(MAX_STORAGE_TIME))) {
            clientAction = null;
        }

        return CloneUtils.cloneObject(clientAction);
    }

    @Override
    public void updateActionByChatId(Long chatId, ClientAction clientAction) {
        clientsAction.put(chatId, CloneUtils.cloneObject(clientAction));
    }

    @Override
    public void deleteActionByChatId(Long chatId) {
        clientsAction.remove(chatId);
    }

}
