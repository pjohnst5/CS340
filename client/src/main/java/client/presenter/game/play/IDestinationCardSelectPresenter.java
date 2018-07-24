package client.presenter.game.play;

import java.util.List;

import shared.model.decks.DestCard;

public interface IDestinationCardSelectPresenter {

    public void destroy();
    public void init();
    public void submitData(List<DestCard> cardsSelected, List<DestCard> cardsDiscarded);
}
