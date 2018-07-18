package shared.model.request;

import shared.model.decks.TrainDeck;

public class TrainCardRequest extends IServiceRequest {

    private TrainDeck _deck;

    public TrainCardRequest(String gameID, String playerID, TrainDeck deck){
        super(gameID,playerID);
        _deck = deck;
    }

    public TrainDeck getDeck()
    {
        return _deck;
    }
}
