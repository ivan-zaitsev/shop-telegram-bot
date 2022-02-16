package ua.ivan909020.bot.handlers.impl;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.ivan909020.bot.commands.impl.order.OrderEnterAddressCommand;
import ua.ivan909020.bot.commands.impl.order.OrderEnterCityCommand;
import ua.ivan909020.bot.commands.impl.order.OrderEnterNameCommand;
import ua.ivan909020.bot.commands.impl.order.OrderEnterPhoneNumberCommand;
import ua.ivan909020.bot.handlers.Handler;
import ua.ivan909020.bot.models.domain.ClientAction;
import ua.ivan909020.bot.services.ClientActionService;
import ua.ivan909020.bot.services.ClientService;
import ua.ivan909020.bot.services.impl.ClientActionServiceDefault;
import ua.ivan909020.bot.services.impl.ClientServiceDefault;

class ActionHandler implements Handler {

    private final ClientActionService clientActionService = ClientActionServiceDefault.getInstance();

    private final OrderEnterNameCommand enterNameCommand = OrderEnterNameCommand.getInstance();
    private final OrderEnterPhoneNumberCommand enterPhoneNumberCommand = OrderEnterPhoneNumberCommand.getInstance();
    private final OrderEnterCityCommand enterCityNameCommand = OrderEnterCityCommand.getInstance();
    private final OrderEnterAddressCommand orderEnterAddressCommand = OrderEnterAddressCommand.getInstance();

    @Override
    public boolean supports(Update update) {
        return update.hasMessage() && update.getMessage().getReplyMarkup() == null;
    }

    @Override
    public void handle(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        ClientAction clientAction = clientActionService.findActionByChatId(chatId);
        String action = clientAction != null ? clientAction.getAction() : null;

        if ("order=enter-client-name".equals(action)) {
            enterNameCommand.doEnterName(chatId, text);
        } else if ("order=enter-client-phone-number".equals(action)) {
            enterPhoneNumberCommand.doEnterPhoneNumber(chatId, text);
        } else if ("order=enter-client-city".equals(action)) {
            enterCityNameCommand.doEnterCity(chatId, text);
        } else if ("order=enter-client-address".equals(action)) {
            orderEnterAddressCommand.doEnterAddress(chatId, text);
        }
    }

}
