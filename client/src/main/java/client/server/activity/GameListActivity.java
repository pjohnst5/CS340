package client.server.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import client.server.fragment.GameListFragment;
import client.server.fragment.IGameListView;
import client.server.presenter.GameListPresenter;
import client.server.presenter.IGameListPresenter;

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
