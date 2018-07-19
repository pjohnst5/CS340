package shared.model.request;

public class LeaveGameRequest extends IServiceRequest {

    public LeaveGameRequest(String gameID, String playerID)
    {
        super(gameID,playerID);
    }
}
