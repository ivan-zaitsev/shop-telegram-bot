package ua.ivan909020.bot.services.impl;

import ua.ivan909020.bot.commands.CommandSequence;
import ua.ivan909020.bot.models.entities.Order;
import ua.ivan909020.bot.exceptions.ValidationException;
import ua.ivan909020.bot.repositories.OrderStepRepository;
import ua.ivan909020.bot.repositories.memory.OrderStepRepositoryDefault;
import ua.ivan909020.bot.services.OrderStepService;

public class OrderStepServiceDefault implements OrderStepService {

    private static final OrderStepService INSTANCE = new OrderStepServiceDefault();

    private final OrderStepRepository repository = new OrderStepRepositoryDefault();

    private OrderStepServiceDefault() {
    }

    public static OrderStepService getInstance() {
        return INSTANCE;
    }

    @Override
    public void updateOrderStepByChatId(Long chatId, CommandSequence<Long> orderStep) {
        repository.updateOrderStepByChatId(chatId, orderStep);
    }

    @Override
    public void executePreviousOrderStep(Long chatId) {
        CommandSequence<Long> orderStep = repository.findOrderStepByClientChatId(chatId);

        if (orderStep != null) {
            orderStep.doPreviousCommand(chatId);
        }
    }

    @Override
    public void executeCurrentOrderStep(Long chatId) {
        CommandSequence<Long> orderStep = repository.findOrderStepByClientChatId(chatId);

        if (orderStep != null) {
            orderStep.execute(chatId);
        }
    }

    @Override
    public void executeNextOrderStep(Long chatId) {
        CommandSequence<Long> orderStep = repository.findOrderStepByClientChatId(chatId);

        if (orderStep != null) {
            orderStep.doNextCommand(chatId);
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
