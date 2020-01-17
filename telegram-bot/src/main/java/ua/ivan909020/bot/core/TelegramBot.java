package ua.ivan909020.bot.core;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.ivan909020.bot.handlers.impl.UpdateHandler;

public class TelegramBot extends TelegramLongPollingBot {

    private final UpdateHandler updateHandler = new UpdateHandler();

    private static final ConfigReader CONFIG = ConfigReader.getInstance();

    @Override
    public void onUpdateReceived(Update update) {
        updateHandler.handle(update);
    }

    @Override
    public String getBotUsername() {
        return CONFIG.get("telegram.bot.username");
    }

    @Override
    public String getBotToken() {
        return CONFIG.get("telegram.bot.token");
    }

}
