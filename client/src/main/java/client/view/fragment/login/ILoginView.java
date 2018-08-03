package client.view.fragment.login;

import client.presenter.login.ILoginPresenter;

public interface ILoginView {

    public void switchToGameList();
    public void setPresenter(ILoginPresenter presenter);
    public void showToast(String message);
    public void hideLoadMenu();
    
}
