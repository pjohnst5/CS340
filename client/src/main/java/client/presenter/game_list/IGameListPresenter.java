package client.presenter.game_list;

import shared.enumeration.PlayerColor;

public interface IGameListPresenter {

    public void pause();
    public void resume();
    public void createGame(String gameName, String displayName, PlayerColor color, int maxPlayers);
    public void joinGame(String displayName, PlayerColor color, String gameId);

}
