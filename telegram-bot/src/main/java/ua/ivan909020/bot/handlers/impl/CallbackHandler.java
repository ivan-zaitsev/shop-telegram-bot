package ua.ivan909020.bot.handlers.impl;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ua.ivan909020.bot.commands.impl.CartCommand;
import ua.ivan909020.bot.commands.impl.CatalogCommand;
import ua.ivan909020.bot.commands.impl.ShowProductsCommand;
import ua.ivan909020.bot.handlers.Handler;

class CallbackHandler implements Handler<CallbackQuery> {

    private final ShowProductsCommand showProductsCommand = ShowProductsCommand.getInstance();
    private final CatalogCommand catalogCommand = CatalogCommand.getInstance();
    private final CartCommand cartCommand = CartCommand.getInstance();

    @Override
    public void handle(CallbackQuery callbackQuery) {
        if (callbackQuery.getMessage() == null) {
            handleInline(callbackQuery);
        } else {
            handleMessage(callbackQuery);
        }
    }

    private void handleInline(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getFrom().getId().longValue();
        String inlineMessageId = callbackQuery.getInlineMessageId();
        String data = callbackQuery.getData();

        if (data.startsWith("show-products=plus-product")) {
            showProductsCommand.doPlusProduct(chatId, inlineMessageId, data);
        } else if (data.startsWith("show-products=minus-product")) {
            showProductsCommand.doMinusProduct(chatId, inlineMessageId, data);
        } else if (data.equals("show-products=open-catalog")) {
            catalogCommand.execute(chatId);
        } else if (data.equals("show-products=open-cart")) {
            cartCommand.execute(chatId);
        }
    }

    private void handleMessage(CallbackQuery callbackQuery) {
        Message message = callbackQuery.getMessage();
        Long chatId = message.getChatId();
        Integer messageId = message.getMessageId();
        String data = callbackQuery.getData();

        if (data.equals("cart=delete-product")) {
            cartCommand.deleteProduct(chatId, messageId);
        } else if (data.equals("cart=minus-product")) {
            cartCommand.minusProduct(chatId, messageId);
        } else if (data.equals("cart=plus-product")) {
            cartCommand.plusProduct(chatId, messageId);
        } else if (data.equals("cart=previous-product")) {
            cartCommand.previousProduct(chatId, messageId);
        } else if (data.equals("cart=next-product")) {
            cartCommand.nextProduct(chatId, messageId);
        } else if (data.equals("cart=continue")) {
            cartCommand.doContinue(chatId, messageId);
        }
    }

}
