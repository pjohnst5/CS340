package client.presenter;

import java.util.Observable;
import java.util.Observer;

import client.facade.ServerFacade;
import client.model.ClientModel;
import client.server.communication.ServerProxy;
import client.server.communication.poll.GameLobbyPoller;
import client.server.communication.poll.Poller;
import client.view.fragment.IGameLobbyView;
import client.server.AsyncServerTask;
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
            } else if (currentGame.getStarted()) {
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
    public void sendMessage(String message) {
        if (message.equals("")) { return; }
        User user = _model.getUser();
        Message newMessage = new Message();
        newMessage.setGameID(_model.getCurrentGame().getGameID());
        newMessage.setMessage(message);
        newMessage.setUsername(user.getUserName());
        ServerFacade.sendMessage(this, newMessage);
    }

    @Override
    public void startGame() {
        String gameID = _model.getCurrentGame().getGameID();
        String playerID = _model.getUser().get_playerId();

        ServerFacade.startGame(this, gameID, playerID);
    }

    @Override
    public void leaveGame() {
        // FIXME: implement
//        ServerFacade.leaveGame(this,
//                _model.getCurrentGame().getGameID(),
//                _model.getUser().get_playerId());
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        _view.showToast(exception.getMessage());
    }
}
