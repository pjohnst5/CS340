package shared.exception;

public class DatabaseException extends Exception {
    private String _message;

    public DatabaseException(String message){
        _message = message;
    }

    public String get_message(){
        return _message;
    }
}
