package client.server.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import client.server.fragment.ILoginView;
import client.server.fragment.LoginFragment;
import client.server.presenter.ILoginPresenter;
import client.server.presenter.LoginPresenter;

public class LoginActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, LoginActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        ILoginView view = new LoginFragment();
        ILoginPresenter presenter = new LoginPresenter(view);
        view.setPresenter(presenter);
        return (Fragment) view;
    }
}
