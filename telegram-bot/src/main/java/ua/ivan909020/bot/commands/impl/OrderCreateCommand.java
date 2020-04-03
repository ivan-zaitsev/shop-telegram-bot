package ua.ivan909020.bot.commands.impl;

import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.domain.entities.Client;
import ua.ivan909020.bot.domain.entities.Order;
import ua.ivan909020.bot.domain.entities.OrderState;
import ua.ivan909020.bot.domain.models.CartItem;
import ua.ivan909020.bot.domain.models.MessageSend;
import ua.ivan909020.bot.services.CartService;
import ua.ivan909020.bot.services.ClientService;
import ua.ivan909020.bot.services.OrderService;
import ua.ivan909020.bot.services.TelegramService;
import ua.ivan909020.bot.services.impl.CartServiceDefault;
import ua.ivan909020.bot.services.impl.ClientServiceDefault;
import ua.ivan909020.bot.services.impl.OrderServiceDefault;
import ua.ivan909020.bot.services.impl.TelegramServiceDefault;

import java.time.LocalDateTime;
import java.util.List;

public class OrderCreateCommand implements Command {

    private static final OrderCreateCommand INSTANCE = new OrderCreateCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final ClientService clientService = ClientServiceDefault.getInstance();
    private final CartService cartService = CartServiceDefault.getInstance();
    private final OrderService orderService = OrderServiceDefault.getInstance();

    private OrderCreateCommand() {
    }

    public static OrderCreateCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(Long chatId) {
        Client client = clientService.findByChatId(chatId);
        if (client != null) {
            orderService.save(build(client, cartService.findAllCartItemsByChatId(chatId)));
        }
        clientService.setActionForChatId(chatId, null);
        cartService.deleteAllCartItemsByChatId(chatId);
        telegramService.sendMessage(new MessageSend(chatId,
                "Order success created.", StartCommand.createGeneralMenuKeyboard()));
    }

    private Order build(Client client, List<CartItem> cartItems) {
        Order order = new Order();
        order.setClient(client);
        order.setCreatedDate(LocalDateTime.now());
        order.setState(OrderState.WAITING);
        order.setAmount(cartService.calculateTotalPrice(cartItems));
        order.setItems(orderService.from(cartItems));
        return order;
    }

}
