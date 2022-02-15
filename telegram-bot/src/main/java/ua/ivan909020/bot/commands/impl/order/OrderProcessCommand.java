package ua.ivan909020.bot.commands.impl.order;

import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.commands.CommandSequence;
import ua.ivan909020.bot.models.entities.Client;
import ua.ivan909020.bot.models.entities.Order;
import ua.ivan909020.bot.models.entities.OrderStatus;
import ua.ivan909020.bot.models.domain.CartItem;
import ua.ivan909020.bot.exceptions.EntityNotFoundException;
import ua.ivan909020.bot.services.CartService;
import ua.ivan909020.bot.services.ClientService;
import ua.ivan909020.bot.services.OrderService;
import ua.ivan909020.bot.services.OrderStepService;
import ua.ivan909020.bot.services.impl.CartServiceDefault;
import ua.ivan909020.bot.services.impl.ClientServiceDefault;
import ua.ivan909020.bot.services.impl.OrderServiceDefault;
import ua.ivan909020.bot.services.impl.OrderStepServiceDefault;

import java.time.LocalDateTime;
import java.util.List;

public class OrderProcessCommand implements CommandSequence<Long> {

    private static final OrderProcessCommand INSTANCE = new OrderProcessCommand();

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
            throw new EntityNotFoundException("Client with chatId '" + chatId + "' not found");
        }

        Order order = buildOrder(client, cartService.findAllCartItemsByChatId(chatId));
        orderStepService.updateCachedOrder(chatId, order);

        doNextCommand(chatId);
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

    @Override
    public void doPreviousCommand(Long chatId) {
        OrderCancelCommand.getInstance().execute(chatId);
    }

    @Override
    public void doNextCommand(Long chatId) {
        OrderEnterNameCommand.getInstance().execute(chatId);
    }

}
