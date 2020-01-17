package ua.ivan909020.bot.repositories.impl;

import ua.ivan909020.bot.repositories.OrderStepRepository;

import java.util.HashMap;
import java.util.Map;

public class OrderStepRepositoryDefault implements OrderStepRepository {

    private Map<Long, Integer> orderStepNumbers = new HashMap<>();

    @Override
    public void setOrderStepNumber(Long chatId, Integer orderStepNumber) {
        orderStepNumbers.put(chatId, orderStepNumber);
    }

    @Override
    public Integer findOrderStepNumberByChatId(Long chatId) {
        return orderStepNumbers.getOrDefault(chatId, 0);
    }

}
