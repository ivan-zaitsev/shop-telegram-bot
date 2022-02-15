package ua.ivan909020.bot.services;

import ua.ivan909020.bot.commands.CommandSequence;
import ua.ivan909020.bot.models.entities.Order;

public interface OrderStepService {

    void updateOrderStepByChatId(Long chatId, CommandSequence<Long> orderStep);

    void executePreviousOrderStep(Long chatId);

    void executeCurrentOrderStep(Long chatId);

    void executeNextOrderStep(Long chatId);

    Order findCachedOrderByChatId(Long chatId);

    void updateCachedOrder(Long chatId, Order order);

    void deleteCachedOrderByChatId(Long chatId);

}
