package shared.model.request;

import shared.model.Player;
import shared.model.decks.TrainDeck;

public class TrainCardRequest extends IServiceRequest {

    private TrainDeck _deck;
    private Player _player;

    public TrainCardRequest(TrainDeck deck, Player player){
        super(player);
        _deck = deck;
        _player = player;
    }

    public TrainDeck getDeck()
    {
        return _deck;
    }

    public Player get_player()
    {
        return _player;
    }
}
