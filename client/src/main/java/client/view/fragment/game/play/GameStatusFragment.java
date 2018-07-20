package client.view.fragment.game.play;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pjohnst5icloud.tickettoride.R;

import client.view.fragment.SidebarFragment;

public class GameStatusFragment extends SidebarFragment implements IGameStatusView {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_status, container, false);

        setupSidebarButtons(ButtonType.GAME_STATUS);

        return v;
    }

}
