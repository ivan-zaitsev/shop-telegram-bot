package ua.ivanzaitsev.bot.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public interface CommandHandler extends Handler {

    void executeCommand(AbsSender absSender, Update update, Long chatId) throws TelegramApiException;

}
