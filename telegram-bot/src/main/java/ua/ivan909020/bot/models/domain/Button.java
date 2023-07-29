package ua.ivan909020.bot.models.domain;

import static org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton.builder;

import java.util.Arrays;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

public enum Button {

    START("/start"),
    CATALOG("\uD83D\uDCE6 Catalog"),
    CART("\uD83D\uDECD Cart"),
    SEND_PHONE_NUMBER("\uD83D\uDCF1 Send Phone Number"),
    ORDER_STEP_NEXT("\u2714\uFE0F Correct"),
    ORDER_STEP_PREVIOUS("\u25C0 Back"),
    ORDER_STEP_CANCEL("\u274C Cancel order"),
    ORDER_CONFIRM("\u2705 Confirm");

    private final String alias;

    Button(String alias) {
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public static ReplyKeyboardMarkup createGeneralMenuKeyboard() {
        ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder keyboardBuilder = ReplyKeyboardMarkup.builder();
        keyboardBuilder.resizeKeyboard(true);
        keyboardBuilder.selective(true);

        keyboardBuilder.keyboardRow(new KeyboardRow(Arrays.asList(
                builder().text(CATALOG.getAlias()).build(),
                builder().text(CART.getAlias()).build()
                )));

        return keyboardBuilder.build();
    }

}
