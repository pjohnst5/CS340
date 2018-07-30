package client.presenter.game_history;

import shared.model.Player;

public interface IGameHistoryPresenter {

    public void pause();
    public void resume();
    public Player getPlayerByDisplayName(String displayName);

}
