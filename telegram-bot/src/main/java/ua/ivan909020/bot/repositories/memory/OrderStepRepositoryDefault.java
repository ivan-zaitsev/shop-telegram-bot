package ua.ivan909020.bot.repositories.memory;

import ua.ivan909020.bot.commands.CommandSequence;
import ua.ivan909020.bot.models.entities.Order;
import ua.ivan909020.bot.repositories.OrderStepRepository;
import ua.ivan909020.bot.utils.CloneUtils;

import java.util.HashMap;
import java.util.Map;

public class OrderStepRepositoryDefault implements OrderStepRepository {

    private final Map<Long, CommandSequence<Long>> clientOrderSteps = new HashMap<>();

    private final Map<Long, Order> cachedOrders = new HashMap<>();

    @Override
    public CommandSequence<Long> findOrderStepByClientChatId(Long chatId) {
        return clientOrderSteps.get(chatId);
    }

    @Override
    public void updateOrderStepByChatId(Long chatId, CommandSequence<Long> orderStep) {
        clientOrderSteps.put(chatId, orderStep);
    }

    @Override
    public Order findCachedOrderByChatId(Long chatId) {
        return CloneUtils.cloneObject(cachedOrders.get(chatId));
    }

    @Override
    public void updateCachedOrder(Long chatId, Order order) {
        cachedOrders.put(chatId, CloneUtils.cloneObject(order));
    }

    @Override
    public void deleteCachedOrderByChatId(Long chatId) {
        clientOrderSteps.remove(chatId);
        cachedOrders.remove(chatId);
    }

}
