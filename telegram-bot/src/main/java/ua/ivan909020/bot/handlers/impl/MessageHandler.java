package ua.ivan909020.bot.handlers.impl;

import org.telegram.telegrambots.meta.api.objects.Message;
import ua.ivan909020.bot.commands.Commands;
import ua.ivan909020.bot.commands.impl.CartCommand;
import ua.ivan909020.bot.commands.impl.CatalogCommand;
import ua.ivan909020.bot.commands.impl.StartCommand;
import ua.ivan909020.bot.handlers.Handler;
import ua.ivan909020.bot.services.ClientService;
import ua.ivan909020.bot.services.OrderStepService;
import ua.ivan909020.bot.services.impl.ClientServiceDefault;
import ua.ivan909020.bot.services.impl.OrderStepServiceDefault;

class MessageHandler implements Handler<Message> {

    private final ActionHandler actionHandler = new ActionHandler();

    private final ClientService clientService = ClientServiceDefault.getInstance();
    private final OrderStepService orderStepService = OrderStepServiceDefault.getInstance();

    private final StartCommand startCommand = StartCommand.getInstance();
    private final CatalogCommand catalogCommand = CatalogCommand.getInstance();
    private final CartCommand cartCommand = CartCommand.getInstance();

    @Override
    public void handle(Message message) {
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.startsWith(Commands.START_COMMAND)) {
            startCommand.execute(chatId);
        } else if (text.equals(Commands.CATALOG_COMMAND)) {
            catalogCommand.execute(chatId);
        } else if (text.equals(Commands.CART_COMMAND)) {
            cartCommand.execute(chatId);
        } else if (text.equals(Commands.ORDER_NEXT_STEP_COMMAND)) {
            orderStepService.nextOrderStep(chatId);
        } else if (text.equals(Commands.ORDER_PREVIOUS_STEP_COMMAND)) {
            orderStepService.previousOrderStep(chatId);
        } else if (text.equals(Commands.ORDER_CANCEL_COMMAND)) {
            startCommand.execute(chatId);
        } else if (clientService.findActionByChatId(chatId) != null) {
            actionHandler.handle(message);
        }
    }

}
