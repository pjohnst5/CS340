package shared.exception;

public class NotEnoughTrainCarsException extends Exception {
    private String message;

    public NotEnoughTrainCarsException()
    {
        message = "Not enough Train Cars to claim route";
    }

    public String getMessage()
    {
        return message;
    }
}
