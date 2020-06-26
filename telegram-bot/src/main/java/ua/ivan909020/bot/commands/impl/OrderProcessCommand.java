package ua.ivan909020.bot.commands.impl;

import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.domain.entities.Client;
import ua.ivan909020.bot.domain.entities.Order;
import ua.ivan909020.bot.domain.entities.OrderStatus;
import ua.ivan909020.bot.domain.models.CartItem;
import ua.ivan909020.bot.domain.models.MessageSend;
import ua.ivan909020.bot.services.*;
import ua.ivan909020.bot.services.impl.*;

import java.time.LocalDateTime;
import java.util.List;

public class OrderProcessCommand implements Command<Long> {

    private static final OrderProcessCommand INSTANCE = new OrderProcessCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final ClientService clientService = ClientServiceDefault.getInstance();
    private final CartService cartService = CartServiceDefault.getInstance();
    private final OrderStepService orderStepService = OrderStepServiceDefault.getInstance();
    private final OrderService orderService = OrderServiceDefault.getInstance();

    private OrderProcessCommand() {
    }

    public static OrderProcessCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(Long chatId) {
        Client client = clientService.findByChatId(chatId);
        if (client == null) {
            telegramService.sendMessage(new MessageSend(chatId, "Error processing order. Press /start and try again."));
        }
        orderStepService.saveCachedOrder(chatId, buildOrder(client, cartService.findAllCartItemsByChatId(chatId)));
        orderStepService.nextOrderStep(chatId);
    }

    private Order buildOrder(Client client, List<CartItem> cartItems) {
        Order order = new Order();
        order.setClient(client);
        order.setCreatedDate(LocalDateTime.now());
        order.setStatus(OrderStatus.WAITING);
        order.setAmount(cartService.calculateTotalPrice(cartItems));
        order.setItems(orderService.fromCartItems(cartItems));
        return order;
    }

}
