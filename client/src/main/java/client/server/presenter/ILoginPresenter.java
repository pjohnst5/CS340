package client.server.presenter;

public interface ILoginPresenter {

    public boolean login(String username, String password);

    public boolean register(String username, String password, String checkPassword);
}
