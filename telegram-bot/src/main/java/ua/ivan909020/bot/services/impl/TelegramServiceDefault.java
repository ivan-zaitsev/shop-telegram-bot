package ua.ivan909020.bot.services.impl;

import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ua.ivan909020.bot.core.TelegramBot;
import ua.ivan909020.bot.exceptions.SendMessageException;
import ua.ivan909020.bot.models.domain.InlineQuerySend;
import ua.ivan909020.bot.models.domain.MessageEdit;
import ua.ivan909020.bot.models.domain.MessageSend;
import ua.ivan909020.bot.services.TelegramService;

public class TelegramServiceDefault extends DefaultAbsSender implements TelegramService {

    private static final TelegramService INSTANCE = new TelegramServiceDefault();

    private TelegramServiceDefault() {
        super(new DefaultBotOptions(), TelegramBot.TELEGRAM_BOT_TOKEN);
    }

    public static TelegramService getInstance() {
        return INSTANCE;
    }

    @Override
    public void sendMessage(MessageSend message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(message.getText());
        sendMessage.setParseMode("HTML");
        if (message.getKeyboard() != null) {
            sendMessage.setReplyMarkup(message.getKeyboard());
        }

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new SendMessageException("Failed send text message " + message, e);
        }
    }

    @Override
    public void editMessageText(MessageEdit message) {
        EditMessageText editMessageText = new EditMessageText();
        if (message.getMessageId() != null) {
            editMessageText.setChatId(String.valueOf(message.getChatId()));
            editMessageText.setMessageId(message.getMessageId());
        } else {
            editMessageText.setInlineMessageId(message.getInlineMessageId());
        }
        editMessageText.setText(message.getText());
        editMessageText.setParseMode("HTML");
        if (message.getKeyboard() != null) {
            editMessageText.setReplyMarkup(message.getKeyboard());
        }

        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            throw new SendMessageException("Failed edit text message " + message, e);
        }
    }

    @Override
    public void sendInlineQuery(InlineQuerySend inlineQuery) {
        AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
        answerInlineQuery.setInlineQueryId(inlineQuery.getInlineQueryId());
        answerInlineQuery.setResults(inlineQuery.getInlineQueryResults());
        answerInlineQuery.setCacheTime(1);
        if (inlineQuery.getOffset() != null) {
            answerInlineQuery.setNextOffset(Integer.toString(inlineQuery.getOffset()));
        }

        try {
            execute(answerInlineQuery);
        } catch (TelegramApiException e) {
            throw new SendMessageException("Failed send inline query " + inlineQuery, e);
        }
    }

}
