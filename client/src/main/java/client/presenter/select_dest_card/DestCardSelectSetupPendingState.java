package client.presenter.select_dest_card;

import java.util.List;
import java.util.Observable;

import client.facade.ServicesFacade;
import client.model.ClientModel;
import shared.enumeration.GameState;
import shared.model.decks.DestCard;

public class DestCardSelectSetupPendingState extends DestCardSelectState {

    private ClientModel _model = ClientModel.getInstance();
    private ServicesFacade _facade = new ServicesFacade();

    public DestCardSelectSetupPendingState() { }

    public DestCardSelectSetupPendingState(IDestCardSelectPresenter presenter) {
        super(presenter);
    }

    @Override
    public int getNumCardsRequired() {
        return 4;
    }

    @Override
    public void update(Observable o, Object arg) {

        int numPlayersCompleted = _model.getCurrentGame().getNumPlayersCompletedSetup();
        int numPlayers = _model.getCurrentGame().getMaxPlayers();

        if (numPlayersCompleted >= numPlayers){
            presenter().setState(new DestCardSelectSetupCompleteState(presenter()));
            _model.getCurrentGame().set_state(GameState.STARTED);
            presenter().switchToGameMap();
        }

    }

    @Override
    public void submitData(List<DestCard> cardsSelected, List<DestCard> cardsDiscarded) { }
}
