package ua.ivan909020.bot.repositories.impl;

import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.commands.impl.*;
import ua.ivan909020.bot.domain.entities.Order;
import ua.ivan909020.bot.repositories.OrderStepRepository;
import ua.ivan909020.bot.utils.ClonerUtils;

import java.util.HashMap;
import java.util.Map;

public class OrderStepRepositoryDefault implements OrderStepRepository {

    private final Map<Long, Integer> orderStepNumbers = new HashMap<>();
    private final Map<Integer, Command<Long>> orderSteps = new HashMap<>();

    private final Map<Long, Order> cachedOrders = new HashMap<>();

    @Override
    public void setOrderStepNumber(Long chatId, Integer orderStepNumber) {
        orderStepNumbers.put(chatId, orderStepNumber);
    }

    @Override
    public Integer findOrderStepNumberByChatId(Long chatId) {
        return orderStepNumbers.getOrDefault(chatId, 0);
    }

    @Override
    public Map<Integer, Command<Long>> getOrderSteps() {
        if (orderSteps.isEmpty()) {
            orderSteps.put(1, OrderProcessCommand.getInstance());
            orderSteps.put(2, OrderEnterNameCommand.getInstance());
            orderSteps.put(3, OrderEnterPhoneNumberCommand.getInstance());
            orderSteps.put(4, OrderEnterCityCommand.getInstance());
            orderSteps.put(5, OrderEnterAddressCommand.getInstance());
            orderSteps.put(6, OrderCreateCommand.getInstance());
        }
        return orderSteps;
    }

    @Override
    public Order findCachedOrderByChatId(Long chatId) {
        return ClonerUtils.cloneObject(cachedOrders.get(chatId));
    }

    @Override
    public void saveCachedOrder(Long chatId, Order order) {
        cachedOrders.put(chatId, ClonerUtils.cloneObject(order));
    }

    @Override
    public void updateCachedOrder(Long chatId, Order order) {
        cachedOrders.put(chatId, ClonerUtils.cloneObject(order));
    }

    @Override
    public void deleteCachedOrderByChatId(Long chatId) {
        cachedOrders.remove(chatId);
    }

}
