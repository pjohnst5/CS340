package client.presenter.game_map;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import client.model.ClientModel;
import client.server.AsyncServerTask;
import client.view.fragment.game_map.IGameMapView;
import shared.exception.InvalidGameException;
import shared.model.Player;
import shared.model.Route;

/**
 * Created by jtyler17 on 7/23/18.
 */

public class GameMapPresenter implements IGameMapPresenter, Observer, AsyncServerTask.AsyncCaller {
    private IGameMapView _mapView;
    private ClientModel _model;

    public GameMapPresenter(IGameMapView view) {
        _mapView = view;
        _model = ClientModel.getInstance();

        if (_model.getCurrentGame() == null) {
            return;
        }
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
        // TODO: implement -- see if the route can be claimed; if it's the player's turn, enable the claim button; etc.
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        _mapView.showToast(exception.getMessage());
    }

    @Override
    public void update(Observable observable, Object o) {
        _mapView.updateMap();
        _mapView.updatePlayerTurn();
    }
}
