package ua.ivan909020.bot.exceptions;

public class OrderStepStateException extends RuntimeException {

    public OrderStepStateException() {
    }

    public OrderStepStateException(String message) {
        super(message);
    }

    public OrderStepStateException(String message, Throwable cause) {
        super(message, cause);
    }

}
