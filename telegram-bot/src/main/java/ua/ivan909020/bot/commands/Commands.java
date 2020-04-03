package ua.ivan909020.bot.commands;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ua.ivan909020.bot.utils.KeyboardUtils;

import java.util.ArrayList;

public final class Commands {

    public static final String START_COMMAND = "/start";

    public static final String CATALOG_COMMAND = "\uD83D\uDCE6 Catalog";
    public static final String CART_COMMAND = "\uD83D\uDECD Cart";

    public static final String ORDER_NEXT_STEP_COMMAND = "\u2714\uFE0F Correct";
    public static final String ORDER_PREVIOUS_STEP_COMMAND = "\u25C0 Back";
    public static final String ORDER_CANCEL_COMMAND = "\u274C Cancel order";

    private Commands() {
    }

    public static ReplyKeyboardMarkup createGeneralMenuKeyboard() {
        return KeyboardUtils.create(new ArrayList<KeyboardRow>() {{
            add(new KeyboardRow() {{
                add(CATALOG_COMMAND);
                add(CART_COMMAND);
            }});
        }});
    }

}
