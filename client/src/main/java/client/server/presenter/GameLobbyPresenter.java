package client.server.presenter;

import java.util.Observable;
import java.util.Observer;

import client.ClientFacade.GameLobbyFacade;
import client.ClientModel;
import client.server.communication.ServerProxy;
import client.server.communication.poll.GameLobbyPoller;
import client.server.communication.poll.Poller;
import client.server.fragment.IGameLobbyView;
import client.server.task.AsyncServerTask;
import shared.User;

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
    }

    @Override
    public void update(Observable observable, Object o) {

    }

    @Override
    public void sendMessage(String message) {
        User user = _model.getUser();
        Message newMessage = new Message();
        //
        new AsyncServerTask(this).execute(newMessage);
    }

    @Override
    public void startGame() {
        //
    }

    @Override
    public void leaveGame() {
        new AsyncServerTask(this).execute(new LeaveGameTask());
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        
    }
}
