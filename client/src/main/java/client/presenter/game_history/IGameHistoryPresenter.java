package client.presenter.game_history;

import shared.model.Player;

public interface IGameHistoryPresenter {

    public void destroy();
    public Player getPlayerByDisplayName(String displayName);

}
