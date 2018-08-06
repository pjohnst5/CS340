package client.view.fragment.game_status;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import client.model.ClientModel;
import client.presenter.game_status.GameStatusContainerPresenter;
import client.presenter.game_status.IGameStatusContainerPresenter;
import client.server.communication.ServerProxy;
import client.view.activity.GameListActivity;
import client.view.dialog.GameOverDialog;
import client.view.dialog.GameStatusDialog;
import client.view.fragment.SidebarFragment;
import shared.enumeration.GameState;

public class GameStatusFragment extends SidebarFragment implements IGameStatusContainerView {

    private Button _gameOverButton;
    private Button _gameExitButton;
    private IGameStatusContainerPresenter _presenter;

    private static final String TAG="GameStatusFragment";
    private static final String VIEW_GAME_STATUS_DIALOG_TAG = "OpenGameStatusDialog";
    private static final String GAME_OVER_DIALOG_TAG = "GameOverDialog";
    private static final int VIEW_GAME_STATUS_DIALOG_CODE = 0;
    private static final int GAME_OVER_DIALOG_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_status, container, false);

        setupSidebarButtons(ButtonType.GAME_STATUS);

        // Initialize Simple Members
        GameState state = ClientModel.getInstance().getCurrentGame().get_state();

        // Initialize View Members
        Button _openButton = v.findViewById(R.id.game_status_open_dialog);
        _gameOverButton = v.findViewById(R.id.game_over_open_dialog);
        _gameExitButton = v.findViewById(R.id.game_over_exit_game_button);

        // Set View OnClickListeners
        _openButton.setOnClickListener((view) -> {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            GameStatusDialog dialog = GameStatusDialog.newInstance();
            dialog.setTargetFragment(GameStatusFragment.this, VIEW_GAME_STATUS_DIALOG_CODE);
            dialog.show(manager, VIEW_GAME_STATUS_DIALOG_TAG);
        });

        _gameOverButton.setOnClickListener((view) -> {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            GameOverDialog dialog = GameOverDialog.newInstance();
            dialog.setTargetFragment(GameStatusFragment.this, GAME_OVER_DIALOG_CODE);
            dialog.show(manager, GAME_OVER_DIALOG_TAG);
        });

        _gameExitButton.setOnClickListener((view) ->{
            ServerProxy.instance().stopPoller();
            ClientModel.getInstance().reset();
            getActivity().runOnUiThread(() -> {
                Intent intent = GameListActivity.newIntent(getActivity());
                startActivity(intent);
                getActivity().finish();
            });
        });

        _presenter = new GameStatusContainerPresenter(this);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        _presenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        _presenter.pause();
    }

    @Override
    public void showEndGameButtons(boolean showButtons){
        getActivity().runOnUiThread(() -> {

            if (showButtons){
                _gameOverButton.setEnabled(true);
                _gameExitButton.setEnabled(true);
                _gameOverButton.setVisibility(View.VISIBLE);
                _gameExitButton.setVisibility(View.VISIBLE);
            } else {
                _gameOverButton.setEnabled(false);
                _gameExitButton.setEnabled(false);
                _gameOverButton.setVisibility(View.GONE);
                _gameExitButton.setVisibility(View.GONE);
            }

        });
    }

    @Override
    public void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show());
    }
}
