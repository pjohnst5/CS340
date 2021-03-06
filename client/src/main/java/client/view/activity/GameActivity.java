package client.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pjohnst5icloud.tickettoride.R;

import client.server.communication.ServerProxy;
import client.server.communication.poll.MainGamePoller;
import client.view.fragment.chat.ChatFragment;
import client.view.fragment.select_dest_card.DestCardSelectFragment;

public class GameActivity extends AppCompatActivity {

    public static Intent newIntent(Context packageContext) {
        return new Intent(packageContext, GameActivity.class);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        getSupportActionBar().hide();
    }

    @Override
    public void onBackPressed() { }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);

        FragmentManager fm = getSupportFragmentManager();

        Fragment gameContentFragment = fm.findFragmentById(R.id.game_content_container);
        Fragment gameSidebarFragment = fm.findFragmentById(R.id.game_sidebar_container);

        if (gameContentFragment == null){
            gameContentFragment = new DestCardSelectFragment();
            fm.beginTransaction()
                    .add(R.id.game_content_container, gameContentFragment)
                    .commit();
        }

        if (gameSidebarFragment == null) {
            gameSidebarFragment = new ChatFragment();
            fm.beginTransaction()
                    .add(R.id.game_sidebar_container, gameSidebarFragment)
                    .commit();
        }

        ServerProxy.instance().usePoller(MainGamePoller.instance());
    }
}
