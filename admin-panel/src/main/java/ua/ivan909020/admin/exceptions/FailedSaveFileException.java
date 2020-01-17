package ua.ivan909020.admin.exceptions;

public class FailedSaveFileException extends RuntimeException {

    public FailedSaveFileException() {
    }

    public FailedSaveFileException(String message) {
        super(message);
    }

    public FailedSaveFileException(String message, Throwable cause) {
        super(message, cause);
    }

}
