package client.presenter.train_card_select;

import java.util.List;

import client.server.AsyncServerTask;
import shared.model.decks.TrainCard;

public interface ITrainCardSelectPresenter extends AsyncServerTask.AsyncCaller {

    public void pause();
    public void resume();
    public void switchToGameMap();
    public boolean trainCardsLoaded();
    public void setCards(List<TrainCard> cards);
    public void setState(TrainCardSelectState state);
    public void setEnableCardSelect(boolean value);
    public void setEnableSelectionSubmit(boolean value);
    public void submitData(TrainCard keep);

}
