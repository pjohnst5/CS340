package client.presenter.select_dest_card;

import java.util.List;

import client.server.AsyncServerTask;
import shared.model.decks.DestCard;

public interface IDestCardSelectPresenter extends AsyncServerTask.AsyncCaller {

    public void pause();
    public void resume();
    public void disableInput();
    public void switchToGameMap();
    public int getNumCardsRequired();
    public void hideOverlayMessage();
    public void addCard(DestCard card);
    public void displayNumCardsRequired(int num);
    public void showOverlayMessage(String message);
    public void setState(DestCardSelectState state);
    public void submitData(List<DestCard> cardsSelected, List<DestCard> cardsDiscarded);
}
