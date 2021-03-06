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
    private List<TrainCard> _cards;

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
    public boolean trainCardsLoaded() {
        return (_cards != null && _cards.size() > 0);
    }

    @Override
    public void enableLocomotives(boolean enable) {
        _selectView.enableLocomotives(enable);
    }

    @Override
    public void setCards(List<TrainCard> cards) {
        _cards = cards;
        _selectView.clearCards();
        for (TrainCard card : cards){
            _selectView.addCard(card);
        }
    }

    @Override
    public void setEnableCloseDialog(boolean value) {
        _selectView.setEnabledCloseDialog(value);
    }

    @Override
    public void setEnableCardSelect(boolean value) {
        _selectView.setCardSelectEnabled(value);
    }

    @Override
    public void setEnableSelectionSubmit(boolean value) {
        _selectView.setSelectionSubmitEnabled(value);
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
    public void submitData(TrainCard keep) {
        _selectView.showLoadScreen(true);
        _state.submitData(keep);
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        exception.printStackTrace();
        _selectView.showLoadScreen(true);
        _state.serverResponseFailure();
    }

    @Override
    public void onServerResponseComplete() {
        _selectView.showLoadScreen(false);
        _state.serverResponseSuccess();
    }

    @Override
    public void update(Observable o, Object arg) {
        _state.update();
    }
}
