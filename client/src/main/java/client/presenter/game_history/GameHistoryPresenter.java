package client.presenter.game_history;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import client.model.ClientModel;
import client.server.AsyncServerTask;
import client.view.fragment.game_history.IGameHistoryView;
import shared.model.GameAction;
import shared.model.Player;

public class GameHistoryPresenter
        implements IGameHistoryPresenter, Observer, AsyncServerTask.AsyncCaller {

    private IGameHistoryView _historyView;
    private ClientModel _model;

    private int _oldSize;

    public GameHistoryPresenter(IGameHistoryView historyView){

        _historyView = historyView;
        _model = ClientModel.getInstance();
        _oldSize = 0;
        update();

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
    public Player getPlayerByDisplayName(String displayName) {
        return _model.getCurrentGame().getPlayerByDisplayName(displayName);
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        _historyView.showToast(exception.getMessage());
    }

    @Override
    public void onServerResponseComplete() {

    }

    private void update(){
        List<GameAction> actions = _model.getCurrentGame().getGameActions();

        if (_oldSize != actions.size()) {
            for (int i = actions.size() - 1; i >= 0; i--){
                if (_historyView.addAction(actions.get(i))){
                    _oldSize++;

                    if (_oldSize == actions.size()){
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void update(Observable observable, Object o) {
        update();
    }
}
