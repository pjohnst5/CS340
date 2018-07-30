package client.presenter.select_dest_card;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import client.model.ClientModel;
import client.view.fragment.select_dest_card.IDestCardSelectView;
import shared.enumeration.GameState;
import shared.model.decks.DestCard;

public class DestCardSelectPresenter
        implements IDestCardSelectPresenter, Observer {

    private IDestCardSelectView _selectView;
    private ClientModel _model;

    private DestCardSelectState _state;
    public DestCardSelectPresenter(IDestCardSelectView selectView){

        _selectView = selectView;
        _model = ClientModel.getInstance();

        if (_model.getCurrentGame().get_state() == GameState.SETUP){
            setState(new SetupState(this));
        } else {
            setState(new SetupCompleteState(this));
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
    public int getNumCardsRequired() {
        return _state.getNumCardsRequired();
    }

    @Override
    public void setState(DestCardSelectState state) {
        if (_state != null) {
            _state.exitState();
        }

        _state = state;
        _state.enterState();
    }

    @Override
    public void addCard(DestCard card){
        _selectView.addCard(card);
    }

    @Override
    public void disableInput() {
        _selectView.disableSubmitButton();
        _selectView.disableCardSelect();
    }

    @Override
    public void showOverlayMessage(String message) {
        _selectView.showOverlayMessage(message);
    }

    @Override
    public void hideOverlayMessage() {
        _selectView.hideOverlayMessage();
    }

    @Override
    public void submitData(List<DestCard> cardsSelected, List<DestCard> cardsDiscarded){
        _state.submitData(cardsSelected, cardsDiscarded);
    }

    @Override
    public void onServerResponseComplete(Exception exception) {

    }

    @Override
    public void switchToGameMap() {
        _selectView.switchToGameMap();
    }

    @Override
    public void update(Observable o, Object arg) {
        _state.update(o, arg);
    }
}
