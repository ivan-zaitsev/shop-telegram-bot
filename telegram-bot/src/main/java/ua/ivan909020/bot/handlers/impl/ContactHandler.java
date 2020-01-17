package ua.ivan909020.bot.handlers.impl;

import org.telegram.telegrambots.meta.api.objects.Message;
import ua.ivan909020.bot.commands.impl.OrderEnterPhoneNumberCommand;
import ua.ivan909020.bot.handlers.Handler;
import ua.ivan909020.bot.services.ClientService;
import ua.ivan909020.bot.services.OrderStepService;
import ua.ivan909020.bot.services.impl.ClientServiceDefault;
import ua.ivan909020.bot.services.impl.OrderStepServiceDefault;

public class ContactHandler implements Handler<Message> {

    private final ClientService clientService = ClientServiceDefault.getInstance();
    private final OrderStepService orderStepService = OrderStepServiceDefault.getInstance();

    private final OrderEnterPhoneNumberCommand enterPhoneNumberCommand = OrderEnterPhoneNumberCommand.getInstance();

    @Override
    public void handle(Message message) {
        Long chatId = message.getChatId();
        String phoneNumber = message.getContact().getPhoneNumber();
        String action = clientService.findActionByChatId(chatId);

        if ("order=enter-client-phone-number".equals(action)) {
            enterPhoneNumberCommand.doEnterPhoneNumber(chatId, phoneNumber);
            orderStepService.nextOrderStep(chatId);
        }
    }

}
