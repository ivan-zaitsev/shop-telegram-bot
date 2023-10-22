package ua.ivanzaitsev.bot.repositories.memory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.SerializationUtils;

import ua.ivanzaitsev.bot.models.domain.ClientOrder;
import ua.ivanzaitsev.bot.repositories.ClientOrderStateRepository;

public class ClientOrderStateRepositoryDefault implements ClientOrderStateRepository {

    private final Map<Long, ClientOrder> clientOrders = new ConcurrentHashMap<>();

    @Override
    public ClientOrder findByChatId(Long chatId) {
        ClientOrder clientOrder = clientOrders.get(chatId);
        return SerializationUtils.clone(clientOrder);
    }

    @Override
    public void updateByChatId(Long chatId, ClientOrder userOrder) {
        clientOrders.put(chatId, SerializationUtils.clone(userOrder));
    }

    @Override
    public void deleteByChatId(Long chatId) {
        clientOrders.remove(chatId);
    }

}
