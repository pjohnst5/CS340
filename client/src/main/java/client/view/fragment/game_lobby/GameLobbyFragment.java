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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import java.util.List;

import client.presenter.game_lobby.GameLobbyPresenter;
import client.presenter.game_lobby.IGameLobbyPresenter;
import client.util.ColorPicker;
import client.view.activity.GameActivity;
import shared.enumeration.GameState;
import shared.model.Game;
import shared.model.Player;

public class GameLobbyFragment extends Fragment implements IGameLobbyView {

    private Game _currentGame;
    private Button _startButton;
    private TextView _roomName;
    private RecyclerView _playerListRecyclerView;
    private IGameLobbyPresenter _presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_lobby, container, false);

        // Initialize Simple Members
        _presenter = new GameLobbyPresenter(this);

        // Initialize View Members
        Button _leaveButton = v.findViewById(R.id.leave_game_button);
        _roomName = v.findViewById(R.id.game_lobby_room_name);
        _startButton = v.findViewById(R.id.start_game_button);
        _playerListRecyclerView = v.findViewById(R.id.player_list_recycler_view);

        // Modify View Members
        _leaveButton.setEnabled(false);
        _leaveButton.setVisibility(View.INVISIBLE); // FIXME: leave game feature not implemented
        _playerListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Set View OnClickListeners
        _leaveButton.setOnClickListener((view) -> _presenter.leaveGame());
        _startButton.setOnClickListener((view) -> _presenter.startGame());

        updateView(_currentGame);
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

    private void updateView(Game game) {
        // Get the current game
        _currentGame = game;
        if (_currentGame == null) { return; }

        // Show the game room name
        String roomName = _currentGame.getGameName();
        _roomName.setText(roomName);

        // Get the players from the game
        List<Player> players = _currentGame.getPlayers();
        if (players == null) {return; }

        // Load the players into the adapter
        PlayerListAdapter _playerListAdapter = new PlayerListAdapter(players);
        _playerListRecyclerView.setAdapter(_playerListAdapter);

        // Enable Start Button if possible
        GameState state = _currentGame.get_state();
        if (state == GameState.READY) {
            _startButton.setEnabled(true);
        } else {
            _startButton.setEnabled(false);
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
        _presenter = presenter;
    }

    @Override
    public void setCurrentGame(Game currentGame) {
        _currentGame = currentGame;
    }

    @Override
    public void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show());
    }


    private class PlayerHolder extends RecyclerView.ViewHolder {
        private TextView _playerNameView;
        private LinearLayout _container;

        public PlayerHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.player_list_item, parent, false));

            _playerNameView = itemView.findViewById(R.id.list_item_player_name);
            _container = itemView.findViewById(R.id.list_item_player_container);
        }

        public void bind(Player player) {
            _playerNameView.setText(player.getDisplayName());
            _container.setBackgroundColor(ColorPicker.getPlayerColor(getResources(), player.getColor()));
        }
    }
    private class PlayerListAdapter extends RecyclerView.Adapter<PlayerHolder> {
        List<Player> _players;
        public PlayerListAdapter(List<Player> players) {
            _players = players;
        }

        @NonNull
        @Override
        public PlayerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new PlayerHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PlayerHolder holder, int position) {
            Player player = _players.get(position);
            holder.bind(player);
        }

        @Override
        public int getItemCount() {
            return _players.size();
        }
    }
}
