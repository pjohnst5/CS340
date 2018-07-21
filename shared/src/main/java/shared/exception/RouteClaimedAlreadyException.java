package shared.exception;

public class RouteClaimedAlreadyException extends Exception {
    private String message;

    public RouteClaimedAlreadyException()
    {
        message = "This route has been claimed already";
    }

    public String getMessage()
    {
        return message;
    }
}
