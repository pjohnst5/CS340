package client.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import client.view.fragment.GameLobbyFragment;
import client.view.fragment.IGameLobbyView;
import client.presenter.game_lobby.GameLobbyPresenter;
import client.presenter.game_lobby.IGameLobbyPresenter;

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
