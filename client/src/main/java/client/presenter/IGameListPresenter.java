package client.presenter;

import shared.enumeration.PlayerColor;

public interface IGameListPresenter {
    public void createGame(String gameName, PlayerColor color, int maxPlayers);
    public void joinGame(String gameId);
}