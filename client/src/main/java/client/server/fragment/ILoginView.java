package client.server.fragment;

import client.server.presenter.ILoginPresenter;

public interface ILoginView {
    public void changeViewGameList();
    public void setPresenter(ILoginPresenter presenter);
    public void showToast(String message);
}
