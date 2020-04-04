package ua.ivan909020.bot.repositories;

import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.domain.entities.Order;

import java.util.Map;

public interface OrderStepRepository {

    void setOrderStepNumber(Long chatId, Integer orderStep);

    Integer findOrderStepNumberByChatId(Long chatId);

    Map<Integer, Command<Long>> getOrderSteps();

    Order findCachedOrderByChatId(Long chatId);

    void saveCachedOrder(Long chatId, Order order);

    void updateCachedOrder(Long chatId, Order order);

    void deleteCachedOrderByChatId(Long chatId);

}
