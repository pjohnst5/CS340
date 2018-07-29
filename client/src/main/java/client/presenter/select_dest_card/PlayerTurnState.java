package client.presenter.select_dest_card;

import java.util.List;
import java.util.Observable;

import shared.model.decks.DestCard;

public class PlayerTurnState extends DestCardSelectState {
    @Override
    public int getNumCardsRequired() {
        return 0;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    @Override
    public void submitData(List<DestCard> cardsSelected, List<DestCard> cardsDiscarded) {

    }
}
