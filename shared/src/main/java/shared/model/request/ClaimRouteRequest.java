package shared.model.request;

import java.util.List;

import shared.model.GameMap;
import shared.model.Player;
import shared.model.Route;
import shared.model.decks.TrainCard;
import shared.model.decks.TrainDeck;

public class ClaimRouteRequest extends IServiceRequest {

    private Route _route;
    private List<TrainCard> _discarded;

    public ClaimRouteRequest(Route route, List<TrainCard> discarded, Player player)
    {
        super(player);
        _route = route;
        _discarded = discarded;
    }

    public Route get_route() {
        return _route;
    }

    public List<TrainCard> get_discarded() {
        return _discarded;
    }
}
