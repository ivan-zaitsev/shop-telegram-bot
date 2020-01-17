package ua.ivan909020.bot.utils;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public final class KeyboardUtils {

    private KeyboardUtils() {
    }

    public static ReplyKeyboardMarkup create(List<KeyboardRow> rows) {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setKeyboard(rows);
        keyboardMarkup.setOneTimeKeyboard(false);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setSelective(true);
        return keyboardMarkup;
    }

}
