package client.presenter.select_dest_card;

import java.util.List;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

import client.facade.ServicesFacade;
import client.model.ClientModel;
import shared.model.Player;
import shared.model.decks.DestCard;
import shared.model.request.DestCardRequest;
import shared.model.wrapper.ThreeDestCardWrapper;

public class PlayerTurnState extends DestCardSelectState {

    private static final int NUM_CARDS_REQUIRED = 1;

    private boolean submitted;
    private ClientModel _model = ClientModel.getInstance();
    private ServicesFacade _facade = new ServicesFacade();
    private DataRequest submitData;

    public PlayerTurnState(IDestCardSelectPresenter presenter){
        super(presenter);
    }

    @Override
    public void enterState() {
        submitted = false;
        update(null, null);
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
        submitted = true;
    }

    @Override
    public void update(Observable o, Object arg) {

        if (submitted || _model.getCurrentGame().destOptionCardsEmpty()) return;

        List<DestCard> cards = _model.getCurrentGame().getDestOptionCards();

        for (DestCard card : cards){
            presenter().addCard(card);
        }

        _model.getCurrentGame().clearDestOptionCards();

    }

    @Override
    public void serverResponseSuccess() {
        if (!submitted) return;
        presenter().setState(new PlayerTurnWaitingState(presenter()));
    }

    @Override
    public void serverResponseFailure() {
        if (!submitted) return;
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                _facade.sendSetupResults(
                        presenter(),
                        submitData.getSelected(),
                        submitData.getDiscarded(),
                        _model.getCurrentPlayer()
                );
                timer.cancel();
            }
        }, 100);
    }
}
