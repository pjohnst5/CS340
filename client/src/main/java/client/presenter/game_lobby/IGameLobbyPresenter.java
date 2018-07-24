package client.presenter.game_lobby;

public interface IGameLobbyPresenter {
    public void sendMessage(String message);
    public void startGame();
    public void leaveGame();
}
