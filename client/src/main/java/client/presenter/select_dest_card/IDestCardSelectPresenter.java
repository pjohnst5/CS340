package client.presenter.select_dest_card;

import java.util.List;

import client.server.AsyncServerTask;
import shared.model.decks.DestCard;

public interface IDestCardSelectPresenter extends AsyncServerTask.AsyncCaller {

    public void destroy();
    public void addCard(DestCard card);
    public void submitData(List<DestCard> cardsSelected, List<DestCard> cardsDiscarded);
}
