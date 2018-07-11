package client.server.presenter;

import java.util.Observable;
import java.util.Observer;

import client.ClientModel;
import client.server.fragment.IGameListView;
import client.server.task.AsyncServerTask;
import shared.CustomEnumerations.PlayerColor;
import shared.CustomExceptions.InvalidGameException;
import shared.CustomExceptions.PlayerException;
import shared.Game;
import shared.Player;
import shared.User;

public class GameListPresenter implements IGameListPresenter, Observer {
    private IGameListView _view;
    private ClientModel _model;

    public GameListPresenter(IGameListView view) {
        _view = view;
        _model = ClientModel.getInstance();
        _model.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {

    }

    @Override
    public void createGame(String gameName, PlayerColor color, int maxPlayers) {
        User user = _model.getUser();
        Player player = null;
        try {
            player = new Player(user.getUserName(), user.getUserName(), color);
        } catch (PlayerException e) {
            e.printStackTrace();
            _view.showMessage("Invalid player object");
        }
        Game game = null;
        try {
            game = new Game(gameName, player, maxPlayers);
        } catch (InvalidGameException e) {
            e.printStackTrace();
            _view.showMessage("Invalid game object");
        }
        new AsyncServerTask().execute(game);
        // FIXME: call GameListFacade.createGame(game)
    }

    @Override
    public void joinGame(int gameId) {
        // FIXME: implement; call GameListFacade.joinGame(gameId)

    }
}
