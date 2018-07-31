package client.presenter.select_dest_card;

import java.util.List;
import java.util.Observable;

import client.facade.ServicesFacade;
import client.model.ClientModel;
import shared.exception.InvalidGameException;
import shared.model.decks.DestCard;

public class SetupPendingState extends DestCardSelectState {

    private ClientModel _model = ClientModel.getInstance();
    private ServicesFacade _facade = new ServicesFacade();

    public SetupPendingState() { }

    public SetupPendingState(IDestCardSelectPresenter presenter) {
        super(presenter);
    }

    @Override
    public int getNumCardsRequired() {
        return 4;
    }

    @Override
    public void enterState() {
        presenter().showOverlayMessage("Please wait while the other players select their cards...");
    }

    @Override
    public void exitState(){
        presenter().switchToGameMap();
        presenter().hideOverlayMessage();
    }

    @Override
    public void update(Observable o, Object arg) {

        int numPlayersCompleted = _model.getCurrentGame().getNumPlayersCompletedSetup();
        int numPlayers = _model.getCurrentGame().getMaxPlayers();

        if (numPlayersCompleted >= numPlayers){
            _model.getCurrentGame().setupComplete();
            //presenter().setState(new SetupCompleteState(presenter()));
            exitState();
        }

    }

    @Override
    public void submitData(List<DestCard> cardsSelected, List<DestCard> cardsDiscarded) { }
}
