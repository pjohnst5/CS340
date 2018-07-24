package client.view.fragment;

import client.presenter.login.ILoginPresenter;

public interface ILoginView {
    public void changeViewGameList();
    public void setPresenter(ILoginPresenter presenter);
    public void showToast(String message);
}
