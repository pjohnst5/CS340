package client.presenter.train_card_select;

import java.util.List;

import client.server.AsyncServerTask;
import shared.model.decks.TrainCard;

public interface ITrainCardSelectPresenter extends AsyncServerTask.AsyncCaller {

    public void pause();
    public void resume();
    public void switchToGameMap();
    public void setState(TrainCardSelectState state);
    public void submitData(List<TrainCard> keep, List<TrainCard> discard);

}
