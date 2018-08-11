package client.presenter.game_lobby;

import java.util.Observable;
import java.util.Observer;

import client.facade.GameLobbyService;
import client.model.ClientModel;
import client.server.communication.ServerProxy;
import client.server.communication.poll.GameLobbyPoller;
import client.server.communication.poll.Poller;
import client.view.fragment.game_lobby.IGameLobbyView;
import client.server.AsyncServerTask;
import shared.enumeration.GameState;
import shared.model.Game;

public class GameLobbyPresenter implements IGameLobbyPresenter, Observer, AsyncServerTask.AsyncCaller {
    private IGameLobbyView _view;
    private ClientModel _model;

    public GameLobbyPresenter(IGameLobbyView view) {
        _view = view;
        _model = ClientModel.getInstance();

        Poller _poller = GameLobbyPoller.instance();
        ServerProxy.instance().usePoller(_poller);

        _view.setCurrentGame(_model.getCurrentGame());
    }

    @Override
    public void pause() {
        ServerProxy.instance().stopPoller();
        _model.deleteObserver(this);
    }

    @Override
    public void resume() {
        _model.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        Game currentGame = _model.getCurrentGame();

        if (currentGame == null) {
            // player left the game
            ServerProxy.instance().stopPoller();
            _view.leaveGame();

        } else if (currentGame.get_state() == GameState.SETUP) {
            // started the game
            ServerProxy.instance().stopPoller();
            _view.startGame();

        } else {
            _view.updateGame(currentGame);
        }
    }

    @Override
    public void startGame() {
        Game game = _model.getCurrentGame();
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

    @Override
    public void onServerResponseComplete() {

    }
}
