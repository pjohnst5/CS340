package client.presenter.train_card_select;

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
    public abstract void serverResponseSuccess();
    public abstract void serverResponseFailure();
    public abstract void submitData(TrainCard keep);

}
