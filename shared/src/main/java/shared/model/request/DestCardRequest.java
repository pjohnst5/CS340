package shared.model.request;

import shared.model.Player;
import shared.model.decks.DestDeck;

public class DestCardRequest extends IServiceRequest {

    private DestDeck _deck;

    public DestCardRequest(DestDeck deck, Player player) {
        super(player);
        _deck = deck;
    }

    public DestDeck get_deck()
    {
        return _deck;
    }

}
