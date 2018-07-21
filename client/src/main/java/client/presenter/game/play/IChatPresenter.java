package client.presenter.game.play;

import shared.enumeration.PlayerColor;

public interface IChatPresenter {

    public void sendMessage(String message);
    public String getClientDisplayName();

    /**
     * called to detach presenter from observable
     */
    public void destroy();
}
