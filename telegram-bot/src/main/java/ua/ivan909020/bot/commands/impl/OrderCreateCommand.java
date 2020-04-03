package ua.ivan909020.bot.commands.impl;

import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.commands.Commands;
import ua.ivan909020.bot.domain.entities.Order;
import ua.ivan909020.bot.domain.models.MessageSend;
import ua.ivan909020.bot.services.*;
import ua.ivan909020.bot.services.impl.*;

public class OrderCreateCommand implements Command {

    private static final OrderCreateCommand INSTANCE = new OrderCreateCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final ClientService clientService = ClientServiceDefault.getInstance();
    private final CartService cartService = CartServiceDefault.getInstance();
    private final OrderStepService orderStepService = OrderStepServiceDefault.getInstance();
    private final OrderService orderService = OrderServiceDefault.getInstance();

    private OrderCreateCommand() {
    }

    public static OrderCreateCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(Long chatId) {
        Order order = orderStepService.findCachedOrderByChatId(chatId);
        if (order != null && order.getClient() != null) {
            clientService.update(order.getClient());
            orderService.save(order);
        }
        clearClientCache(chatId);
        telegramService.sendMessage(new MessageSend(chatId, "Order created.", Commands.createGeneralMenuKeyboard()));
    }


    private void clearClientCache(Long chatId) {
        clientService.setActionForChatId(chatId, null);
        cartService.deleteAllCartItemsByChatId(chatId);
        orderStepService.deleteCachedOrderByChatId(chatId);
    }

}
