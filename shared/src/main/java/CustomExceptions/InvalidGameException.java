package CustomExceptions;

public class InvalidGameException extends Exception {
    private String message;

    public InvalidGameException(String s)
    {
        message = s;
    }

    public String getMessage()
    {
        return message;
    }
}
