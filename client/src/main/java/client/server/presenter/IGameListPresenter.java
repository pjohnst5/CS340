package client.server.presenter;

import shared.CustomEnumerations.PlayerColor;

public interface IGameListPresenter {
    public void createGame(String gameName, PlayerColor color, int maxPlayers);
    public void joinGame(int gameId);
}
