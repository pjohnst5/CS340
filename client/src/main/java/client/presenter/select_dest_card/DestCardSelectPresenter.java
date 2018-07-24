package client.presenter.select_dest_card;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import client.facade.ServicesFacade;
import client.model.ClientModel;
import client.view.fragment.game.play.IDestinationCardSelectView;
import shared.enumeration.GameState;
import shared.model.decks.DestCard;

public class DestCardSelectPresenter
        implements IDestCardSelectPresenter, Observer {

    private IDestinationCardSelectView _selectView;
    private ClientModel _model;
    private ServicesFacade _facade;
    private static boolean _initialized;

    private static DestCardSelectState _state;
    private static DestCardSelectSetupState _setupState;
    private DestCardSelectSetupCompleteState _setupCompleteState;

    public DestCardSelectPresenter(IDestinationCardSelectView selectView){

        _selectView = selectView;
        _model = ClientModel.getInstance();
        _model.addObserver(this);
        _facade = new ServicesFacade();

        _setupState = new DestCardSelectSetupState(this);
        _setupCompleteState = new DestCardSelectSetupCompleteState(this);

        if (_model.getCurrentGame().get_state() == GameState.SETUP){
            _state = _setupState;
        }

        _state.init();

    }

    @Override
    public void addCard(DestCard card){
        _selectView.addCard(card);
    }



    @Override
    public void submitData(List<DestCard> cardsSelected, List<DestCard> cardsDiscarded){

        _state.submitData(cardsSelected, cardsDiscarded);

    }

    @Override
    public void destroy() {
        _model.deleteObserver(this);
    }

    @Override
    public void onServerResponseComplete(Exception exception) {

    }

    @Override
    public void update(Observable o, Object arg) {

        _state.update(o, arg);
        System.out.println("WHAT!");
        if (_initialized){

        } else if (!_model.getCurrentGame().destOptionCardsEmpty()) {

            List<DestCard> cards = _model.getCurrentGame().getDestOptionCards();

            for (DestCard card : cards){
                _selectView.addCard(card);
            }

            _initialized = true;
        }
    }
}
