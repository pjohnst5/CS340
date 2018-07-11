package client.server.presenter;

import java.util.Observable;
import java.util.Observer;

import client.ClientModel;
import client.server.fragment.ILoginView;
import client.server.task.AsyncServerTask;
import client.server.task.LoginTask;
import client.server.task.RegisterTask;
import shared.User;

public class LoginPresenter implements ILoginPresenter, Observer {
    private ILoginView _view;
    private ClientModel _model;

    public LoginPresenter(ILoginView view) {
        _view = view;
        _model = ClientModel.getInstance();
        _model.addObserver(this);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof ClientModel) {
            User user = _model.getUser();
//            if (_model.getUUID() != null) {
//                _model.deleteObserver(this);
//                _view.changeViewGameList();
//            }
        }
    }

    @Override
    public void login(String username, String password) {
        LoginTask request = new LoginTask();
        request.set_username(username);
        request.set_password(password);
        new AsyncServerTask().execute(request);
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
        new AsyncServerTask().execute(request);
    }
}
