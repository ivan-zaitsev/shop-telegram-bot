package ua.ivan909020.bot.handlers.impl;

import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import ua.ivan909020.bot.commands.impl.CartCommand;
import ua.ivan909020.bot.commands.impl.CatalogCommand;
import ua.ivan909020.bot.commands.impl.ShowProductsCommand;
import ua.ivan909020.bot.handlers.Handler;

class CallbackHandler implements Handler {

    private final ShowProductsCommand showProductsCommand = ShowProductsCommand.getInstance();
    private final CatalogCommand catalogCommand = CatalogCommand.getInstance();
    private final CartCommand cartCommand = CartCommand.getInstance();

    @Override
    public boolean supports(Update update) {
        return update.hasCallbackQuery();
    }

    @Override
    public void handle(Update update) {
        CallbackQuery callbackQuery = update.getCallbackQuery();

        if (callbackQuery.getMessage() == null) {
            handleInline(callbackQuery);
        } else {
            handleMessage(callbackQuery);
        }
    }

    private void handleInline(CallbackQuery callbackQuery) {
        Long chatId = callbackQuery.getFrom().getId();
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
            cartCommand.doDeleteProduct(chatId, messageId);
        } else if (data.equals("cart=minus-product")) {
            cartCommand.doMinusProduct(chatId, messageId);
        } else if (data.equals("cart=plus-product")) {
            cartCommand.doPlusProduct(chatId, messageId);
        } else if (data.equals("cart=previous-product")) {
            cartCommand.doPreviousProduct(chatId, messageId);
        } else if (data.equals("cart=next-product")) {
            cartCommand.doNextProduct(chatId, messageId);
        } else if (data.equals("cart=process-order")) {
            cartCommand.doProcessOrder(chatId, messageId);
        }
    }

}
