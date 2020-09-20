package ua.ivan909020.bot.commands.impl;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.domain.entities.Message;
import ua.ivan909020.bot.domain.entities.Product;
import ua.ivan909020.bot.domain.models.CartItem;
import ua.ivan909020.bot.domain.models.MessageEdit;
import ua.ivan909020.bot.domain.models.MessageSend;
import ua.ivan909020.bot.services.CartService;
import ua.ivan909020.bot.services.MessageService;
import ua.ivan909020.bot.services.OrderStepService;
import ua.ivan909020.bot.services.TelegramService;
import ua.ivan909020.bot.services.impl.CartServiceDefault;
import ua.ivan909020.bot.services.impl.MessageServiceCached;
import ua.ivan909020.bot.services.impl.OrderStepServiceDefault;
import ua.ivan909020.bot.services.impl.TelegramServiceDefault;

import java.util.ArrayList;
import java.util.List;

import static ua.ivan909020.bot.domain.models.MessagePlaceholder.of;

public class CartCommand implements Command<Long> {

    private static final CartCommand INSTANCE = new CartCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final CartService cartService = CartServiceDefault.getInstance();
    private final OrderStepService orderStepService = OrderStepServiceDefault.getInstance();
    private final MessageService messageService = MessageServiceCached.getInstance();

    private final static int MAX_QUANTITY_PER_PRODUCT = 50;

    private CartCommand() {
    }

    public static CartCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(Long chatId) {
        List<CartItem> cartItems = cartService.findAllCartItemsByChatId(chatId);
        cartService.setPageNumber(chatId, 0);
        if (cartItems.isEmpty()) {
            telegramService.sendMessage(new MessageSend(chatId, "Cart is empty."));
            return;
        }
        telegramService.sendMessage(new MessageSend(chatId,
                createProductText(cartItems.get(0)), createCartKeyboard(cartItems, 0)));
    }

    private String createProductText(CartItem cartItem) {
        Message message = messageService.findByName("CART_MESSAGE");
        if (cartItem != null) {
            Product product = cartItem.getProduct();
            message.applyPlaceholder(of("%PRODUCT_NAME%", product.getName()));
            message.applyPlaceholder(of("%PRODUCT_DESCRIPTION%", product.getDescription()));
            message.applyPlaceholder(of("%PRODUCT_PRICE%", product.getPrice()));
            message.applyPlaceholder(of("%PRODUCT_QUANTITY%", cartItem.getQuantity()));
            message.applyPlaceholder(of("%PRODUCT_TOTAL_PRICE%", product.getPrice() * cartItem.getQuantity()));
        }
        return message.getText();
    }

    private InlineKeyboardMarkup createCartKeyboard(List<CartItem> cartItems, int currentCartPage) {
        return new InlineKeyboardMarkup().setKeyboard(new ArrayList<List<InlineKeyboardButton>>() {{
            add(new ArrayList<InlineKeyboardButton>() {{
                add(new InlineKeyboardButton("\u2716").setCallbackData("cart=delete-product"));
                add(new InlineKeyboardButton("\u2796").setCallbackData("cart=minus-product"));
                add(new InlineKeyboardButton(cartItems.get(currentCartPage).getQuantity() + " pcs.")
                        .setCallbackData("cart=product-quantity"));
                add(new InlineKeyboardButton("\u2795").setCallbackData("cart=plus-product"));
            }});
            add(new ArrayList<InlineKeyboardButton>() {{
                add(new InlineKeyboardButton("\u25c0").setCallbackData("cart=previous-product"));
                add(new InlineKeyboardButton((currentCartPage + 1) + "/" + cartItems.size())
                        .setCallbackData("cart=current-page"));
                add(new InlineKeyboardButton("\u25b6").setCallbackData("cart=next-product"));
            }});
            add(new ArrayList<InlineKeyboardButton>() {{
                add(new InlineKeyboardButton().setText(String.format("\u2705 Order for %.2f $ Checkout?",
                        cartService.calculateTotalPrice(cartItems))).setCallbackData("cart=process-order"));
            }});
        }});
    }

    public void doDeleteProduct(Long chatId, Integer messageId) {
        List<CartItem> cartItems = cartService.findAllCartItemsByChatId(chatId);
        int currentCartPage = cartService.findPageNumberByChatId(chatId);
        if (!cartItems.isEmpty()) {
            CartItem cartItem = cartItems.get(currentCartPage);
            if (cartItem != null) {
                cartItems.remove(cartItem);
                cartService.deleteCartItem(chatId, cartItem.getId());
            }
        }
        if (cartItems.isEmpty()) {
            telegramService.editMessageText(new MessageEdit(chatId, messageId, "Cart cleared."));
            return;
        }
        if (cartItems.size() == currentCartPage) {
            currentCartPage -= 1;
            cartService.setPageNumber(chatId, currentCartPage);
        }
        telegramService.editMessageText(new MessageEdit(chatId, messageId,
                createProductText(cartItems.get(currentCartPage)), createCartKeyboard(cartItems, currentCartPage)));
    }

    public void doMinusProduct(Long chatId, Integer messageId) {
        List<CartItem> cartItems = cartService.findAllCartItemsByChatId(chatId);
        int currentCartPage = cartService.findPageNumberByChatId(chatId);
        if (cartItems.isEmpty()) {
            telegramService.editMessageText(new MessageEdit(chatId, messageId, "Cart is empty."));
            return;
        }
        CartItem cartItem = cartItems.get(currentCartPage);
        if (cartItem != null && cartItem.getQuantity() > 1) {
            cartItem.setQuantity(cartItem.getQuantity() - 1);
            cartService.updateCartItem(chatId, cartItem);
            telegramService.editMessageText(new MessageEdit(
                    chatId, messageId, createProductText(cartItem), createCartKeyboard(cartItems, currentCartPage)));
        }
    }

    public void doPlusProduct(Long chatId, Integer messageId) {
        List<CartItem> cartItems = cartService.findAllCartItemsByChatId(chatId);
        int currentCartPage = cartService.findPageNumberByChatId(chatId);
        if (cartItems.isEmpty()) {
            telegramService.editMessageText(new MessageEdit(chatId, messageId, "Cart is empty."));
            return;
        }
        CartItem cartItem = cartItems.get(currentCartPage);
        if (cartItem != null && cartItem.getQuantity() < MAX_QUANTITY_PER_PRODUCT) {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            cartService.updateCartItem(chatId, cartItem);
            telegramService.editMessageText(new MessageEdit(
                    chatId, messageId, createProductText(cartItem), createCartKeyboard(cartItems, currentCartPage)));
        }
    }

    public void doPreviousProduct(Long chatId, Integer messageId) {
        List<CartItem> cartItems = cartService.findAllCartItemsByChatId(chatId);
        int currentCartPage = cartService.findPageNumberByChatId(chatId);
        if (cartItems.isEmpty()) {
            telegramService.editMessageText(new MessageEdit(chatId, messageId, "Cart is empty."));
            return;
        }
        if (cartItems.size() == 1) {
            return;
        }
        if (currentCartPage <= 0) {
            currentCartPage = cartItems.size() - 1;
        } else {
            currentCartPage -= 1;
        }
        cartService.setPageNumber(chatId, currentCartPage);
        telegramService.editMessageText(new MessageEdit(chatId, messageId,
                createProductText(cartItems.get(currentCartPage)), createCartKeyboard(cartItems, currentCartPage)));
    }

    public void doNextProduct(Long chatId, Integer messageId) {
        List<CartItem> cartItems = cartService.findAllCartItemsByChatId(chatId);
        int currentCartPage = cartService.findPageNumberByChatId(chatId);
        if (cartItems.isEmpty()) {
            telegramService.editMessageText(new MessageEdit(chatId, messageId, "Cart is empty."));
            return;
        }
        if (cartItems.size() == 1) {
            return;
        }
        if (currentCartPage >= cartItems.size() - 1) {
            currentCartPage = 0;
        } else {
            currentCartPage += 1;
        }
        cartService.setPageNumber(chatId, currentCartPage);
        telegramService.editMessageText(new MessageEdit(chatId, messageId,
                createProductText(cartItems.get(currentCartPage)), createCartKeyboard(cartItems, currentCartPage)));
    }

    public void doProcessOrder(Long chatId, Integer messageId) {
        telegramService.editMessageText(new MessageEdit(chatId, messageId, "Creating order..."));
        orderStepService.revokeOrderStep(chatId);
        orderStepService.nextOrderStep(chatId);
    }

}
