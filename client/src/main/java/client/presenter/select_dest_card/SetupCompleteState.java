package client.presenter.select_dest_card;

import java.util.List;
import java.util.Observable;

import client.model.ClientModel;
import shared.enumeration.GameState;
import shared.exception.InvalidGameException;
import shared.model.decks.DestCard;

public class SetupCompleteState extends DestCardSelectState {

    private static final int NUM_CARDS_REQUIRED = 1;

    private ClientModel _model = ClientModel.getInstance();

    public SetupCompleteState(IDestCardSelectPresenter presenter) {
        super(presenter);
    }

    @Override
    public void enterState() {

        String currentTurnPlayerId = "";
        String clientPlayerId = "";

        try {
            currentTurnPlayerId = _model.getCurrentGame().playerTurn();
            clientPlayerId = _model.getCurrentPlayer().getPlayerID();
        } catch (InvalidGameException e){ }

        if (clientPlayerId.equals(currentTurnPlayerId) && _model.getCurrentGame().get_state() != GameState.FINISHED){
            presenter().setState(new PlayerTurnState(presenter()));
        } else {
            presenter().setState(new PlayerTurnWaitingState(presenter()));
        }
    }

    @Override
    public int getNumCardsRequired() {
        return NUM_CARDS_REQUIRED;
    }

    @Override
    public void submitData(List<DestCard> cardsSelected, List<DestCard> cardsDiscarded) { }

    @Override
    public void serverResponseSuccess() { }

    @Override
    public void serverResponseFailure() { }

    @Override
    public void update(Observable o, Object arg) {
        enterState();
    }
}
