package ua.ivan909020.bot.handlers.impl;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.ivan909020.bot.commands.impl.order.OrderEnterPhoneNumberCommand;
import ua.ivan909020.bot.handlers.Handler;
import ua.ivan909020.bot.models.domain.ClientAction;
import ua.ivan909020.bot.services.ClientActionService;
import ua.ivan909020.bot.services.impl.ClientActionServiceDefault;

public class ContactHandler implements Handler {

    private final ClientActionService clientActionService = ClientActionServiceDefault.getInstance();
    
    private final OrderEnterPhoneNumberCommand enterPhoneNumberCommand = OrderEnterPhoneNumberCommand.getInstance();

    @Override
    public boolean supports(Update update) {
        return update.hasMessage() && update.getMessage().hasContact();
    }

    @Override
    public void handle(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String phoneNumber = message.getContact().getPhoneNumber();

        ClientAction clientAction = clientActionService.findActionByChatId(chatId);
        String action = clientAction != null ? clientAction.getAction() : null;

        if ("order=enter-client-phone-number".equals(action)) {
            enterPhoneNumberCommand.doEnterPhoneNumber(chatId, phoneNumber);
        }
    }

}
