package shared.exception;

public class InvalidGameRequestException extends Exception {
    public String getMessage()
    {
        return "Invalid game request parameters, nothing can be null and max players must between 2 and 5 inclusive.";
    }
}
