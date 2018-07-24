package client.view.fragment;

import java.util.List;

import client.presenter.game_list.IGameListPresenter;
import shared.model.Game;

public interface IGameListView {

    public void updateGameList(List<Game> games);
    public void joinGame();
    public void setPresenter(IGameListPresenter presenter);
    public void showToast(String message);
}
