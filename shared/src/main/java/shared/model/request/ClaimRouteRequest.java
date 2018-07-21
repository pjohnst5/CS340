package shared.model.request;

import shared.model.GameMap;
import shared.model.Player;
import shared.model.decks.TrainDeck;

public class ClaimRouteRequest extends IServiceRequest {

    private GameMap _map;
    private TrainDeck _deck;
    private Player _player;

    public ClaimRouteRequest(GameMap map, TrainDeck deck, Player player)
    {
        super(player);
        _map = map;
        _deck = deck;
        _player = player;
    }

    public GameMap get_map()
    {
        return _map;
    }

    public TrainDeck get_deck()
    {
        return _deck;
    }

    public Player get_player()
    {
        return _player;
    }
}
