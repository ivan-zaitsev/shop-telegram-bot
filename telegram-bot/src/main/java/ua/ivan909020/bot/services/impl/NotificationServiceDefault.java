package ua.ivan909020.bot.services.impl;

import ua.ivan909020.bot.core.ConfigReader;
import ua.ivan909020.bot.domain.entities.Client;
import ua.ivan909020.bot.domain.entities.Order;
import ua.ivan909020.bot.domain.entities.OrderItem;
import ua.ivan909020.bot.domain.models.MessageSend;
import ua.ivan909020.bot.services.NotificationService;
import ua.ivan909020.bot.services.TelegramService;

import java.util.List;

public class NotificationServiceDefault implements NotificationService {

    private static final NotificationService INSTANCE = new NotificationServiceDefault();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();

    private static final ConfigReader CONFIG = ConfigReader.getInstance();
    private static final String ADMIN_PANEL_BASE_URL = CONFIG.get("admin-panel.base-url");
    private static final Long TELEGRAM_ADMIN_CHAT_ID = Long.parseLong(CONFIG.get("telegram.admin.chat-id"));

    private NotificationServiceDefault() {
    }

    public static NotificationService getInstance() {
        return INSTANCE;
    }

    @Override
    public void notifyAdminChatAboutNewOrder(Order order) {
        telegramService.sendMessage(new MessageSend(TELEGRAM_ADMIN_CHAT_ID, createOrderAndClientInformation(order)));
        telegramService.sendMessage(new MessageSend(TELEGRAM_ADMIN_CHAT_ID, createOrderItemsInformation(order)));
    }

    private String createOrderAndClientInformation(Order order) {
        return "#order_" + order.getId() + "\n" +
                "<b>Order url</b>:\n" + buildOrderUrl(order.getId()) + "\n\n" +
                "<b>Order information</b>:\n" + buildOrderInformation(order) + "\n\n" +
                "<b>Client information</b>:\n" + buildClientInformation(order.getClient());
    }

    private String buildOrderUrl(Integer orderId) {
        return ADMIN_PANEL_BASE_URL + "/orders/edit/" + orderId;
    }

    private String buildOrderInformation(Order order) {
        return "-Amount: " + order.getAmount() + " $";
    }

    private String buildClientInformation(Client client) {
        return "-Name: " + client.getName() + "\n" +
                "-Phone number: " + client.getPhoneNumber() + "\n" +
                "-City: " + client.getCity() + "\n" +
                "-Address: " + client.getAddress() + "\n" +
                "<a href=\"tg://user?id=" + client.getChatId() + "\">Open profile</a>";
    }

    private String createOrderItemsInformation(Order order) {
        return "#order_" + order.getId() + "\n" +
                "<b>Order items</b>:\n" + buildOrderItemsInformation(order.getItems());
    }

    private String buildOrderItemsInformation(List<OrderItem> orderItems) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem orderItem = orderItems.get(i);

            result.append(i + 1).append(") ").append(orderItem.getProductName()).append(" — ")
                    .append(orderItem.getQuantity()).append(" pcs. = ")
                    .append(orderItem.getProductPrice() * orderItem.getQuantity()).append(" $\n");
        }

        return result.toString();
    }

}
