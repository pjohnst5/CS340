package client.presenter.select_dest_card;

import java.util.List;
import java.util.Observable;

import shared.model.decks.DestCard;

public class PlayerTurnWaitingState extends DestCardSelectState {

    private static final int NUM_CARDS_REQUIRED = 4;

    public PlayerTurnWaitingState(IDestCardSelectPresenter presenter){
        super(presenter);
    }

    @Override
    public void enterState() {
        presenter().switchToGameMap();
    }

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
