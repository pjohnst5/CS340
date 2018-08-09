package shared.exception;

public class DatabaseException extends Exception {
    public DatabaseException() {}
    public DatabaseException(String message) {
        super(message);
    }
    public DatabaseException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
//    @Override
//    public String getMessage() {}
}
