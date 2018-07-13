package client.server.fragment;

import client.server.presenter.IGameLobbyPresenter;
import shared.model.Game;
import shared.model.Message;

public interface IGameLobbyView {
    public void addMessage(Message message);
    public void updateGame(Game game); // ie: add players
    public void startGame();
    public void leaveGame();
    public void setPresenter(IGameLobbyPresenter presenter);
    public void showToast(String message);
}
