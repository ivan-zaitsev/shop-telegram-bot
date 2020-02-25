package ua.ivan909020.bot.handlers.impl;

import org.telegram.telegrambots.meta.api.objects.Message;
import ua.ivan909020.bot.commands.impl.OrderEnterAddressCommand;
import ua.ivan909020.bot.commands.impl.OrderEnterCityCommand;
import ua.ivan909020.bot.commands.impl.OrderEnterNameCommand;
import ua.ivan909020.bot.commands.impl.OrderEnterPhoneNumberCommand;
import ua.ivan909020.bot.handlers.Handler;
import ua.ivan909020.bot.services.ClientService;
import ua.ivan909020.bot.services.OrderStepService;
import ua.ivan909020.bot.services.impl.ClientServiceDefault;
import ua.ivan909020.bot.services.impl.OrderStepServiceDefault;

class ActionHandler implements Handler<Message> {

    private final ClientService clientService = ClientServiceDefault.getInstance();
    private final OrderStepService orderStepService = OrderStepServiceDefault.getInstance();

    private final OrderEnterNameCommand enterNameCommand = OrderEnterNameCommand.getInstance();
    private final OrderEnterPhoneNumberCommand enterPhoneNumberCommand = OrderEnterPhoneNumberCommand.getInstance();
    private final OrderEnterCityCommand enterCityNameCommand = OrderEnterCityCommand.getInstance();
    private final OrderEnterAddressCommand orderEnterAddressCommand = OrderEnterAddressCommand.getInstance();

    @Override
    public void handle(Message message) {
        Long chatId = message.getChatId();
        String text = message.getText();
        String action = clientService.findActionByChatId(chatId);

        if ("order=enter-client-name".equals(action)) {
            if (enterNameCommand.doEnterName(chatId, text)) {
            	orderStepService.nextOrderStep(chatId);
            }
        } else if ("order=enter-client-phone-number".equals(action)) {
            if(enterPhoneNumberCommand.doEnterPhoneNumber(chatId, text)) {
            	orderStepService.nextOrderStep(chatId);
            }
        } else if ("order=enter-client-city".equals(action)) {
            if (enterCityNameCommand.doEnterCity(chatId, text)) {
            	orderStepService.nextOrderStep(chatId);
            }
        } else if ("order=enter-client-address".equals(action)) {
            if (orderEnterAddressCommand.doEnterAddress(chatId, text)) {
            	orderStepService.nextOrderStep(chatId);
            }
        }
    }

}
