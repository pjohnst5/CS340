package client.presenter.login;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

import client.facade.LoginService;
import client.model.ClientModel;
import client.view.fragment.login.ILoginView;
import client.server.AsyncServerTask;

public class LoginPresenter implements ILoginPresenter, Observer, AsyncServerTask.AsyncCaller {
    private ILoginView _view;
    private ClientModel _model;

    private static final String TAG = "LoginPresenter";

    public LoginPresenter(ILoginView view) {
        _view = view;
        _model = ClientModel.getInstance();
    }

    @Override
    public void pause() {
        _model.deleteObserver(this);
    }

    @Override
    public void resume() {
        _model.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        Log.i(TAG,"Updating LoginPresenter");
        _view.hideLoadMenu();
        if (_model.getUser() != null) {
            _view.switchToGameList();
        }
    }

    private boolean validateArguments(String username,
                                String password,
                                String checkPassword){

        if (username.equals("")){
            _view.showToast("Username cannot be empty");

        } else if (password.equals("")) {
            _view.showToast("Password cannot be empty");

        } else if (checkPassword.equals("")) {
            _view.showToast("Confirm Password Field cannot be empty");

        } else if (!password.equals(checkPassword)) {
            _view.showToast("Passwords do not match");

        } else {
            return true;

        }

        return false;

    }

    @Override
    public void login(String username, String password) {

        if (!validateArguments(username, password, password)) {
            _view.hideLoadMenu();
            return;
        }
        LoginService.login(this, username, password);

    }

    @Override
    public void register(String username, String password, String checkPassword) {

        if (!validateArguments(username, password, checkPassword)) {
            _view.hideLoadMenu();
            return;
        }
        LoginService.register(this, username, password);

    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        _view.hideLoadMenu();
        _view.showToast(exception.getMessage());
    }

    @Override
    public void onServerResponseComplete() {

    }
}
