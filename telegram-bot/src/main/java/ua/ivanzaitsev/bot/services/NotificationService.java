package ua.ivanzaitsev.bot.services;

import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ua.ivanzaitsev.bot.models.entities.Order;

public interface NotificationService {

    void notifyAdminChatAboutNewOrder(AbsSender absSender, Order order) throws TelegramApiException;

}
