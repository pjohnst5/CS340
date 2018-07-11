package client.server.presenter;

import java.util.Observable;
import java.util.Observer;

import client.server.fragment.IGameListView;
import shared.CustomEnumerations.PlayerColor;
import shared.Game;
import shared.Player;

public class GameListPresenter implements IGameListPresenter, Observer {
    private IGameListView _view;

    public GameListPresenter(IGameListView view) {
        _view = view;
    }

    @Override
    public void update(Observable observable, Object o) {

    }

    @Override
    public void createGame(String gameName, PlayerColor color, int maxPlayers) {
        Player player = new Player(ClientModel.getUsername(), ClientModel.getUsername(), color);
        Game game = new Game(gameName, player, maxPlayers);
        // FIXME: call GameListFacade.createGame(game)
    }

    @Override
    public void joinGame(int gameId) {
        // FIXME: implement; call GameListFacade.joinGame(gameId)
    }
}
