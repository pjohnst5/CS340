package shared.CustomExceptions;

public class InvalidPlayerException extends Exception {
    private String message;

    public InvalidPlayerException(String s)
    {
        message = s;
    }

    public String getMessage(){
        return message;
    }
}
