package client.presenter.game_lobby;

import java.util.Observable;
import java.util.Observer;

import client.facade.GameLobbyService;
import client.model.ClientModel;
import client.server.communication.ServerProxy;
import client.server.communication.poll.GameLobbyPoller;
import client.server.communication.poll.Poller;
import client.view.fragment.IGameLobbyView;
import client.server.AsyncServerTask;
import shared.enumeration.GameState;
import shared.model.Game;
import shared.model.Message;
import shared.model.User;

public class GameLobbyPresenter implements IGameLobbyPresenter, Observer, AsyncServerTask.AsyncCaller {
    private IGameLobbyView _view;
    private ClientModel _model;
    private Poller _poller;

    public GameLobbyPresenter(IGameLobbyView view) {
        _view = view;
        _model = ClientModel.getInstance();
        _model.addObserver(this);
        _poller = GameLobbyPoller.instance();
        ServerProxy.instance().usePoller(_poller);

        _view.setCurrentGame(_model.getCurrentGame());
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof ClientModel) {
            Game currentGame = _model.getCurrentGame();
            if (currentGame == null) {
                // player left the game
                _model.deleteObserver(this);
                ServerProxy.instance().stopPoller();
                _view.leaveGame();
            } else if (currentGame.get_state() == GameState.SETUP) {
                // started the game
                _model.deleteObserver(this);
                ServerProxy.instance().stopPoller();
                _view.startGame();
            } else {
                _view.updateGame(currentGame);
            }
        }
    }

    @Override
    public void startGame() {
        Game game = _model.getCurrentGame();
        if (game.get_state() != GameState.READY) {
            _view.showToast("Can't start game until more players have joined");
            return;
        }

        String gameId = game.getGameID();
        String playerId = _model.getUser().get_playerId();

        GameLobbyService.startGame(this, gameId, playerId);
    }

    @Override
    public void leaveGame() {
        String gameId = _model.getCurrentGame().getGameID();
        String playerId = _model.getUser().get_playerId();
        GameLobbyService.leaveGame(this, gameId, playerId);
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        _view.showToast(exception.getMessage());
    }
}
