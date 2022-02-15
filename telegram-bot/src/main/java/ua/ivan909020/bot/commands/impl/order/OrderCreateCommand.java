package ua.ivan909020.bot.commands.impl.order;

import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.commands.Commands;
import ua.ivan909020.bot.models.entities.Order;
import ua.ivan909020.bot.models.domain.MessageSend;
import ua.ivan909020.bot.repositories.hibernate.HibernateFactory;
import ua.ivan909020.bot.services.CartService;
import ua.ivan909020.bot.services.ClientActionService;
import ua.ivan909020.bot.services.ClientService;
import ua.ivan909020.bot.services.MessageService;
import ua.ivan909020.bot.services.NotificationService;
import ua.ivan909020.bot.services.OrderService;
import ua.ivan909020.bot.services.OrderStepService;
import ua.ivan909020.bot.services.TelegramService;
import ua.ivan909020.bot.services.impl.CartServiceDefault;
import ua.ivan909020.bot.services.impl.ClientActionServiceDefault;
import ua.ivan909020.bot.services.impl.ClientServiceDefault;
import ua.ivan909020.bot.services.impl.MessageServiceCached;
import ua.ivan909020.bot.services.impl.NotificationServiceDefault;
import ua.ivan909020.bot.services.impl.OrderServiceDefault;
import ua.ivan909020.bot.services.impl.OrderStepServiceDefault;
import ua.ivan909020.bot.services.impl.TelegramServiceDefault;

public class OrderCreateCommand implements Command<Long> {

    private static final OrderCreateCommand INSTANCE = new OrderCreateCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final ClientService clientService = ClientServiceDefault.getInstance();
    private final ClientActionService clientActionService = ClientActionServiceDefault.getInstance();
    private final CartService cartService = CartServiceDefault.getInstance();
    private final OrderStepService orderStepService = OrderStepServiceDefault.getInstance();
    private final OrderService orderService = OrderServiceDefault.getInstance();
    private final NotificationService notificationService = NotificationServiceDefault.getInstance();
    private final MessageService messageService = MessageServiceCached.getInstance();

    private OrderCreateCommand() {
    }

    public static OrderCreateCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(Long chatId) {
        Order order = orderStepService.findCachedOrderByChatId(chatId);

        HibernateFactory.inTransactionVoid(session -> {
            orderService.save(order);
            clientService.update(order.getClient());
        });

        sendOrderMessageToClient(chatId);
        clearClientOrderCache(chatId);

        notificationService.notifyAdminChatAboutNewOrder(order);
    }

    private void sendOrderMessageToClient(Long chatId) {
        String message = messageService.findByName("ORDER_CREATED_MESSAGE").buildText();
        telegramService.sendMessage(new MessageSend(chatId, message, Commands.createGeneralMenuKeyboard()));
    }

    private void clearClientOrderCache(Long chatId) {
        clientActionService.deleteActionByChatId(chatId);
        orderStepService.deleteCachedOrderByChatId(chatId);
        cartService.deleteAllCartItemsByChatId(chatId);
    }

}
