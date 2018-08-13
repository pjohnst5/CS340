package client.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import client.view.fragment.game_list.GameListFragment;

public class GameListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, GameListActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return new GameListFragment();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        getSupportActionBar().hide();
    }

    @Override
    public void onBackPressed() { }
}
