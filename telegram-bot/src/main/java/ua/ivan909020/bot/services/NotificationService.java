package ua.ivan909020.bot.services;

import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ua.ivan909020.bot.models.entities.Order;

public interface NotificationService {

    void notifyAdminChatAboutNewOrder(AbsSender absSender, Order order) throws TelegramApiException;

}
