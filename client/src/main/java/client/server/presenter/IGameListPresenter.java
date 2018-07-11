package client.server.presenter;

import shared.CustomEnumerations.PlayerColor;
import shared.Game;

public interface IGameListPresenter {
    public void createGame(String gameName, PlayerColor color, int maxPlayers);
    public void joinGame(int gameId);
}
