package client.server.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import client.server.fragment.LoginFragment;

public class LoginActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, LoginActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }
}
