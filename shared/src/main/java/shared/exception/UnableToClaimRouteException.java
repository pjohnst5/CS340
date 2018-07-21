package shared.exception;

public class UnableToClaimRouteException extends Exception {
    private String _message;

    public UnableToClaimRouteException(String message){
        this._message = message;
    }

    public String getMessage()
    {
        return _message;
    }
}
