package client.view.fragment.game_lobby;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import java.util.List;

import client.presenter.game_lobby.IGameLobbyPresenter;
import client.util.ColorPicker;
import client.view.activity.GameActivity;
import shared.enumeration.GameState;
import shared.model.Game;
import shared.model.Player;

public class GameLobbyFragment extends Fragment implements IGameLobbyView {

    private Button mStartButton;
    private Button mLeaveButton;

    private RecyclerView mPlayerListRecyclerView;
    private PlayerListAdapter mPlayerListAdapter;

    private Game mCurrentGame;
    private IGameLobbyPresenter mPresenter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_lobby, container, false);

        mStartButton = v.findViewById(R.id.start_game_button);
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.startGame();
            }
        });

        mLeaveButton = v.findViewById(R.id.leave_game_button);
        mLeaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.leaveGame();
            }
        });
        mLeaveButton.setEnabled(false);
        mLeaveButton.setVisibility(View.INVISIBLE); // FIXME: leave game feature not implemented


        mPlayerListRecyclerView = v.findViewById(R.id.player_list_recycler_view);
        mPlayerListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateView(mCurrentGame);
        return v;
    }

    private void updateView(Game game) {
        mCurrentGame = game;
        if (mCurrentGame == null) { return; }
        List<Player> players = mCurrentGame.getPlayers();
        if (players == null) {return; }
        mPlayerListAdapter = new PlayerListAdapter(players);
        mPlayerListRecyclerView.setAdapter(mPlayerListAdapter);
        GameState state = mCurrentGame.get_state();
        if (state == GameState.READY) {
            mStartButton.setEnabled(true);
        } else {
            mStartButton.setEnabled(false);
        }
    }

    @Override
    public void updateGame(Game game) {
        getActivity().runOnUiThread(() -> updateView(game));
    }

    @Override
    public void startGame() {

        Intent intent = GameActivity.newIntent(getActivity());
        startActivity(intent);

    }

    @Override
    public void leaveGame() {
        getActivity().getSupportFragmentManager().popBackStack(); // FIXME: doesn't work
    }

    @Override
    public void setPresenter(IGameLobbyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setCurrentGame(Game currentGame) {
        mCurrentGame = currentGame;
    }

    @Override
    public void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show());
    }


    private class PlayerHolder extends RecyclerView.ViewHolder {
        private TextView mPlayerNameView;
        private Player mPlayer;
        private LinearLayout _container;
        public PlayerHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.player_list_item, parent, false));
            mPlayerNameView = itemView.findViewById(R.id.list_item_player_name);
            _container = itemView.findViewById(R.id.list_item_player_container);
        }
        public void bind(Player player) {
            mPlayer = player;
            mPlayerNameView.setText(mPlayer.getUserName());
            _container.setBackgroundColor(ColorPicker.getPlayerColor(getResources(), mPlayer.getColor()));
        }
    }
    private class PlayerListAdapter extends RecyclerView.Adapter<PlayerHolder> {
        List<Player> mPlayers;
        public PlayerListAdapter(List<Player> players) {
            mPlayers = players;
        }

        @NonNull
        @Override
        public PlayerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PlayerHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PlayerHolder holder, int position) {
            Player player = mPlayers.get(position);
            holder.bind(player);
        }

        @Override
        public int getItemCount() {
            return mPlayers.size();
        }
    }
}
