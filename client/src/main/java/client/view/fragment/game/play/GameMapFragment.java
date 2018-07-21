package client.view.fragment.game.play;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pjohnst5icloud.tickettoride.R;

/**
 * Created by jtyler17 on 7/21/18.
 */

public class GameMapFragment extends Fragment {
    public static GameMapFragment newInstance() {
        return new GameMapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_map, container, false);
        return v;
    }
}
