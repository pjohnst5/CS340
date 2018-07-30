package client.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import java.io.IOException;

import client.view.fragment.login.LoginFragment;
import shared.configuration.ConfigurationManager;

public class LoginActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, LoginActivity.class);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        getSupportActionBar().hide();
    }

    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
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
