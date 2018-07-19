package shared.model.request;

import shared.model.GameMap;
import shared.model.decks.TrainDeck;

public class ClaimRouteRequest extends IServiceRequest {

    private GameMap _map;
    private TrainDeck _deck;

    public ClaimRouteRequest(String gameID, String playerID, GameMap map, TrainDeck deck)
    {
        super(gameID,playerID);
        _map = map;
        _deck = deck;
    }

    public GameMap get_map()
    {
        return _map;
    }

    public TrainDeck get_deck()
    {
        return _deck;
    }
}
