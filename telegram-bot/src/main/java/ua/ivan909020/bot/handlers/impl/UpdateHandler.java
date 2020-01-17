package ua.ivan909020.bot.handlers.impl;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.ivan909020.bot.handlers.Handler;

public class UpdateHandler implements Handler<Update> {

    private final MessageHandler messageHandler = new MessageHandler();
    private final ContactHandler contactHandler = new ContactHandler();
    private final InlineQueryHandler inlineQueryHandler = new InlineQueryHandler();
    private final CallbackHandler callbackHandler = new CallbackHandler();

    @Override
    public void handle(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText() && message.getReplyMarkup() == null) {
                messageHandler.handle(message);
            } else if (message.hasContact()) {
                contactHandler.handle(message);
            }
        } else if (update.hasInlineQuery()) {
            inlineQueryHandler.handle(update.getInlineQuery());
        } else if (update.hasCallbackQuery()) {
            callbackHandler.handle(update.getCallbackQuery());
        }
    }

}
