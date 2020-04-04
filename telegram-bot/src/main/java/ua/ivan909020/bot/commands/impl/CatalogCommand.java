package ua.ivan909020.bot.commands.impl;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ua.ivan909020.bot.commands.Command;
import ua.ivan909020.bot.domain.entities.Category;
import ua.ivan909020.bot.domain.models.MessageSend;
import ua.ivan909020.bot.services.CategoryService;
import ua.ivan909020.bot.services.TelegramService;
import ua.ivan909020.bot.services.impl.CategoryServiceDefault;
import ua.ivan909020.bot.services.impl.TelegramServiceDefault;

import java.util.ArrayList;
import java.util.List;

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
        return new InlineKeyboardMarkup().setKeyboard(new ArrayList<List<InlineKeyboardButton>>() {{
            for (Category category : categoryService.findAll()) {
                add(new ArrayList<InlineKeyboardButton>() {{
                    String categoryName = category.getName();
                    add(new InlineKeyboardButton(categoryName).setSwitchInlineQueryCurrentChat(categoryName));
                }});
            }
        }});
    }

}
