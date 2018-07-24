package client.presenter.game.play;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import client.facade.ServicesFacade;
import client.model.ClientModel;
import client.server.AsyncServerTask;
import client.view.fragment.game.play.IDestinationCardSelectView;
import shared.enumeration.CityName;
import shared.exception.DeckException;
import shared.exception.InvalidGameException;
import shared.model.City;
import shared.model.Player;
import shared.model.decks.DestCard;
import shared.model.decks.ICard;

public class DestinationCardSelectPresenter
        implements IDestinationCardSelectPresenter, Observer, AsyncServerTask.AsyncCaller {

    private IDestinationCardSelectView _selectView;
    private ClientModel _model;
    private boolean _initialized;
    private ServicesFacade _facade;

    public DestinationCardSelectPresenter(IDestinationCardSelectView selectView){

        _selectView = selectView;
        _model = ClientModel.getInstance();
        _model.addObserver(this);
        _initialized = false;
        _facade = new ServicesFacade();
        //update();

    }

    @Override
    public void submitData(List<DestCard> cardsSelected, List<DestCard> cardsDiscarded){
        String playerId = _model.getUser().get_playerId();
        Player player = null;
        try {
            player = _model.getCurrentGame().getPlayer(playerId);
        } catch (InvalidGameException e) {
            e.printStackTrace();
            System.out.println("RAWR");
        }

        _facade.selectDestCard(this, cardsSelected, player);
        _facade.discardDestCard(this, cardsDiscarded, player);
    }

    @Override
    public void destroy() {
        _model.deleteObserver(this);
    }

    @Override
    public void onServerResponseComplete(Exception exception) {

    }

    @Override
    public void init() {
        if (_initialized) return;
        try {
            String playerId = _model.getUser().get_playerId();
            if (_model.getCurrentGame().playerTurn().equals(playerId)){
                List<DestCard> cards = _model.getCurrentGame().getDestDeck().getThreeCards();
                for (DestCard card : cards){
                    _selectView.addCard(card);
                    _initialized = true;
                }
            }
        } catch (InvalidGameException | DeckException e){
            System.out.println("Something went wrong");
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        init();
    }
}
