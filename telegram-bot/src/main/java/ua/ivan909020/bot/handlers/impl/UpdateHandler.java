package ua.ivan909020.bot.handlers.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.meta.api.objects.Update;

import ua.ivan909020.bot.handlers.Handler;

public class UpdateHandler implements Handler {

    private static final Logger LOG = LogManager.getLogger(UpdateHandler.class);

    private final MessageHandler messageHandler = new MessageHandler();
    private final CallbackHandler callbackHandler = new CallbackHandler();
    private final ContactHandler contactHandler = new ContactHandler();
    private final InlineQueryHandler inlineQueryHandler = new InlineQueryHandler();
    private final ActionHandler actionHandler = new ActionHandler();

    private Set<Handler> getHandlers() {
        Set<Handler> result = new LinkedHashSet<>();

        result.add(messageHandler);
        result.add(callbackHandler);
        result.add(contactHandler);
        result.add(inlineQueryHandler);
        result.add(actionHandler);

        return result;
    }

    @Override
    public boolean supports(Update update) {
        return true;
    }

    @Override
    public void handle(Update update) {
        try {
            handleUpdate(update);
        } catch (Exception e) {
            LOG.error("Error update handling", e);
        }
    }

    private void handleUpdate(Update update) {
        getHandlers().stream()
                .filter(handler -> handler.supports(update))
                .findFirst()
                .ifPresent(handler -> handler.handle(update));
    }

}
