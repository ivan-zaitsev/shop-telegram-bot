package ua.ivan909020.bot.services;

import ua.ivan909020.bot.domain.entities.Order;

public interface NotificationService {

    void notifyAdminChatAboutNewOrder(Order order);

}
