package ua.ivanzaitsev.bot.repositories.memory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.SerializationUtils;

import ua.ivanzaitsev.bot.models.domain.ClientAction;
import ua.ivanzaitsev.bot.repositories.ClientActionRepository;

public class ClientActionRepositoryDefault implements ClientActionRepository {

    private final Map<Long, ClientAction> clientsAction = new ConcurrentHashMap<>();

    @Override
    public ClientAction findByChatId(Long chatId) {
        ClientAction clientAction = clientsAction.get(chatId);
        return SerializationUtils.clone(clientAction);
    }

    @Override
    public void updateByChatId(Long chatId, ClientAction clientAction) {
        clientsAction.put(chatId, SerializationUtils.clone(clientAction));
    }

    @Override
    public void deleteByChatId(Long chatId) {
        clientsAction.remove(chatId);
    }

}
