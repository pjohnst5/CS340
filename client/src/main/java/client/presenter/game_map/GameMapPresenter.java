package client.presenter.game_map;

import android.util.Log;

import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import client.facade.ServicesFacade;
import client.model.ClientModel;
import client.server.AsyncServerTask;
import client.view.fragment.game_map.IGameMapView;
import shared.enumeration.GameState;
import shared.enumeration.TrainColor;
import shared.exception.DeckException;
import shared.exception.InvalidGameException;
import shared.exception.RouteClaimedAlreadyException;
import shared.model.Game;
import shared.model.Player;
import shared.model.Route;
import shared.model.decks.TrainCard;

/**
 * Created by jtyler17 on 7/23/18.
 */

public class GameMapPresenter implements IGameMapPresenter, Observer, AsyncServerTask.AsyncCaller {
    private static final String TAG = "GameMapPresenter";
    private IGameMapView _mapView;
    private ClientModel _model;

    public GameMapPresenter(IGameMapView view) {
        _mapView = view;
        _model = ClientModel.getInstance();

        if (_model.getCurrentGame() == null) {
            return;
        }

        int numDestCards = _model.getCurrentGame().getDestDeck().get_cards().size();
        int numTrainCards = _model.getCurrentGame().getTrainDeck().sizeOfFaceDownDeck();
        _mapView.updateDeckCount(numDestCards, numTrainCards);

        _model.addObserver(this);
    }

    @Override
    public List<Player> getPlayers() {
        return _model.getCurrentGame().getPlayers();
    }

    @Override
    public String getCurrentTurnPlayerId(){
        try {
            return _model.getCurrentGame().playerTurn();
        } catch (InvalidGameException e){
            return "";
        }
    }

    @Override
    public void destroy() {
        _model.deleteObserver(this);
    }

    @Override
    public void routeSelected(Route route) {
        if (!_model.isMyTurn()) {
            return;
        }
        _mapView.setClaimRouteButtonEnabled(true);
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        _mapView.showToast(exception.getMessage());
    }

    @Override
    public void update(Observable observable, Object o) {

        int numDestCards = _model.getCurrentGame().getDestDeck().get_cards().size();
        int numTrainCards = _model.getCurrentGame().getTrainDeck().sizeOfFaceDownDeck();

        _mapView.updateMap();
        _mapView.updatePlayerTurn();
        _mapView.updateDeckCount(numDestCards, numTrainCards);

        if (_model.getCurrentGame().get_state() == GameState.FINISHED) {
            _mapView.gameOver();
        }
    }

    @Override
    public Player getMyPlayer() {
        return _model.getMyPlayer();
    }

    @Override
    public void claimRoute(Route route, Map<TrainColor, Integer> selectedCards) {
        List<TrainCard> discardCards = _model.getMyPlayer().getCardsFromCounts(selectedCards);
        try {
            new ServicesFacade().claimRoute(this, route, discardCards);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            onServerResponseComplete(e);
        }
    }
}
