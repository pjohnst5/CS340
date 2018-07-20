package client.view.fragment.game.play;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pjohnst5icloud.tickettoride.R;

import client.view.fragment.SidebarFragment;

public class GameChatFragment extends SidebarFragment implements IGameChatView {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_chat_list, container, false);

        setupSidebarButtons(ButtonType.CHAT);

        return v;
    }
}
