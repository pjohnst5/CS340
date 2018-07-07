public class MaxPlayersException extends Exception {
    private String message;

    public MaxPlayersException(int max)
    {
        message = new String("Maximum players added, cannot go over " + max);
    }

    public String getMessage(){
        return message;
    }
}
