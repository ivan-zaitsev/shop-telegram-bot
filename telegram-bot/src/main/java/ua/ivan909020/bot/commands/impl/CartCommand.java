package ua.ivan909020.bot.commands.impl;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
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

import java.util.List;

import static java.util.Arrays.asList;
import static org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton.builder;
import static ua.ivan909020.bot.domain.models.MessagePlaceholder.of;

public class CartCommand implements Command<Long> {

    private static final CartCommand INSTANCE = new CartCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final CartService cartService = CartServiceDefault.getInstance();
    private final OrderStepService orderStepService = OrderStepServiceDefault.getInstance();
    private final MessageService messageService = MessageServiceCached.getInstance();

    private static final int MAX_QUANTITY_PER_PRODUCT = 50;

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
        return message.buildText();
    }

    private InlineKeyboardMarkup createCartKeyboard(List<CartItem> cartItems, int currentCartPage) {
        InlineKeyboardMarkup.InlineKeyboardMarkupBuilder keyboardBuilder = InlineKeyboardMarkup.builder();

        keyboardBuilder.keyboardRow(asList(
                builder().text("\u2716").callbackData("cart=delete-product").build(),
                builder().text("\u2796").callbackData("cart=minus-product").build(),
                builder().text(cartItems.get(currentCartPage).getQuantity() + " pcs.").callbackData("cart=product-quantity").build(),
                builder().text("\u2795").callbackData("cart=plus-product").build()
        ));

        keyboardBuilder.keyboardRow(asList(
                builder().text("\u25c0").callbackData("cart=previous-product").build(),
                builder().text((currentCartPage + 1) + "/" + cartItems.size()).callbackData("cart=current-page").build(),
                builder().text("\u25b6").callbackData("cart=next-product").build()
        ));

        keyboardBuilder.keyboardRow(asList(
                builder()
                        .text(String.format("\u2705 Order for %.2f $ Checkout?", cartService.calculateTotalPrice(cartItems)))
                        .callbackData("cart=process-order")
                        .build()
        ));

        return keyboardBuilder.build();
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
