package client.presenter.chat;

public interface IChatPresenter {

    public void pause();
    public void resume();
    public void sendMessage(String message);
    public String getClientDisplayName();

}
