package ua.ivan909020.bot.services;

import ua.ivan909020.bot.domain.entities.Order;

public interface OrderStepService {

    void revokeOrderStep(Long chatId);

    void previousOrderStep(Long chatId);

    void nextOrderStep(Long chatId);

    Order findCachedOrderByChatId(Long chatId);

    void saveCachedOrder(Long chatId, Order order);

    void updateCachedOrder(Long chatId, Order order);

    void deleteCachedOrderByChatId(Long chatId);
}
