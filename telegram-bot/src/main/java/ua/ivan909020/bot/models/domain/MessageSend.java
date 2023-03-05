package ua.ivan909020.bot.models.domain;

import java.util.Objects;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class MessageSend {

    private final Long chatId;
    private final String text;
    private final ReplyKeyboard keyboard;

    public MessageSend(Long chatId, String text) {
        this.chatId = chatId;
        this.text = text;
        this.keyboard = null;
    }

    public MessageSend(Long chatId, String text, ReplyKeyboard keyboard) {
        this.chatId = chatId;
        this.text = text;
        this.keyboard = keyboard;
    }

    public Long getChatId() {
        return chatId;
    }

    public String getText() {
        return text;
    }

    public ReplyKeyboard getKeyboard() {
        return keyboard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageSend that = (MessageSend) o;
        return Objects.equals(chatId, that.chatId) && 
                Objects.equals(text, that.text) && 
                Objects.equals(keyboard, that.keyboard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, text, keyboard);
    }

    @Override
    public String toString() {
        return "MessageSend [chatId=" + chatId + ", text=" + text + ", keyboard=" + keyboard + "]";
    }

}
