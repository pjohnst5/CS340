package client.presenter.select_dest_card;

import java.util.List;
import java.util.Observable;

import client.facade.ServicesFacade;
import client.model.ClientModel;
import shared.model.Player;
import shared.model.decks.DestCard;
import shared.model.request.DestCardRequest;
import shared.model.wrapper.ThreeDestCardWrapper;

public class PlayerTurnState extends DestCardSelectState {

    private static final int NUM_CARDS_REQUIRED = 1;

    private ClientModel _model = ClientModel.getInstance();
    private ServicesFacade _facade = new ServicesFacade();

    public PlayerTurnState(IDestCardSelectPresenter presenter){
        super(presenter);
    }

    @Override
    public void enterState() {
        displayNumCardsRequired();
        _facade.requestDestCards(presenter(), _model.getCurrentPlayer());
    }

    @Override
    public void exitState() {
        presenter().disableInput();
    }

    @Override
    public int getNumCardsRequired() {
        return NUM_CARDS_REQUIRED;
    }

    @Override
    public void submitData(List<DestCard> cardsSelected, List<DestCard> cardsDiscarded) {

        Player player = _model.getCurrentPlayer();

        ThreeDestCardWrapper keep = new ThreeDestCardWrapper(cardsSelected);
        ThreeDestCardWrapper discard = new ThreeDestCardWrapper(cardsDiscarded);
        DestCardRequest request = new DestCardRequest(player, keep, discard);

        _facade.selectDestCard(presenter(), request);
        presenter().setState(new PlayerTurnWaitingState(presenter()));

    }

    @Override
    public void update(Observable o, Object arg) {

        if (_model.getCurrentGame().destOptionCardsEmpty()) return;

        List<DestCard> cards = _model.getCurrentGame().getDestOptionCards();

        for (DestCard card : cards){
            presenter().addCard(card);
        }

        _model.getCurrentGame().clearDestOptionCards();

    }
}
