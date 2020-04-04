package ua.ivan909020.bot.commands.impl;

import com.mchange.lang.IntegerUtils;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.domain.entities.Product;
import ua.ivan909020.bot.domain.models.CartItem;
import ua.ivan909020.bot.domain.models.InlineQuerySend;
import ua.ivan909020.bot.domain.models.MessageEdit;
import ua.ivan909020.bot.services.CartService;
import ua.ivan909020.bot.services.ProductService;
import ua.ivan909020.bot.services.TelegramService;
import ua.ivan909020.bot.services.impl.CartServiceDefault;
import ua.ivan909020.bot.services.impl.ProductServiceDefault;
import ua.ivan909020.bot.services.impl.TelegramServiceDefault;

import java.util.ArrayList;
import java.util.List;

public class ShowProductsCommand implements Command {

    private static final ShowProductsCommand INSTANCE = new ShowProductsCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final ProductService productService = ProductServiceDefault.getInstance();
    private final CartService cartService = CartServiceDefault.getInstance();

    private final static int PRODUCTS_QUANTITY_PER_PAGE = 50;
    private final static int MAX_QUANTITY_PER_PRODUCT = 50;

    private ShowProductsCommand() {
    }

    public static ShowProductsCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(Long chatId) {
    }

    public void execute(InlineQuery inlineQuery) {
        Long chatId = inlineQuery.getFrom().getId().longValue();
        String inlineQueryId = inlineQuery.getId();
        String categoryName = inlineQuery.getQuery();

        int offset = IntegerUtils.parseInt(inlineQuery.getOffset(), 0);
        List<Product> products = productService.findAllByCategoryName(categoryName, offset, PRODUCTS_QUANTITY_PER_PAGE);
        if (!products.isEmpty()) {
            int nextOffset = offset + PRODUCTS_QUANTITY_PER_PAGE;
            sendProductsQuery(chatId, inlineQueryId, products, nextOffset);
        }
    }

    private void sendProductsQuery(Long chatId, String inlineQueryId, List<Product> products, Integer nextOffset) {
        telegramService.sendInlineQuery(
                new InlineQuerySend(inlineQueryId, createProductsInlineQuery(chatId, products), nextOffset));
    }

    private List<InlineQueryResult> createProductsInlineQuery(Long chatId, List<Product> products) {
        List<InlineQueryResult> productsResult = new ArrayList<>();
        for (Product product : products) {
            productsResult.add(new InlineQueryResultArticle().setId(product.getId().toString())
                    .setThumbUrl(product.getPhotoUrl()).setThumbHeight(48).setThumbWidth(48)
                    .setTitle(product.getName()).setDescription(product.getDescription())
                    .setReplyMarkup(createProductKeyboard(chatId, product))
                    .setInputMessageContent(new InputTextMessageContent()
                            .setParseMode("HTML").setMessageText(createProductText(chatId, product))));
        }
        return productsResult;
    }

    private InlineKeyboardMarkup createProductKeyboard(Long chatId, Product product) {
        List<List<InlineKeyboardButton>> rows = new ArrayList<List<InlineKeyboardButton>>() {{
            CartItem cartItem = cartService.findCartItemByChatIdAndProductId(chatId, product.getId());
            if (cartItem != null) {
                add(new ArrayList<InlineKeyboardButton>() {{
                    add(new InlineKeyboardButton("\u2796")
                            .setCallbackData("show-products=minus-product_" + product.getId()));
                    add(new InlineKeyboardButton(cartItem.getQuantity() + " pcs.")
                            .setCallbackData("show-products=quantity-products"));
                    add(new InlineKeyboardButton("\u2795")
                            .setCallbackData("show-products=plus-product_" + product.getId()));
                }});
                add(new ArrayList<InlineKeyboardButton>() {{
                    add(new InlineKeyboardButton("\ud83c\udf7d Menu").setCallbackData("show-products=open-menu"));
                    add(new InlineKeyboardButton("\ud83d\udecd Cart").setCallbackData("show-products=open-cart"));
                }});
            } else {
                add(new ArrayList<InlineKeyboardButton>() {{
                    add(new InlineKeyboardButton(
                            String.format("\uD83D\uDCB5 Price: %.2f $ \uD83D\uDECD Add to cart", product.getPrice()))
                            .setCallbackData("show-products=plus-product_" + product.getId()));
                }});
            }
        }};
        return new InlineKeyboardMarkup().setKeyboard(rows);
    }

    private String createProductText(Long chatId, Product product) {
        StringBuilder description = new StringBuilder();
        description.append("<b>Name</b>: ").append("<a href=\"").append(product.getPhotoUrl()).append("\">")
                .append(product.getName()).append("</a>\n");
        description.append("<b>Description</b>: ").append(product.getDescription()).append("\n\n");

        CartItem cartItem = cartService.findCartItemByChatIdAndProductId(chatId, product.getId());
        if (cartItem != null) {
            description.append("Price, quantity, total:\n");
            description.append(product.getPrice()).append(" $ * ").append(cartItem.getQuantity())
                    .append(" pcs. = ").append(product.getPrice() * cartItem.getQuantity()).append(" $");
        }
        return description.toString();
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
            cartService.saveCartItem(chatId, new CartItem(product, 1));
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
