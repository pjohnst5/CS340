package client.presenter.select_dest_card;

import java.util.List;
import java.util.Observable;

import client.facade.ServicesFacade;
import client.model.ClientModel;
import shared.exception.InvalidGameException;
import shared.model.Player;
import shared.model.decks.DestCard;

public class DestCardSelectSetupState extends DestCardSelectState {

    private static final int NUM_CARDS_REQUIRED = 2;

    private ClientModel _model = ClientModel.getInstance();
    private ServicesFacade _facade = new ServicesFacade();

    public DestCardSelectSetupState(IDestCardSelectPresenter presenter) {
        super(presenter);
    }

    @Override
    public void init(){

        String playerId;
        Player player = null;
        try {
            playerId = _model.getUser().get_playerId();
            player = _model.getCurrentGame().getPlayer(playerId);
        } catch(InvalidGameException e) {
            e.printStackTrace();
            System.out.println("This is the real Error");
        }

        _facade.requestDestCards(presenter(), player);
    }

    @Override
    public int getNumCardsRequired() {
        return NUM_CARDS_REQUIRED;
    }

    @Override
    public void submitData(List<DestCard> cardsSelected, List<DestCard> cardsDiscarded) {

        String playerId = _model.getUser().get_playerId();
        Player player = null;
        try {
            player = _model.getCurrentGame().getPlayer(playerId);
        } catch (InvalidGameException e) {
            e.printStackTrace();
            System.out.println("RAWR");
        }

        //_facade.returnSetupInformation(this, cardsSelected, cardsDiscarded);
        //_facade.selectDestCard(this, cardsSelected, player);
        //_facade.discardDestCard(this, cardsDiscarded, player);
    }

    @Override
    public void update(Observable o, Object arg) {

        if (_model.getCurrentGame().destOptionCardsEmpty()) return;

        List<DestCard> cards = _model.getCurrentGame().getDestOptionCards();

        for (DestCard card : cards){
            presenter().addCard(card);
        }

    }
}
