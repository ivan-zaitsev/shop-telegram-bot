package ua.ivan909020.bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import ua.ivan909020.bot.core.TelegramBot;

public class Application {

    public static void main(String[] args) throws TelegramApiRequestException {
        ApiContextInitializer.init();
        new TelegramBotsApi().registerBot(new TelegramBot());
    }

}