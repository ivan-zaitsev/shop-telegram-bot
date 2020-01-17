package ua.ivan909020.bot.exceptions;

public class FailedSendMessageException extends RuntimeException {

    public FailedSendMessageException() {
    }

    public FailedSendMessageException(String message) {
        super(message);
    }

    public FailedSendMessageException(String message, Throwable cause) {
        super(message, cause);
    }

}
