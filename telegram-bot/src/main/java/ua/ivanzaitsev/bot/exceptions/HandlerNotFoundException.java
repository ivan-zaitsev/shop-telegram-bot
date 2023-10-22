package ua.ivanzaitsev.bot.exceptions;

import java.io.Serial;

public class HandlerNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public HandlerNotFoundException() {
    }

    public HandlerNotFoundException(String message) {
        super(message);
    }

    public HandlerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
