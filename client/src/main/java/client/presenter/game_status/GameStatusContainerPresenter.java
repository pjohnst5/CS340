package client.presenter.game_status;

import java.util.Observable;
import java.util.Observer;

import client.model.ClientModel;
import client.server.AsyncServerTask;
import client.view.fragment.game_status.IGameStatusContainerView;
import shared.enumeration.GameState;

public class GameStatusContainerPresenter implements IGameStatusContainerPresenter, Observer, AsyncServerTask.AsyncCaller {

    private IGameStatusContainerView _statusView;
    private ClientModel _model;

    public GameStatusContainerPresenter(IGameStatusContainerView view){
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
        _statusView.showEndGameButtons(showEndButtons());
    }

    @Override
    public void update(Observable observable, Object o) {
        _statusView.showEndGameButtons(showEndButtons());
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        exception.printStackTrace();
    }

    @Override
    public void onServerResponseComplete() {

    }

    private boolean showEndButtons(){
        return ClientModel.getInstance().getCurrentGame().get_state() == GameState.FINISHED;
    }

}
