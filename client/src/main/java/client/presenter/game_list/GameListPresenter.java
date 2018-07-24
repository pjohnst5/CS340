package client.presenter.game_list;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

import client.facade.GameListService;
import client.model.ClientModel;
import client.server.communication.ServerProxy;
import client.server.communication.poll.GameListPoller;
import client.server.communication.poll.Poller;
import client.view.fragment.IGameListView;
import client.server.AsyncServerTask;
import shared.enumeration.PlayerColor;
import shared.exception.InvalidGameException;
import shared.exception.InvalidGameRequestException;
import shared.exception.MaxPlayersException;
import shared.exception.PlayerException;
import shared.model.Game;
import shared.model.Player;
import shared.model.User;
import shared.model.request.JoinRequest;

public class GameListPresenter implements IGameListPresenter, Observer, AsyncServerTask.AsyncCaller {
    private IGameListView _view;
    private ClientModel _model;
    private Poller _poller;

    public GameListPresenter(IGameListView view) {
        _view = view;
        _model = ClientModel.getInstance();
        _model.addObserver(this);
        _poller = GameListPoller.instance();

        // DALLAS TODO: Fix this after fragment creation works
        ServerProxy.instance().usePoller(_poller);
    }

    // FIXME: _poller should get added/started in onStart()

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof ClientModel) {
            Game currentGame = _model.getCurrentGame();
            if (currentGame != null) {
                _model.deleteObserver(this);
                ServerProxy.instance().stopPoller();
                _view.joinGame();
            } else {
                Map<String, Game> gameMap = _model.getGames();
                List<Game> games = new ArrayList(gameMap.values());
                _view.updateGameList(games);
            }
        }
    }

    private boolean validateCreateGameRequest(String gameName, String displayName){

        if (gameName.equals("")) {
            _view.showToast("Room Name can't be empty");

        } else if (displayName.equals("")){
            _view.showToast("Display name can't be empty");

        } else {
            return true;

        }

        return false;

    }

    @Override
    public void createGame(String gameName, String displayName, PlayerColor color, int maxPlayers) {

        if (!validateCreateGameRequest(gameName, displayName)) {
            return;
        }

        User user = _model.getUser();
        Player player = null;

        String gameId = UUID.randomUUID().toString();

        try {
            player = new Player(user.getUserName(), displayName, color, gameId, user.getUUID().toString());
        } catch (PlayerException e) {
            e.printStackTrace();
            _view.showToast("Invalid player object");
        }
        Game game = null;
        try {
            game = new Game(gameName, maxPlayers);
            game.setGameID(gameId);
            game.addPlayer(player);
        } catch (InvalidGameException | MaxPlayersException e) {
            e.printStackTrace();
            _view.showToast("Invalid game object");
        }
        GameListService.createGame(this, game);
    }

    @Override
    public void joinGame(String displayName, PlayerColor color, String gameId) {
        JoinRequest request = null;
        try {
            request = new JoinRequest(_model.getUser().getUserName(), displayName, color, gameId);
        } catch (InvalidGameRequestException e) {
            e.printStackTrace();
            _view.showToast("Error joining game");
        }
        GameListService.joinGame(this, request);
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        _view.showToast(exception.getMessage());
    }
}
