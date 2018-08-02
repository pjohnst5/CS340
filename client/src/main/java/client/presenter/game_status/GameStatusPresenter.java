package client.presenter.game_status;

import java.util.Observable;
import java.util.Observer;

import client.model.ClientModel;
import client.server.AsyncServerTask;
import client.view.fragment.game_status.IGameStatusView;

public class GameStatusPresenter implements IGameStatusPresenter, Observer, AsyncServerTask.AsyncCaller {

    private IGameStatusView _statusView;
    private ClientModel _model;

    public GameStatusPresenter(IGameStatusView view){
        _statusView = view;
        _model = ClientModel.getInstance();
    }

    @Override
    public void pause() {
        _model.deleteObserver(this);
    }

    @Override
    public void resume() {
        _model.addObserver(this);
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        exception.printStackTrace();
    }

    @Override
    public void update(Observable observable, Object o) {
        _statusView.update();
    }
}
