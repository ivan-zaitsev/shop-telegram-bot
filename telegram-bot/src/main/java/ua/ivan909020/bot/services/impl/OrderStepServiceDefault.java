package ua.ivan909020.bot.services.impl;

import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.domain.entities.Order;
import ua.ivan909020.bot.exceptions.ValidationException;
import ua.ivan909020.bot.repositories.OrderStepRepository;
import ua.ivan909020.bot.repositories.impl.OrderStepRepositoryDefault;
import ua.ivan909020.bot.services.OrderStepService;

import java.util.Map;

public class OrderStepServiceDefault implements OrderStepService {

    private static final OrderStepService INSTANCE = new OrderStepServiceDefault();

    private final OrderStepRepository repository = new OrderStepRepositoryDefault();

    private OrderStepServiceDefault() {
    }

    public static OrderStepService getInstance() {
        return INSTANCE;
    }

    @Override
    public void revokeOrderStep(Long chatId) {
        repository.setOrderStepNumber(chatId, 0);
    }

    @Override
    public void previousOrderStep(Long chatId) {
        int orderStep = repository.findOrderStepNumberByChatId(chatId);
        Map<Integer, Command<Long>> orderSteps = repository.getOrderSteps();
        if (orderStep > 1) {
            orderStep--;
            repository.setOrderStepNumber(chatId, orderStep);
            orderSteps.get(orderStep).execute(chatId);
        }
    }

    @Override
    public void nextOrderStep(Long chatId) {
        int orderStep = repository.findOrderStepNumberByChatId(chatId);
        Map<Integer, Command<Long>> orderSteps = repository.getOrderSteps();
        if (orderStep < orderSteps.size()) {
            orderStep++;
            repository.setOrderStepNumber(chatId, orderStep);
            orderSteps.get(orderStep).execute(chatId);
        }
    }

    @Override
    public Order findCachedOrderByChatId(Long chatId) {
        if (chatId == null) {
            throw new IllegalArgumentException("ChatId of Client should not be NULL");
        }
        return repository.findCachedOrderByChatId(chatId);
    }

    @Override
    public void saveCachedOrder(Long chatId, Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order should not be NULL");
        }
        if (order.getClient() == null) {
            throw new ValidationException("Client should not be NULL");
        }
        repository.saveCachedOrder(chatId, order);
    }

    @Override
    public void updateCachedOrder(Long chatId, Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order should not be NULL");
        }
        if (order.getClient() == null) {
            throw new ValidationException("Client should not be NULL");
        }
        repository.updateCachedOrder(chatId, order);
    }

    @Override
    public void deleteCachedOrderByChatId(Long chatId) {
        if (chatId == null) {
            throw new IllegalArgumentException("ChatId of Client should not be NULL");
        }
        repository.deleteCachedOrderByChatId(chatId);
    }

}
