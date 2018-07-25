package client.view.fragment.game.play;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pjohnst5icloud.tickettoride.R;

import client.facade.ServicesFacade;
import client.view.dialog.GameStatusDialog;
import client.view.fragment.SidebarFragment;

public class GameStatusFragment extends SidebarFragment implements IGameStatusView {

    private Button _openButton;
    private Button _testButton;

    private static final String TAG="GameStatusFragment";
    private static final String VIEW_GAME_STATUS_DIALOG_TAG = "OpenGameStatusDialog";
    private static final int VIEW_GAME_STATUS_DIALOG_CODE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_status, container, false);

        setupSidebarButtons(ButtonType.GAME_STATUS);

        _openButton = v.findViewById(R.id.game_status_open_dialog);
        _openButton.setOnClickListener((view) -> {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            GameStatusDialog dialog = GameStatusDialog.newInstance();
            dialog.setTargetFragment(GameStatusFragment.this, VIEW_GAME_STATUS_DIALOG_CODE);
            dialog.show(manager, VIEW_GAME_STATUS_DIALOG_TAG);
        });

        // FIXME: delete after phase 2
        _testButton = v.findViewById(R.id.game_status_test_button);
        _testButton.setOnClickListener((view) -> {
            ServicesFacade facade = new ServicesFacade();
            facade.phase2PassoffScenarios();
        });

        return v;
    }

}
