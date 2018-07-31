package client.presenter.train_card_select;

import java.util.List;

import shared.model.decks.TrainCard;

public abstract class TrainCardSelectState {

    private ITrainCardSelectPresenter _presenter;

    public TrainCardSelectState() {}
    public TrainCardSelectState(ITrainCardSelectPresenter presenter) {
        _presenter = presenter;
    }

    public ITrainCardSelectPresenter presenter(){
        return _presenter;
    }
    public void setPresenter(ITrainCardSelectPresenter presenter) { _presenter = presenter; }

    public void enterState(){}
    public void exitState(){}
    public abstract void update();
    public abstract void submitData(List<TrainCard> keep,List<TrainCard> discard);

}
