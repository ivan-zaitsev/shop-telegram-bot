package ua.ivan909020.bot.handlers.impl;

import org.telegram.telegrambots.meta.api.objects.Message;
import ua.ivan909020.bot.commands.impl.CartCommand;
import ua.ivan909020.bot.commands.impl.MenuCommand;
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
    private final MenuCommand menuCommand = MenuCommand.getInstance();
    private final CartCommand cartCommand = CartCommand.getInstance();

    @Override
    public void handle(Message message) {
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.startsWith("/start")) {
            startCommand.execute(chatId);
            return;
        }

        if (text.endsWith("Menu")) {
            menuCommand.execute(chatId);
            return;
        } else if (text.endsWith("Cart")) {
            cartCommand.execute(chatId);
            return;
        }

        if (text.endsWith("Correct")) {
            orderStepService.nextOrderStep(chatId);
            return;
        } else if (text.endsWith("Back")) {
            orderStepService.previousOrderStep(chatId);
            return;
        } else if (text.endsWith("Cancel order")) {
            startCommand.execute(chatId);
            return;
        }

        if (clientService.findActionByChatId(chatId) != null) {
            actionHandler.handle(message);
        }
    }

}
