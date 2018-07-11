package client.server.presenter;

import java.util.Observable;
import java.util.Observer;

import client.server.fragment.ILoginView;

public class LoginPresenter implements ILoginPresenter, Observer {
    private ILoginView _view;

    public LoginPresenter(ILoginView view) {
        _view = view;
    }

    @Override
    public void update(Observable observable, Object o) {
//        if (observable instanceof ClientModel) {
        // FIXME: implement
    }

    @Override
    public void login(String username, String password) {
        // FIXME: call Login method on Login Facade
        return;
    }

    @Override
    public void register(String username, String password, String checkPassword) {
        if (!password.equals(checkPassword)) {
            _view.sendMessage("Confirm password does not match");
            return;
        }
        // FIXME: call register method on Login Facade
        return;
    }
}
