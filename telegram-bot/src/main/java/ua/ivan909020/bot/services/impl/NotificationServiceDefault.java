package ua.ivan909020.bot.services.impl;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ua.ivan909020.bot.core.ConfigReader;
import ua.ivan909020.bot.domain.entities.Client;
import ua.ivan909020.bot.exceptions.FailedSendMessageException;
import ua.ivan909020.bot.services.ClientService;
import ua.ivan909020.bot.services.NotificationService;

public class NotificationServiceDefault extends DefaultAbsSender implements NotificationService {

    private static final NotificationService INSTANSE = new NotificationServiceDefault();

    private NotificationServiceDefault() {
        super(ApiContext.getInstance(DefaultBotOptions.class));
    }

    private static final ConfigReader CONFIG = ConfigReader.getInstance();

    public static NotificationService getInstanse() {
        return INSTANSE;
    }

    public static final long ADMINID = 123456; //Telegram chatId of admin

    @Override
    public String getBotToken() {
        return CONFIG.get("telegram.bot.token");
    }

    @Override
    public void notifyAdmin(Client client) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(ADMINID).setText("You have one new order. Check it out!\n" +
                "From: " + client.getName() + "\n" +
                "Phone: " + client.getPhoneNumber() + "\n" +
                "City: " + client.getCity() + "\n" +
                "Adress: " + client.getAddress());
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new FailedSendMessageException(String.format("Failed send text message %s", sendMessage), e);
        }
    }
}
