package ua.ivanzaitsev.bot.services.impl;

import java.util.List;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ua.ivanzaitsev.bot.core.ConfigReader;
import ua.ivanzaitsev.bot.models.entities.Client;
import ua.ivanzaitsev.bot.models.entities.Order;
import ua.ivanzaitsev.bot.models.entities.OrderItem;
import ua.ivanzaitsev.bot.services.NotificationService;

public class NotificationServiceDefault implements NotificationService {

    private final String adminPanelBaseUrl;
    private final Long telegramAdminChatId;

    public NotificationServiceDefault(ConfigReader configReader) {
        this.adminPanelBaseUrl = configReader.get("admin-panel.base-url");
        this.telegramAdminChatId = Long.parseLong(configReader.get("telegram.admin.chat-id"));
    }

    @Override
    public void notifyAdminChatAboutNewOrder(AbsSender absSender, Order order) throws TelegramApiException {
        sendOrderAndClientInformationMessage(absSender, order);
        sendOrderItemsInformationMessage(absSender, order);
    }
    private void sendOrderAndClientInformationMessage(AbsSender absSender, Order order) throws TelegramApiException {
        SendMessage message = SendMessage.builder()
                .chatId(telegramAdminChatId)
                .text(createOrderAndClientInformation(order))
                .parseMode("HTML")
                .build();
        absSender.execute(message);
    }

    private void sendOrderItemsInformationMessage(AbsSender absSender, Order order) throws TelegramApiException {
        SendMessage message = SendMessage.builder()
                .chatId(telegramAdminChatId)
                .text(createOrderItemsInformation(order))
                .parseMode("HTML")
                .build();
        absSender.execute(message);
    }

    private String createOrderAndClientInformation(Order order) {
        return "#order_" + order.getId() + "\n" +
                "<b>Order url</b>:\n" + buildOrderUrl(order.getId()) + "\n\n" +
                "<b>Order information</b>:\n" + buildOrderInformation(order) + "\n\n" +
                "<b>Client information</b>:\n" + buildClientInformation(order.getClient());
    }

    private String buildOrderUrl(Integer orderId) {
        return adminPanelBaseUrl + "/orders/edit/" + orderId;
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
