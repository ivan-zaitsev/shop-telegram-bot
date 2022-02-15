package ua.ivan909020.bot.repositories;

import ua.ivan909020.bot.commands.CommandSequence;
import ua.ivan909020.bot.models.entities.Order;

public interface OrderStepRepository {

    CommandSequence<Long> findOrderStepByClientChatId(Long chatId);

    void updateOrderStepByChatId(Long chatId, CommandSequence<Long> orderStep);

    Order findCachedOrderByChatId(Long chatId);

    void updateCachedOrder(Long chatId, Order order);

    void deleteCachedOrderByChatId(Long chatId);

}
