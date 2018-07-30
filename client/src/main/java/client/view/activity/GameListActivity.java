package client.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import client.view.fragment.game_list.GameListFragment;
import client.view.fragment.game_list.IGameListView;
import client.presenter.game_list.GameListPresenter;
import client.presenter.game_list.IGameListPresenter;

public class GameListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, GameListActivity.class);
    }

    @Override
    protected Fragment createFragment() {
        return new GameListFragment();
    }
}
