package client.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.WindowManager;

import client.view.fragment.login.LoginFragment;

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
    public void onBackPressed() { }

    @Override
    protected Fragment createFragment() {
        return new LoginFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
