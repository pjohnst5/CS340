package client.presenter.select_dest_card;

import java.util.List;
import java.util.Observable;

import shared.model.decks.DestCard;

public abstract class DestCardSelectState {

    private IDestCardSelectPresenter _presenter;

    public DestCardSelectState() {}
    public DestCardSelectState(IDestCardSelectPresenter presenter){
        _presenter = presenter;
    }

    public IDestCardSelectPresenter presenter() {
        return _presenter;
    }
    public void setPresenter(IDestCardSelectPresenter presenter) { _presenter = presenter; }

    public void enterState() {}
    public void exitState(){}
    public abstract int getNumCardsRequired();
    public abstract void update(Observable o, Object arg);
    public abstract void submitData(List<DestCard> cardsSelected, List<DestCard> cardsDiscarded);
}
