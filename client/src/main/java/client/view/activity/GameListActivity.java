package client.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import client.view.fragment.GameListFragment;
import client.view.fragment.IGameListView;
import client.presenter.GameListPresenter;
import client.presenter.IGameListPresenter;

public class GameListActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, GameListActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        IGameListView view = new GameListFragment();
        IGameListPresenter presenter = new GameListPresenter(view);
        view.setPresenter(presenter);
        return (Fragment) view;
    }
}
