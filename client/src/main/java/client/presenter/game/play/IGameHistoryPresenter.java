package client.presenter.game.play;

import shared.model.Player;

public interface IGameHistoryPresenter {

    public void destroy();
    public Player getPlayerByDisplayName(String displayName);

}
