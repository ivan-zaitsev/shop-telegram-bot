package ua.ivan909020.bot.exceptions;

public class SendMessageException extends RuntimeException {

    public SendMessageException() {
    }

    public SendMessageException(String message) {
        super(message);
    }

    public SendMessageException(String message, Throwable cause) {
        super(message, cause);
    }

}
