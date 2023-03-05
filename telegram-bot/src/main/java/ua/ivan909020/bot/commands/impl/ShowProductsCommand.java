package ua.ivan909020.bot.commands.impl;

import static java.util.Arrays.asList;
import static org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton.builder;
import static ua.ivan909020.bot.models.domain.MessagePlaceholder.of;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.math.NumberUtils;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.commands.Commands;
import ua.ivan909020.bot.models.domain.CartItem;
import ua.ivan909020.bot.models.domain.InlineQuerySend;
import ua.ivan909020.bot.models.domain.MessageEdit;
import ua.ivan909020.bot.models.entities.Message;
import ua.ivan909020.bot.models.entities.Product;
import ua.ivan909020.bot.services.CartService;
import ua.ivan909020.bot.services.MessageService;
import ua.ivan909020.bot.services.ProductService;
import ua.ivan909020.bot.services.TelegramService;
import ua.ivan909020.bot.services.impl.CartServiceDefault;
import ua.ivan909020.bot.services.impl.MessageServiceCached;
import ua.ivan909020.bot.services.impl.ProductServiceDefault;
import ua.ivan909020.bot.services.impl.TelegramServiceDefault;

public class ShowProductsCommand implements Command<InlineQuery> {

    private static final ShowProductsCommand INSTANCE = new ShowProductsCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final ProductService productService = ProductServiceDefault.getInstance();
    private final CartService cartService = CartServiceDefault.getInstance();
    private final MessageService messageService = MessageServiceCached.getInstance();

    private static final int PRODUCTS_QUANTITY_PER_PAGE = 50;
    private static final int MAX_QUANTITY_PER_PRODUCT = 50;
    private static final int MAX_PRODUCTS_QUANTITY_PER_CART = 50;

    private ShowProductsCommand() {
    }

    public static ShowProductsCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(InlineQuery inlineQuery) {
        Long chatId = inlineQuery.getFrom().getId();
        String inlineQueryId = inlineQuery.getId();
        String categoryName = inlineQuery.getQuery();

        int offset = NumberUtils.toInt(inlineQuery.getOffset(), 0);
        List<Product> products = productService.findAllByCategoryName(categoryName, offset, PRODUCTS_QUANTITY_PER_PAGE);
        if (!products.isEmpty()) {
            int nextOffset = offset + PRODUCTS_QUANTITY_PER_PAGE;
            sendProductsQuery(chatId, inlineQueryId, products, nextOffset);
        }
    }

    private void sendProductsQuery(Long chatId, String inlineQueryId, List<Product> products, Integer nextOffset) {
        List<InlineQueryResult> queryResults = products.stream()
                .map(product -> buildProductArticle(chatId, product)).collect(Collectors.toList());

        telegramService.sendInlineQuery(new InlineQuerySend(inlineQueryId, queryResults, nextOffset));
    }

    private InlineQueryResultArticle buildProductArticle(Long chatId, Product product) {
        return InlineQueryResultArticle.builder()
                .id(product.getId().toString())
                .thumbUrl(product.getPhotoUrl())
                .thumbHeight(48)
                .thumbWidth(48)
                .title(product.getName())
                .description(product.getDescription())
                .replyMarkup(createProductKeyboard(chatId, product))
                .inputMessageContent(
                        InputTextMessageContent.builder()
                                .parseMode("HTML")
                                .messageText(createProductText(chatId, product))
                                .build()
                )
                .build();
    }

    private InlineKeyboardMarkup createProductKeyboard(Long chatId, Product product) {
        InlineKeyboardMarkup.InlineKeyboardMarkupBuilder keyboardBuilder = InlineKeyboardMarkup.builder();

        CartItem cartItem = cartService.findCartItemByChatIdAndProductId(chatId, product.getId());

        if (cartItem != null) {
            keyboardBuilder.keyboardRow(asList(
                    builder().text("\u2796").callbackData("show-products=minus-product_" + product.getId()).build(),
                    builder().text(cartItem.getQuantity() + " pcs.").callbackData("show-products=quantity-products").build(),
                    builder().text("\u2795").callbackData("show-products=plus-product_" + product.getId()).build()
            ));
            keyboardBuilder.keyboardRow(asList(
                    builder().text(Commands.CATALOG_COMMAND).callbackData("show-products=open-catalog").build(),
                    builder().text(Commands.CART_COMMAND).callbackData("show-products=open-cart").build()
            ));
        } else {
            keyboardBuilder.keyboardRow(asList(
                    builder()
                            .text(String.format("\uD83D\uDCB5 Price: %d $ \uD83D\uDECD Add to cart", product.getPrice()))
                            .callbackData("show-products=plus-product_" + product.getId())
                            .build()
            ));
        }

        return keyboardBuilder.build();
    }

    private String createProductText(Long chatId, Product product) {
        Message message = messageService.findByName("PRODUCT_MESSAGE");

        message.applyPlaceholder(of("%PRODUCT_PHOTO_URL%", product.getPhotoUrl()));
        message.applyPlaceholder(of("%PRODUCT_NAME%", product.getName()));
        message.applyPlaceholder(of("%PRODUCT_DESCRIPTION%", product.getDescription()));

        CartItem cartItem = cartService.findCartItemByChatIdAndProductId(chatId, product.getId());

        if (cartItem == null) {
            message.removeTextBetweenPlaceholder("%PRODUCT_PRICES%");
        } else {
            message.applyPlaceholder(of("%PRODUCT_PRICE%", product.getPrice()));
            message.applyPlaceholder(of("%PRODUCT_QUANTITY%", cartItem.getQuantity()));
            message.applyPlaceholder(of("%PRODUCT_TOTAL_PRICE%", product.getPrice() * cartItem.getQuantity()));
        }

        return message.buildText();
    }

    public void doPlusProduct(Long chatId, String inlineMessageId, String data) {
        Integer productId = Integer.parseInt(data.split("_")[1]);
        CartItem cartItem = cartService.findCartItemByChatIdAndProductId(chatId, productId);
        Product product = cartItem != null ? cartItem.getProduct() : productService.findById(productId);

        if (product == null) {
            return;
        }

        if (cartItem != null) {
            if (cartItem.getQuantity() < MAX_QUANTITY_PER_PRODUCT) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartService.updateCartItem(chatId, cartItem);
            }
        } else {
            if (cartService.findAllCartItemsByChatId(chatId).size() < MAX_PRODUCTS_QUANTITY_PER_CART) {
                cartService.saveCartItem(chatId, new CartItem(product, 1));
            }
        }

        telegramService.editMessageText(new MessageEdit(
                inlineMessageId, createProductText(chatId, product), createProductKeyboard(chatId, product)));
    }

    public void doMinusProduct(Long chatId, String inlineMessageId, String data) {
        Integer productId = Integer.parseInt(data.split("_")[1]);
        CartItem cartItem = cartService.findCartItemByChatIdAndProductId(chatId, productId);
        Product product = cartItem != null ? cartItem.getProduct() : productService.findById(productId);

        if (product == null) {
            return;
        }

        if (cartItem != null) {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cartService.updateCartItem(chatId, cartItem);
            } else {
                cartService.deleteCartItem(chatId, cartItem.getId());
            }
        }

        telegramService.editMessageText(new MessageEdit(
                inlineMessageId, createProductText(chatId, product), createProductKeyboard(chatId, product)));
    }

}
