package ua.ivan909020.bot.core;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import ua.ivan909020.bot.handlers.impl.UpdateHandler;

public class TelegramBot extends TelegramLongPollingBot {

    private final UpdateHandler updateHandler = new UpdateHandler();

    private static final ConfigReader CONFIG = ConfigReader.getInstance();
    public static final String TELEGRAM_BOT_USERNAME = CONFIG.get("telegram.bot.username");
    public static final String TELEGRAM_BOT_TOKEN = CONFIG.get("telegram.bot.token");

    public TelegramBot() {
        super(new DefaultBotOptions(), TELEGRAM_BOT_TOKEN);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (updateHandler.supports(update)) {
            updateHandler.handle(update);
        }
    }

    @Override
    public String getBotUsername() {
        return TELEGRAM_BOT_USERNAME;
    }

}
