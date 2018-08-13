package client.view.fragment.game_list;

import java.util.List;

import shared.model.Game;

public interface IGameListView {

    public void updateGameList(List<Game> games);
    public void joinGame();
    public void showToast(String message);

}
