package client.server.presenter;

import java.util.Observable;
import java.util.Observer;

import client.ClientModel;
import client.server.fragment.ILoginView;
import client.server.task.AsyncServerTask;
import client.server.task.LoginTask;
import client.server.task.RegisterTask;

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
        if (observable instanceof ClientModel) {
            if (_model.getUser() != null) {
                _model.deleteObserver(this);
                _view.changeViewGameList();
            }
        }
    }

    @Override
    public void login(String username, String password) {
        LoginTask request = new LoginTask();
        request.set_username(username);
        request.set_password(password);
        new AsyncServerTask(this).execute(request);
    }

    @Override
    public void register(String username, String password, String checkPassword) {
        if (!password.equals(checkPassword)) {
            _view.showMessage("Confirm password does not match");
            return;
        }
        RegisterTask request = new RegisterTask();
        request.set_username(username);
        request.set_password(password);
        new AsyncServerTask(this).execute(request);
    }

    @Override
    public void onServerResponseComplete(Exception exception) {
        _view.showMessage(exception.getMessage());
    }
}
