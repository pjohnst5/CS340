package shared.exception;

public class InvalidUserException extends Exception {
    public String getMessage()
    {
        return "User must have a username and password";
    }
}
