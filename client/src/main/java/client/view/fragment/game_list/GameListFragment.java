package client.view.fragment.game_list;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import java.util.List;

import client.presenter.game_list.GameListPresenter;
import client.presenter.game_list.IGameListPresenter;
import client.server.communication.poll.GameListPoller;
import client.view.activity.GameLobbyActivity;
import client.view.dialog.CreateGameDialog;
import client.view.dialog.JoinGameDialog;
import shared.enumeration.PlayerColor;
import shared.model.Game;

public class GameListFragment extends Fragment implements IGameListView {

    private Game _currentlySelectedGame;
    private GameHolder _currentHolder;
    private RecyclerView _gameListRecyclerView;
    private IGameListPresenter _presenter;

    private static final int CREATE_GAME_DIALOG_CODE = 0;
    private static final int JOIN_GAME_DIALOG_CODE = 1;

    private static final String TAG = "GameListFragment";
    private static final String CREATE_GAME_DIALOG_TAG = "CreateGameDialog";
    private static final String JOIN_GAME_DIALOG_TAG = "JoinGameDialog";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_list, container, false);

        // Initialize Simple Members
        _currentlySelectedGame = null;
        _presenter = new GameListPresenter(this);

        // Initialize View Members
        Button _joinGameButton = v.findViewById(R.id.join_game_button);
        Button _createGameButton = v.findViewById(R.id.create_game_button);
        _gameListRecyclerView = v.findViewById(R.id.game_list_recycler_view);

        // Modify View Members
        _gameListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Set View OnClickListeners
        _joinGameButton.setOnClickListener((view) -> {
            if (_currentlySelectedGame == null){
                showToast("Please Select a game first");
                return;
            }

            GameListPoller.instance().stop();

            Bundle args = new Bundle();
            args.putString("GameId", _currentlySelectedGame.getGameID());

            FragmentManager manager = getFragmentManager();
            JoinGameDialog dialog = JoinGameDialog.newInstance();
            dialog.setArguments(args);
            dialog.setTargetFragment(GameListFragment.this, JOIN_GAME_DIALOG_CODE);
            dialog.show(manager, JOIN_GAME_DIALOG_TAG);
        });


        _createGameButton.setOnClickListener((view) -> {
            GameListPoller.instance().stop();

            FragmentManager manager = getFragmentManager();
            CreateGameDialog dialog = CreateGameDialog.newInstance();
            dialog.setTargetFragment(GameListFragment.this, CREATE_GAME_DIALOG_CODE);
            dialog.show(manager, CREATE_GAME_DIALOG_TAG);

        });

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) { return; }

        if (requestCode == CREATE_GAME_DIALOG_CODE) {

            String gameName = data.getStringExtra(CreateGameDialog.EXTRA_GAME_NAME);
            String displayName = data.getStringExtra(CreateGameDialog.EXTRA_DISPLAY_NAME);
            int maxPlayers = data.getIntExtra(CreateGameDialog.EXTRA_MAX_PLAYERS, 2);
            int color = data.getIntExtra(CreateGameDialog.EXTRA_PLAYER_COLOR, 0);

            PlayerColor playerColor = getColorChoice(color);

            _presenter.createGame(gameName, displayName, playerColor, maxPlayers);
        }

        if (requestCode == JOIN_GAME_DIALOG_CODE) {
            String displayName = data.getStringExtra(JoinGameDialog.EXTRA_DISPLAY_NAME);
            int color = data.getIntExtra(JoinGameDialog.EXTRA_PLAYER_COLOR, 0);

            PlayerColor playerColor = getColorChoice(color);

            _presenter.joinGame(displayName, playerColor, _currentlySelectedGame.getGameID());
        }
    }

    private PlayerColor getColorChoice(int color) {
        switch (color) {
            case 1:
                return PlayerColor.BLUE;
            case 2:
                return PlayerColor.GREEN;
            case 3:
                return PlayerColor.RED;
            case 4:
                return PlayerColor.YELLOW;
            default:
                return PlayerColor.BLACK;
        }
    }

    @Override
    public void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show());
    }

    private void _updateGamesList(List<Game> games){
        if (games == null) { return; }

        GameListAdapter _gameListAdapter = new GameListAdapter(games);
        _gameListRecyclerView.setAdapter(_gameListAdapter);

        if (_currentlySelectedGame != null && !currentlySelectedIsInList(games)) {
            _currentlySelectedGame = null;
        }
    }

    @Override
    public void updateGameList(List<Game> games) {
        getActivity().runOnUiThread(() -> _updateGamesList(games));

    }

    @Override
    public void joinGame() {
        // switch view
        Intent intent = GameLobbyActivity.newIntent(getActivity());
        startActivity(intent);
    }

    private boolean currentlySelectedIsInList(List<Game> games) {
        for (Game game : games) {
            if (game.getGameID().equals(_currentlySelectedGame.getGameID())) {
                return true;
            }
        }
        return false;
    }

    private class GameHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Game mGame;
        private TextView mGameIdView;
        private TextView mGameNameView;
        private TextView mGamePlayerNumView;

        public void deselect(){
            mGameIdView.setBackgroundColor(Color.TRANSPARENT);
            mGameNameView.setBackgroundColor(Color.TRANSPARENT);
            mGamePlayerNumView.setBackgroundColor(Color.TRANSPARENT);
        }

        public void select(){
            mGameIdView.setBackgroundColor(getResources().getColor(R.color.yellow_highlight));
            mGameNameView.setBackgroundColor(getResources().getColor(R.color.yellow_highlight));
            mGamePlayerNumView.setBackgroundColor(getResources().getColor(R.color.yellow_highlight));
        }

        public GameHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.game_list_item, parent, false));

            mGameIdView = itemView.findViewById(R.id.game_id_view);
            mGameNameView = itemView.findViewById(R.id.game_name_view);
            mGamePlayerNumView = itemView.findViewById(R.id.game_player_num_view);
            itemView.setOnClickListener(this);
        }

        public void bind(Game game) {
            mGame = game;
            mGameIdView.setText(mGame.getGameID());
            mGameNameView.setText(mGame.getGameName());
            String allowedPlayers = Integer.toString(mGame.getPlayers().size());
            allowedPlayers += "/" + mGame.getMaxPlayers();
            mGamePlayerNumView.setText(allowedPlayers);
        }

        @Override
        public void onClick(View view) {
            if (_currentHolder != null){
                _currentHolder.deselect();
            }
            _currentlySelectedGame = mGame;
            _currentHolder = this;
            this.select();
        }
    }

    private class GameListAdapter extends RecyclerView.Adapter<GameHolder> {

        private List<Game> _games;
        public GameListAdapter(List<Game> games) {_games = games;}
        @NonNull
        @Override
        public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new GameHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull GameHolder holder, int position) {
            Game game = _games.get(position);
            holder.bind(game);
        }

        @Override
        public int getItemCount() {
            return _games.size();
        }
    }
}
