package client.view.fragment.game_lobby;

import client.presenter.game_lobby.IGameLobbyPresenter;
import shared.model.Game;
import shared.model.Message;

public interface IGameLobbyView {

    public void updateGame(Game game); // ie: add players
    public void startGame();
    public void leaveGame();
    public void setPresenter(IGameLobbyPresenter presenter);
    public void setCurrentGame(Game currentGame);
    public void showToast(String message);

}
