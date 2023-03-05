package ua.ivan909020.admin.services.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ua.ivan909020.admin.services.TelegramService;

@Service
public class TelegramServiceDefault extends DefaultAbsSender implements TelegramService {

    public TelegramServiceDefault(@Value("${telegram.bot.token}") String telegramBotToken) {
        super(new DefaultBotOptions(), telegramBotToken);
    }

    @Override
    public void sendMessage(Long chatId, String message) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(message);
        sendMessage.setParseMode("HTML");

        execute(sendMessage);
    }

}
