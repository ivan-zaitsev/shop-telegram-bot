package ua.ivan909020.bot.commands.impl.order;

import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.commands.Commands;
import ua.ivan909020.bot.models.domain.MessageSend;
import ua.ivan909020.bot.services.ClientActionService;
import ua.ivan909020.bot.services.OrderStepService;
import ua.ivan909020.bot.services.TelegramService;
import ua.ivan909020.bot.services.impl.ClientActionServiceDefault;
import ua.ivan909020.bot.services.impl.OrderStepServiceDefault;
import ua.ivan909020.bot.services.impl.TelegramServiceDefault;

public class OrderCancelCommand implements Command<Long> {

    private static final OrderCancelCommand INSTANCE = new OrderCancelCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final OrderStepService orderStepService = OrderStepServiceDefault.getInstance();
    private final ClientActionService clientActionService = ClientActionServiceDefault.getInstance();

    private OrderCancelCommand() {
    }

    public static OrderCancelCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(Long chatId) {
        clearClientOrderCache(chatId);

        telegramService.sendMessage(new MessageSend(chatId, "Order canceled.", Commands.createGeneralMenuKeyboard()));
    }

    private void clearClientOrderCache(Long chatId) {
        clientActionService.deleteActionByChatId(chatId);
        orderStepService.deleteCachedOrderByChatId(chatId);
    }

}
