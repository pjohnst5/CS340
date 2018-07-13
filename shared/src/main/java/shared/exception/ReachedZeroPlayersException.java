package shared.exception;

public class ReachedZeroPlayersException extends Exception {
    public String getMessage()
    {
        return "You've reached zero players in the game";
    }
}
