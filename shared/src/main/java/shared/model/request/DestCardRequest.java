package shared.model.request;

import shared.model.decks.DestDeck;

public class DestCardRequest extends IServiceRequest {

    private DestDeck _deck;

    public DestCardRequest(String gameID, String playerID, DestDeck deck)
    {
        super(gameID,playerID);
        _deck = deck;
    }

    public DestDeck get_deck()
    {
        return _deck;
    }
}
