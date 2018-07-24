package client.presenter.select_dest_card;

import java.util.List;
import java.util.Observable;

import shared.model.decks.DestCard;

public class DestCardSelectSetupCompleteState extends DestCardSelectState {

    private static final int NUM_CARDS_REQUIRED = 1;

    public DestCardSelectSetupCompleteState(IDestCardSelectPresenter presenter) {
        super(presenter);
    }

    @Override
    public int getNumCardsRequired() {
        return NUM_CARDS_REQUIRED;
    }

    @Override
    public void submitData(List<DestCard> cardsSelected, List<DestCard> cardsDiscarded) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
