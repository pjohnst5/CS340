package client.server.presenter;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import client.ClientModel;
import client.server.communication.ServerProxy;
import client.server.communication.poll.GameListPoller;
import client.server.communication.poll.Poller;
import client.server.fragment.IGameListView;
import client.server.task.AsyncServerTask;
import client.server.task.JoinGameTask;
import shared.CustomEnumerations.PlayerColor;
import shared.CustomExceptions.InvalidGameException;
import shared.CustomExceptions.PlayerException;
import shared.Game;
import shared.Player;
import shared.User;

public class GameListPresenter implements IGameListPresenter, Observer, AsyncServerTask.AsyncCaller {
    private IGameListView _view;
    private ClientModel _model;
    private Poller _poller;

    public GameListPresenter(IGameListView view) {
        _view = view;
        _model = ClientModel.getInstance();
        _model.addObserver(this);
        _poller = GameListPoller.instance();
        ServerProxy.instance().usePoller(_poller);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof ClientModel) {
            Game currentGame = _model.getCurrentGame();
            if (currentGame != null) {
                // FIXME: transfer the view to GameLobbyView
            } else {
                List<Game> games = _model.getGames();
                _view.updateGameList(games);
            }
        }
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
        new AsyncServerTask(this).execute(game);
    }

    @Override
    public void joinGame(String gameId) {
        JoinGameTask request = new JoinGameTask();
        request.set_gameId(gameId);
        new AsyncServerTask(this).execute(request);
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        _view.showMessage(exception.getMessage());
    }
}
