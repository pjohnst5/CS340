package client.server.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import client.server.fragment.GameLobbyFragment;

public class GameLobbyActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, GameLobbyActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        return new GameLobbyFragment();
    }
}
