package client.view.fragment.login;

import client.presenter.login.ILoginPresenter;
import client.view.fragment.ILoadingScreenFragment;

public interface ILoginView extends ILoadingScreenFragment {

    public void switchToGameList();
    public void setPresenter(ILoginPresenter presenter);
    public void showToast(String message);
    public void hideLoadMenu();
    
}
