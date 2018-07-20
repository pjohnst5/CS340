package client.view.fragment;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.pjohnst5icloud.tickettoride.R;

import client.view.fragment.game.play.GameChatFragment;
import client.view.fragment.game.play.GameHistoryFragment;
import client.view.fragment.game.play.GameStatusFragment;

public abstract class SidebarFragment extends Fragment {


    protected enum ButtonType {
        CHAT,
        GAME_HISTORY,
        GAME_STATUS
    }

    private View.OnClickListener createClickListener(Fragment fragment){
        return (_view) -> {
            FragmentManager fm = getActivity().getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            transaction.replace(R.id.game_sidebar_container, fragment);
            transaction.commit();
        };

    }

    public void setupSidebarButtons(ButtonType type){

        Button chatButton = getActivity().findViewById(R.id.game_chat_button);
        Button historyButton = getActivity().findViewById(R.id.game_history_button);
        Button gameStatusButton = getActivity().findViewById(R.id.game_status);

        chatButton.setSelected(false);
        historyButton.setSelected(false);
        gameStatusButton.setSelected(false);

        View.OnClickListener chatButtonListener = createClickListener(new GameChatFragment());
        View.OnClickListener gameHistoryButtonListener = createClickListener(new GameHistoryFragment());
        View.OnClickListener gameStatusButtonListener = createClickListener(new GameStatusFragment());

        switch (type) {
            case CHAT:
                chatButton.setSelected(true);
                chatButton.setOnClickListener(null);
                historyButton.setOnClickListener(gameHistoryButtonListener);
                gameStatusButton.setOnClickListener(gameStatusButtonListener);
                break;

            case GAME_HISTORY:
                historyButton.setSelected(true);
                historyButton.setOnClickListener(null);
                chatButton.setOnClickListener(chatButtonListener);
                gameStatusButton.setOnClickListener(gameStatusButtonListener);
                break;

            case GAME_STATUS:
                gameStatusButton.setSelected(true);
                gameStatusButton.setOnClickListener(null);
                chatButton.setOnClickListener(chatButtonListener);
                historyButton.setOnClickListener(gameHistoryButtonListener);
                break;
        }

    }
}
