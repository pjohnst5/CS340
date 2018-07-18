package shared.exception;

public class DeckException extends  Exception {

    private String _message;

    public DeckException(String message) {
        _message = message;
    }

    public String get_message() {
        return _message;
    }
}
