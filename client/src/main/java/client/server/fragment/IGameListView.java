package client.server.fragment;

import java.util.List;

import client.server.presenter.IGameListPresenter;
import shared.Game;

public interface IGameListView {

    public void updateGameList(List<Game> games);
    public void joinGame(Game game);
    public void setPresenter(IGameListPresenter presenter);
    public void showToast(String message);
}
