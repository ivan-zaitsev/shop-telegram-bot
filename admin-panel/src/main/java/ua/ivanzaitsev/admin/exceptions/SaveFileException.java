package ua.ivanzaitsev.admin.exceptions;

public class SaveFileException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SaveFileException() {
    }

    public SaveFileException(String message) {
        super(message);
    }

    public SaveFileException(String message, Throwable cause) {
        super(message, cause);
    }

}
