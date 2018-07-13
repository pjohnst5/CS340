package client.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.IOException;

import client.view.fragment.ILoginView;
import client.view.fragment.LoginFragment;
import client.presenter.ILoginPresenter;
import client.presenter.LoginPresenter;
import shared.configuration.ConfigurationManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            ConfigurationManager.use(getBaseContext().getAssets().open("config.properties"));
            Log.i(getClass().getCanonicalName(), "Configurations Loaded from file");

        } catch (IOException e ){
            Log.e(getClass().getCanonicalName(),"Could not load configurations file");
        }
    }
}