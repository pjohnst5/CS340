package client.presenter.train_card_select;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import client.model.ClientModel;
import client.view.fragment.train_card_select.ITrainCardSelectView;
import shared.model.decks.TrainCard;

public class TrainSelectPresenter
        implements ITrainCardSelectPresenter, Observer {

    private ITrainCardSelectView _selectView;
    private ClientModel _model;

    private TrainCardSelectState _state;
    public TrainSelectPresenter(ITrainCardSelectView selectView){

        _selectView = selectView;
        _model = ClientModel.getInstance();

        if (_model.isMyTurn()){
            setState(new PlayerTurnState(this));
        } else {
            setState(new PlayerTurnWaitingState(this));
        }
    }

    @Override
    public void pause() {
        _model.deleteObserver(this);
    }

    @Override
    public void resume() {
        _model.addObserver(this);
    }

    @Override
    public void setState(TrainCardSelectState state) {
        if (_state != null) {
            _state.exitState();
        }

        _state = state;
        _state.enterState();
    }

    @Override
    public void switchToGameMap() {
        _selectView.switchToGameMap();
    }

    @Override
    public void submitData(List<TrainCard> keep, List<TrainCard> discard) {
        _state.submitData(keep, discard);
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        exception.printStackTrace();
    }

    @Override
    public void update(Observable o, Object arg) {
        _state.update();
    }
}
