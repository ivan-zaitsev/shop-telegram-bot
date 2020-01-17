package ua.ivan909020.bot.services;

import ua.ivan909020.bot.domain.models.InlineQuerySend;
import ua.ivan909020.bot.domain.models.MessageEdit;
import ua.ivan909020.bot.domain.models.MessageSend;

public interface TelegramService {

    void sendMessage(MessageSend message);

    void editMessageText(MessageEdit message);

    void editMessageKeyboard(MessageEdit message);

    void sendInlineQuery(InlineQuerySend inlineQuery);

}
