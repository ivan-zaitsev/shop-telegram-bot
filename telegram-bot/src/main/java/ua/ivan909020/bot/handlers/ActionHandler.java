package ua.ivan909020.bot.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface ActionHandler extends Handler {

    boolean canHandleAction(Update update, String action);

    void handleAction(AbsSender absSender, Update update, String action) throws TelegramApiException;

}
