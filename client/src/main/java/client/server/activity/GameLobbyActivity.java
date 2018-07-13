package client.server.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import client.server.fragment.GameLobbyFragment;
import client.server.fragment.IGameLobbyView;
import client.server.presenter.GameLobbyPresenter;
import client.server.presenter.IGameLobbyPresenter;

public class GameLobbyActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, GameLobbyActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        IGameLobbyView view = new GameLobbyFragment();
        IGameLobbyPresenter presenter = new GameLobbyPresenter(view);
        view.setPresenter(presenter);
        return (Fragment) view;
    }
}
