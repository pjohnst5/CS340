package client.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pjohnst5icloud.tickettoride.R;

import client.view.fragment.game.play.ChatFragment;
import client.view.fragment.game.play.SelectDestinationCardFragment;

public class GameActivity extends AppCompatActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, GameActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        FragmentManager fm = getSupportFragmentManager();

        Fragment gameContentFragment = fm.findFragmentById(R.id.game_content_container);
        Fragment gameSidebarFragment = fm.findFragmentById(R.id.game_sidebar_container);

        if (gameContentFragment == null){
            gameContentFragment = new SelectDestinationCardFragment();
            fm.beginTransaction()
                    .add(R.id.game_content_container, gameContentFragment)
                    .commit();
        }

        if (gameSidebarFragment == null){
            gameSidebarFragment = new ChatFragment();
            fm.beginTransaction()
                    .add(R.id.game_sidebar_container, gameSidebarFragment)
                    .commit();
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        getSupportActionBar().hide();
    }
}
