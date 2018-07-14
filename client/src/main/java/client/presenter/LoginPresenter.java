package client.presenter;

import android.util.Log;

import java.util.Observable;
import java.util.Observer;

import client.facade.ServerFacade;
import client.model.ClientModel;
import client.view.fragment.ILoginView;
import client.server.AsyncServerTask;

public class LoginPresenter implements ILoginPresenter, Observer, AsyncServerTask.AsyncCaller {
    private ILoginView _view;
    private ClientModel _model;

    private static final String TAG = "LoginPresenter";

    public LoginPresenter(ILoginView view) {
        _view = view;
        _model = ClientModel.getInstance();
        _model.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        Log.i(TAG,"Updating LoginPresenter");
        if (observable instanceof ClientModel) {
            if (_model.getUser() != null) {
                _model.deleteObserver(this);
                _view.changeViewGameList();
            }
        }
    }

    public boolean validateArguments(String username,
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
        if (!validateArguments(username, password, password))
            return;

        ServerFacade.login(this, username, password);
    }

    @Override
    public void register(String username, String password, String checkPassword) {
        if (!validateArguments(username, password, checkPassword))
            return;

        ServerFacade.register(this, username, password);
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        _view.showToast(exception.getMessage());
    }
}
