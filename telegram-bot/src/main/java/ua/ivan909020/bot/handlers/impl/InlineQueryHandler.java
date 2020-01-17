package ua.ivan909020.bot.handlers.impl;

import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import ua.ivan909020.bot.commands.impl.ShowProductsCommand;
import ua.ivan909020.bot.handlers.Handler;

class InlineQueryHandler implements Handler<InlineQuery> {

    private final ShowProductsCommand showProductsCommand = ShowProductsCommand.getInstance();

    @Override
    public void handle(InlineQuery inlineQuery) {
        if (inlineQuery.hasQuery()) {
            showProductsCommand.execute(inlineQuery);
        }
    }

}
