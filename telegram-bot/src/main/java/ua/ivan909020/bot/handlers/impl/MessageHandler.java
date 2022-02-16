package ua.ivan909020.bot.handlers.impl;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.commands.Commands;
import ua.ivan909020.bot.commands.impl.CartCommand;
import ua.ivan909020.bot.commands.impl.CatalogCommand;
import ua.ivan909020.bot.commands.impl.StartCommand;
import ua.ivan909020.bot.commands.impl.order.OrderCancelCommand;
import ua.ivan909020.bot.handlers.Handler;
import ua.ivan909020.bot.services.ClientService;
import ua.ivan909020.bot.services.OrderStepService;
import ua.ivan909020.bot.services.impl.ClientServiceDefault;
import ua.ivan909020.bot.services.impl.OrderStepServiceDefault;

import java.util.HashMap;
import java.util.Map;

class MessageHandler implements Handler {

    private final OrderStepService orderStepService = OrderStepServiceDefault.getInstance();

    private final StartCommand startCommand = StartCommand.getInstance();
    private final CatalogCommand catalogCommand = CatalogCommand.getInstance();
    private final CartCommand cartCommand = CartCommand.getInstance();
    private final OrderCancelCommand orderCancelCommand = OrderCancelCommand.getInstance();

    private final Map<String, Command<Long>> commands = createCommandHandlers();

    private Map<String, Command<Long>> createCommandHandlers() {
        Map<String, Command<Long>> result = new HashMap<>();

        result.put(Commands.CATALOG_COMMAND, catalogCommand::execute);
        result.put(Commands.CART_COMMAND, cartCommand::execute);
        result.put(Commands.ORDER_PREVIOUS_STEP_COMMAND, orderStepService::executePreviousOrderStep);
        result.put(Commands.ORDER_NEXT_STEP_COMMAND, orderStepService::executeNextOrderStep);
        result.put(Commands.ORDER_CANCEL_COMMAND, orderCancelCommand::execute);

        return result;
    }

    @Override
    public boolean supports(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return false;
        }

        String text = update.getMessage().getText();
        return text.startsWith(Commands.START_COMMAND) || commands.containsKey(text);
    }

    @Override
    public void handle(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if (text.startsWith(Commands.START_COMMAND)) {
            startCommand.execute(chatId);
        } else if (commands.containsKey(text)) {
            commands.get(text).execute(chatId);
        }
    }

}
