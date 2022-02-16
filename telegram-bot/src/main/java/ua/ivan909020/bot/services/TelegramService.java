package ua.ivan909020.bot.services;

import ua.ivan909020.bot.models.domain.InlineQuerySend;
import ua.ivan909020.bot.models.domain.MessageEdit;
import ua.ivan909020.bot.models.domain.MessageSend;

public interface TelegramService {

    void sendMessage(MessageSend message);

    void editMessageText(MessageEdit message);

    void sendInlineQuery(InlineQuerySend inlineQuery);

}
