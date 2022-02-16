package ua.ivan909020.admin.exceptions;

public class SaveFileException extends RuntimeException {

    public SaveFileException() {
    }

    public SaveFileException(String message) {
        super(message);
    }

    public SaveFileException(String message, Throwable cause) {
        super(message, cause);
    }

}
