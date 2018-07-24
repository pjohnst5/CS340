package client.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.pjohnst5icloud.tickettoride.R;

import client.view.fragment.GameLobbyFragment;
import client.view.fragment.IGameLobbyView;
import client.presenter.game_lobby.GameLobbyPresenter;
import client.presenter.game_lobby.IGameLobbyPresenter;
import client.view.fragment.game.play.ChatFragment;

public class GameLobbyActivity extends AppCompatActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, GameLobbyActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_lobby);

        FragmentManager fm = getSupportFragmentManager();

        Fragment lobbyContentFragment = fm.findFragmentById(R.id.game_lobby_container);
        Fragment chatFragment = fm.findFragmentById(R.id.game_lobby_chat_container);

        if (lobbyContentFragment == null){
            IGameLobbyView view = new GameLobbyFragment();
            IGameLobbyPresenter presenter = new GameLobbyPresenter(view);
            view.setPresenter(presenter);
            lobbyContentFragment = (Fragment) view;
            fm.beginTransaction()
                    .add(R.id.game_lobby_container, lobbyContentFragment)
                    .commit();
        }

        if (chatFragment == null){
            chatFragment = new ChatFragment();
            fm.beginTransaction()
                    .add(R.id.game_lobby_chat_container, chatFragment)
                    .commit();
        }

    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        getSupportActionBar().hide();
    }
}
