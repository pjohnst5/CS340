package shared.model.request;

import shared.model.Player;
import shared.model.decks.DestDeck;

public class DestCardRequest extends IServiceRequest {

    private DestDeck _deck;
    private Player _player;

    public DestCardRequest(String gameID, String playerID, DestDeck deck, Player player)
    {
        super(gameID,playerID);
        _deck = deck;
        _player = player;
    }

    public DestDeck get_deck()
    {
        return _deck;
    }

    public Player get_player() {
        return _player;
    }
}
