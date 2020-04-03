package ua.ivan909020.bot.services.impl;

import ua.ivan909020.bot.commands.Command;
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
        Map<Integer, Command> orderSteps = repository.getOrderSteps();
        if (orderStep > 1) {
            orderStep--;
            repository.setOrderStepNumber(chatId, orderStep);
            orderSteps.get(orderStep).execute(chatId);
        }
    }

    @Override
    public void nextOrderStep(Long chatId) {
        int orderStep = repository.findOrderStepNumberByChatId(chatId);
        Map<Integer, Command> orderSteps = repository.getOrderSteps();
        if (orderStep < orderSteps.size()) {
            orderStep++;
            repository.setOrderStepNumber(chatId, orderStep);
            orderSteps.get(orderStep).execute(chatId);
        }
    }

}
