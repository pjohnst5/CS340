package shared.model.request;

import shared.model.Player;

public class LeaveGameRequest extends IServiceRequest {

    public LeaveGameRequest(Player player)
    {
        super(player);
    }
}
