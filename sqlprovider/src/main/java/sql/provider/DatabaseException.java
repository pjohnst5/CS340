package sql.provider;

public class DatabaseException extends Exception {
    public DatabaseException() {}
    public DatabaseException(String message) {
        super(message);
    }
    public DatabaseException(String message, Throwable cause) {
        super(message);
        initCause(cause);
    }
}
