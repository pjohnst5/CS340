package client.server.presenter;

import java.util.Observable;
import java.util.Observer;

public class LoginPresenter implements ILoginPresenter, Observer {
    @Override
    public void update(Observable observable, Object o) {

    }

    @Override
    public boolean login(String username, String password) {
        // FIXME: call Login method on Login Facade
        return false;
    }

    @Override
    public boolean register(String username, String password, String checkPassword) {
        if (!password.equals(checkPassword)) {
            return false;
        }
        // FIXME: call register method on Login Facade
        return false;
    }
}
