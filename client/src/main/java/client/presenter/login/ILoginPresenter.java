package client.presenter.login;

public interface ILoginPresenter {

    public void login(String username, String password);

    public void register(String username, String password, String checkPassword);
}
