package shared.CustomExceptions;

public class PlayerException extends Exception {
    private String message;

    public PlayerException(String s)
    {
        message = s;
    }

    public String getMessage(){
        return message;
    }
}
