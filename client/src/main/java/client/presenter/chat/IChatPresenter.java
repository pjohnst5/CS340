package client.presenter.chat;

import shared.enumeration.PlayerColor;

public interface IChatPresenter {

    public void destroy();
    public void sendMessage(String message);
    public String getClientDisplayName();

}
