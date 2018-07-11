package client.server.presenter;

import client.server.fragment.IGameListView;

public class GameListPresenter implements IGameListPresenter {
    private IGameListView _view;

    public GameListPresenter(IGameListView view) {
        _view = view;
    }
}
