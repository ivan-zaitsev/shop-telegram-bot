package ua.ivan909020.bot.exceptions;

public class SendMessageException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SendMessageException() {
    }

    public SendMessageException(String message) {
        super(message);
    }

    public SendMessageException(String message, Throwable cause) {
        super(message, cause);
    }

}
