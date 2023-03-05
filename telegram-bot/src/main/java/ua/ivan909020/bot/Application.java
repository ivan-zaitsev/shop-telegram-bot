package ua.ivan909020.bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import ua.ivan909020.bot.core.TelegramBot;

public class Application {

    public static void main(String[] args) throws TelegramApiException {
        new TelegramBotsApi(DefaultBotSession.class).registerBot(new TelegramBot());
    }

}