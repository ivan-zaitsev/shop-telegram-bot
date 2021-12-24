package ua.ivan909020.bot.commands.impl;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.domain.entities.Category;
import ua.ivan909020.bot.domain.models.MessageSend;
import ua.ivan909020.bot.services.CategoryService;
import ua.ivan909020.bot.services.TelegramService;
import ua.ivan909020.bot.services.impl.CategoryServiceDefault;
import ua.ivan909020.bot.services.impl.TelegramServiceDefault;

import java.util.Arrays;

import static org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton.builder;

public class CatalogCommand implements Command<Long> {

    private static final CatalogCommand INSTANCE = new CatalogCommand();

    private final TelegramService telegramService = TelegramServiceDefault.getInstance();
    private final CategoryService categoryService = CategoryServiceDefault.getInstance();

    private CatalogCommand() {
    }

    public static CatalogCommand getInstance() {
        return INSTANCE;
    }

    @Override
    public void execute(Long chatId) {
        telegramService.sendMessage(new MessageSend(chatId, "Choose a category:", createCategoriesKeyboard()));
    }

    private InlineKeyboardMarkup createCategoriesKeyboard() {
        InlineKeyboardMarkup.InlineKeyboardMarkupBuilder keyboardBuilder = InlineKeyboardMarkup.builder();

        for (Category category : categoryService.findAll()) {
            String categoryName = category.getName();

            keyboardBuilder.keyboardRow(Arrays.asList(
                    builder().text(categoryName).switchInlineQueryCurrentChat(categoryName).build()
            ));
        }

        return keyboardBuilder.build();
    }

}
