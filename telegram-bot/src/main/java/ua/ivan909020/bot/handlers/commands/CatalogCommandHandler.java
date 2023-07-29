package ua.ivan909020.bot.handlers.commands;

import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.math.NumberUtils;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import ua.ivan909020.bot.handlers.CommandHandler;
import ua.ivan909020.bot.handlers.UpdateHandler;
import ua.ivan909020.bot.handlers.commands.registries.CommandHandlerRegistry;
import ua.ivan909020.bot.models.domain.Button;
import ua.ivan909020.bot.models.domain.CartItem;
import ua.ivan909020.bot.models.domain.Command;
import ua.ivan909020.bot.models.domain.MessagePlaceholder;
import ua.ivan909020.bot.models.entities.Category;
import ua.ivan909020.bot.models.entities.Message;
import ua.ivan909020.bot.models.entities.Product;
import ua.ivan909020.bot.repositories.CartRepository;
import ua.ivan909020.bot.repositories.CategoryRepository;
import ua.ivan909020.bot.repositories.ProductRepository;
import ua.ivan909020.bot.services.MessageService;

public class CatalogCommandHandler implements CommandHandler, UpdateHandler {

    private static final int PRODUCTS_QUANTITY_PER_PAGE = 50;
    private static final int MAX_QUANTITY_PER_PRODUCT = 50;
    private static final int MAX_PRODUCTS_QUANTITY_PER_CART = 50;

    private static final String PRODUCT_QUANTITY_CALLBACK = "catalog=product-quantity";
    private static final String MINUS_PRODUCT_CALLBACK = "catalog=minus-product_";
    private static final String PLUS_PRODUCT_CALLBACK = "catalog=plus-product_";
    private static final String OPEN_CATALOG_CALLBACK = "catalog=open-catalog";
    private static final String OPEN_CART_CALLBACK = "catalog=open-cart";

    private static final Set<String> CALLBACKS = Set.of(MINUS_PRODUCT_CALLBACK, PLUS_PRODUCT_CALLBACK,
            OPEN_CATALOG_CALLBACK, OPEN_CART_CALLBACK);

    private final CommandHandlerRegistry commandHandlerRegistry;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final MessageService messageService;

    public CatalogCommandHandler(
            CommandHandlerRegistry commandHandlerRegistry,
            CategoryRepository categoryRepository,
            ProductRepository productRepository,
            CartRepository cartRepository,
            MessageService messageService) {

        this.commandHandlerRegistry = commandHandlerRegistry;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.messageService = messageService;
    }

    @Override
    public Command getCommand() {
        return Command.CATALOG;
    }

    @Override
    public void executeCommand(AbsSender absSender, Update update, Long chatId) throws TelegramApiException {
        handleCatalogMessageUpdate(absSender, chatId);
    }

    @Override
    public boolean canHandleUpdate(Update update) {
        return isCatalogMessageUpdate(update) || isInlineQueryUpdate(update) || isCallbackQueryUpdate(update);
    }

    @Override
    public void handleUpdate(AbsSender absSender, Update update) throws TelegramApiException {
        if (isCatalogMessageUpdate(update)) {
            handleCatalogMessageUpdate(absSender, update.getMessage().getChatId());
        }
        if (isInlineQueryUpdate(update)) {
            handleInlineQueryUpdate(absSender, update);
        }
        if (isCallbackQueryUpdate(update)) {
            handleCallbackQueryUpdate(absSender, update);
        }
    }

    private boolean isCatalogMessageUpdate(Update update) {
        return update.hasMessage() &&
                update.getMessage().hasText() &&
                update.getMessage().getText().equals(Button.CATALOG.getAlias());
    }

    private boolean isInlineQueryUpdate(Update update) {
        return update.hasInlineQuery();
    }

    private boolean isCallbackQueryUpdate(Update update) {
        return update.hasCallbackQuery() && 
                CALLBACKS.stream().anyMatch(callback -> update.getCallbackQuery().getData().startsWith(callback));
    }

    private void handleCatalogMessageUpdate(AbsSender absSender, Long chatId) throws TelegramApiException {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Choose a category:")
                .replyMarkup(createCategoriesKeyboard())
                .build();
        absSender.execute(message);
    }

    private InlineKeyboardMarkup createCategoriesKeyboard() {
        InlineKeyboardMarkup.InlineKeyboardMarkupBuilder keyboardBuilder = InlineKeyboardMarkup.builder();

        for (Category category : categoryRepository.findAll()) {
            String categoryName = category.getName();

            keyboardBuilder.keyboardRow(Arrays.asList(
                    InlineKeyboardButton.builder().text(categoryName).switchInlineQueryCurrentChat(categoryName).build()
                    ));
        }

        return keyboardBuilder.build();
    }

    private void handleInlineQueryUpdate(AbsSender absSender, Update update) throws TelegramApiException {
        InlineQuery inlineQuery = update.getInlineQuery();
        String query = inlineQuery.getQuery();

        int offset = NumberUtils.toInt(inlineQuery.getOffset(), 0);
        List<Product> products = productRepository.findAllByCategoryName(query, offset, PRODUCTS_QUANTITY_PER_PAGE);
        if (!products.isEmpty()) {
            int nextOffset = offset + PRODUCTS_QUANTITY_PER_PAGE;
            sendCatalogQueryAnswer(absSender, inlineQuery, products, nextOffset);
        }
    }

    private void sendCatalogQueryAnswer(AbsSender absSender, InlineQuery inlineQuery, List<Product> products,
            Integer nextOffset) throws TelegramApiException {

        Long chatId = inlineQuery.getFrom().getId();

        List<InlineQueryResult> queryResults = products.stream()
                .map(product -> buildProductArticle(chatId, product)).collect(toList());

        AnswerInlineQuery answerInlineQuery = AnswerInlineQuery.builder()
                .inlineQueryId(inlineQuery.getId())
                .results(queryResults)
                .nextOffset(Integer.toString(nextOffset))
                .cacheTime(1)
                .build();
        absSender.execute(answerInlineQuery);
    }

    private InlineQueryResultArticle buildProductArticle(Long chatId, Product product) {
        return InlineQueryResultArticle.builder()
                .id(product.getId().toString())
                .thumbnailUrl(product.getPhotoUrl())
                .thumbnailHeight(48)
                .thumbnailHeight(48)
                .title(product.getName())
                .description(product.getDescription())
                .replyMarkup(createProductKeyboard(chatId, product))
                .inputMessageContent(
                        InputTextMessageContent.builder()
                            .parseMode("HTML").messageText(createProductText(chatId, product)).build()
                        )
                .build();
    }

    private void handleCallbackQueryUpdate(AbsSender absSender, Update update) throws TelegramApiException {
        CallbackQuery callbackQuery = update.getCallbackQuery();
        Long chatId = callbackQuery.getFrom().getId();
        String inlineMessageId = callbackQuery.getInlineMessageId();
        String data = callbackQuery.getData();

        if (data.startsWith(MINUS_PRODUCT_CALLBACK)) {
            doMinusProduct(absSender, chatId, inlineMessageId, data);
        }
        if (data.startsWith(PLUS_PRODUCT_CALLBACK)) {
            doPlusProduct(absSender, chatId, inlineMessageId, data);
        }
        if (data.equals(OPEN_CATALOG_CALLBACK)) {
            commandHandlerRegistry.find(Command.CATALOG).executeCommand(absSender, update, chatId);
        }
        if (data.equals(OPEN_CART_CALLBACK)) {
            commandHandlerRegistry.find(Command.CART).executeCommand(absSender, update, chatId);
        }
    }

    private void doPlusProduct(AbsSender absSender, Long chatId, String inlineMessageId, String data)
            throws TelegramApiException {

        Integer productId = Integer.parseInt(data.split("_")[1]);
        CartItem cartItem = cartRepository.findCartItemByChatIdAndProductId(chatId, productId);
        Product product = cartItem != null ? cartItem.getProduct() : productRepository.findById(productId);

        if (product == null) {
            return;
        }

        if (cartItem != null) {
            if (cartItem.getQuantity() < MAX_QUANTITY_PER_PRODUCT) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                cartRepository.updateCartItem(chatId, cartItem);
            }
        } else {
            if (cartRepository.findAllCartItemsByChatId(chatId).size() < MAX_PRODUCTS_QUANTITY_PER_CART) {
                cartRepository.saveCartItem(chatId, new CartItem(product, 1));
            }
        }

        editProductMessage(absSender, chatId, inlineMessageId, product);
    }

    private void doMinusProduct(AbsSender absSender, Long chatId, String inlineMessageId, String data)
            throws TelegramApiException {

        Integer productId = Integer.parseInt(data.split("_")[1]);
        CartItem cartItem = cartRepository.findCartItemByChatIdAndProductId(chatId, productId);
        Product product = cartItem != null ? cartItem.getProduct() : productRepository.findById(productId);

        if (product == null) {
            return;
        }

        if (cartItem != null) {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                cartRepository.updateCartItem(chatId, cartItem);
            } else {
                cartRepository.deleteCartItem(chatId, cartItem.getId());
            }
        }

        editProductMessage(absSender, chatId, inlineMessageId, product);
    }

    private void editProductMessage(AbsSender absSender, Long chatId, String inlineMessageId, Product product)
            throws TelegramApiException {

        EditMessageText message = EditMessageText.builder()
                .inlineMessageId(inlineMessageId)
                .text(createProductText(chatId, product))
                .replyMarkup(createProductKeyboard(chatId, product))
                .parseMode("HTML")
                .build();
        absSender.execute(message);
    }

    private String createProductText(Long chatId, Product product) {
        Message message = messageService.findByName("PRODUCT_MESSAGE");

        message.applyPlaceholder(MessagePlaceholder.of("%PRODUCT_PHOTO_URL%", product.getPhotoUrl()));
        message.applyPlaceholder(MessagePlaceholder.of("%PRODUCT_NAME%", product.getName()));
        message.applyPlaceholder(MessagePlaceholder.of("%PRODUCT_DESCRIPTION%", product.getDescription()));

        CartItem cartItem = cartRepository.findCartItemByChatIdAndProductId(chatId, product.getId());

        if (cartItem == null) {
            message.removeTextBetweenPlaceholder("%PRODUCT_PRICES%");
        } else {
            message.applyPlaceholder(MessagePlaceholder.of("%PRODUCT_PRICE%", product.getPrice()));
            message.applyPlaceholder(MessagePlaceholder.of("%PRODUCT_QUANTITY%", cartItem.getQuantity()));
            message.applyPlaceholder(
                    MessagePlaceholder.of("%PRODUCT_TOTAL_PRICE%", product.getPrice() * cartItem.getQuantity()));
        }

        return message.buildText();
    }

    private InlineKeyboardMarkup createProductKeyboard(Long chatId, Product product) {
        InlineKeyboardMarkup.InlineKeyboardMarkupBuilder keyboardBuilder = InlineKeyboardMarkup.builder();

        CartItem cartItem = cartRepository.findCartItemByChatIdAndProductId(chatId, product.getId());

        if (cartItem != null) {
            keyboardBuilder.keyboardRow(Arrays.asList(
                    InlineKeyboardButton.builder()
                        .text("\u2796").callbackData(MINUS_PRODUCT_CALLBACK + product.getId()).build(),
                    InlineKeyboardButton.builder()
                        .text(cartItem.getQuantity() + " pcs.").callbackData(PRODUCT_QUANTITY_CALLBACK).build(),
                    InlineKeyboardButton.builder()
                        .text("\u2795").callbackData(PLUS_PRODUCT_CALLBACK + product.getId()).build()
                    ));
            keyboardBuilder.keyboardRow(Arrays.asList(
                    InlineKeyboardButton.builder()
                        .text(Button.CATALOG.getAlias()).callbackData(OPEN_CATALOG_CALLBACK).build(),
                    InlineKeyboardButton.builder()
                        .text(Button.CART.getAlias()).callbackData(OPEN_CART_CALLBACK).build()
                    ));
        } else {
            keyboardBuilder.keyboardRow(Arrays.asList(
                    InlineKeyboardButton.builder()
                        .text(String.format("\uD83D\uDCB5 Price: %d $ \uD83D\uDECD Add to cart", product.getPrice()))
                        .callbackData(PLUS_PRODUCT_CALLBACK + product.getId()).build()
                    ));
        }

        return keyboardBuilder.build();
    }

}
