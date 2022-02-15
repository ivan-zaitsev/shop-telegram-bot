package ua.ivan909020.bot.models.domain;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.Objects;

public class MessageEdit {

    private final Long chatId;
    private final Integer messageId;
    private final String inlineMessageId;
    private final String text;
    private final InlineKeyboardMarkup keyboard;

    public MessageEdit(Long chatId, Integer messageId, String text) {
        this.chatId = chatId;
        this.messageId = messageId;
        this.inlineMessageId = null;
        this.text = text;
        this.keyboard = null;
    }

    public MessageEdit(Long chatId, Integer messageId, String text, InlineKeyboardMarkup keyboard) {
        this.chatId = chatId;
        this.messageId = messageId;
        this.inlineMessageId = null;
        this.text = text;
        this.keyboard = keyboard;
    }

    public MessageEdit(String inlineMessageId, String text, InlineKeyboardMarkup keyboard) {
        this.chatId = null;
        this.messageId = null;
        this.inlineMessageId = inlineMessageId;
        this.text = text;
        this.keyboard = keyboard;
    }

    public Long getChatId() {
        return chatId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public String getInlineMessageId() {
        return inlineMessageId;
    }

    public String getText() {
        return text;
    }

    public InlineKeyboardMarkup getKeyboard() {
        return keyboard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageEdit that = (MessageEdit) o;
        return Objects.equals(chatId, that.chatId) &&
                Objects.equals(messageId, that.messageId) &&
                Objects.equals(inlineMessageId, that.inlineMessageId) &&
                Objects.equals(text, that.text) &&
                Objects.equals(keyboard, that.keyboard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, messageId, inlineMessageId, text, keyboard);
    }

    @Override
    public String toString() {
        return "MessageEdit{" +
                "chatId=" + chatId +
                ", messageId=" + messageId +
                ", inlineMessageId=" + inlineMessageId +
                ", text='" + text + '\'' +
                ", keyboard=" + keyboard +
                '}';
    }

}
