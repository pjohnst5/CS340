package client.presenter;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import client.model.ClientModel;
import client.server.communication.ServerProxy;
import client.server.communication.poll.GameLobbyPoller;
import client.server.communication.poll.Poller;
import client.view.fragment.IGameLobbyView;
import client.server.task.AsyncServerTask;
import client.server.task.LeaveGameTask;
import client.server.task.StartGameTask;
import shared.model.Game;
import shared.model.Message;
import shared.model.Player;
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

        _view.updateGame(_model.getCurrentGame());
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
        new AsyncServerTask(this).execute(newMessage);
    }

    @Override
    public void startGame() {
        Game game = _model.getCurrentGame();
        if (true ){ // !game.isReadyToStart()) {
            _view.showToast("Still waiting for players to join!");
            return;
        }

        //Paul added this below, I just need to know who is trying to start the game
        //------------------------------------------------------
        //Get the username of this player
        String username = _model.getUser().getUserName();
        String playerID = new String();
        List<Player> players = _model.getCurrentGame().getPlayers();
        for (int i = 0; i < players.size(); i++){
            if (players.get(i).getUserName().equals(username)){ //we have a match
                playerID = players.get(i).getPlayerID();
            }
        }
        // ----------------------------------------------------


        StartGameTask request = new StartGameTask();
        request.set_gameId(_model.getCurrentGame().getGameID());
        //-----------------------------------------------------
        request.set_playerId(playerID);
        //-----------------------------------------------------
        new AsyncServerTask(this).execute(request);
    }

    @Override
    public void leaveGame() {
        LeaveGameTask request = new LeaveGameTask();
        request.set_gameId(_model.getCurrentGame().getGameID());
        request.set_username(_model.getUser().getUserName());
        new AsyncServerTask(this).execute(request);
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        _view.showToast(exception.getMessage());
    }
}
