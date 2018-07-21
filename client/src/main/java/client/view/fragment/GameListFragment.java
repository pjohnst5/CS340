package client.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import java.util.List;

import client.presenter.IGameListPresenter;
import client.server.communication.poll.GameListPoller;
import client.view.activity.GameLobbyActivity;
import client.view.dialog.CreateGameDialog;
import client.view.dialog.JoinGameDialog;
import shared.enumeration.PlayerColor;
import shared.model.Game;

public class GameListFragment extends Fragment implements IGameListView {
    private RecyclerView mGameListRecyclerView;
    private Button mCreateGameButton;
    private Button mJoinGameButton;
    private Game mCurrentlySelectedGame;

    private GameListAdapter mGameListAdapter;
    private IGameListPresenter mPresenter;

    private GameHolder mCurrentHolder;

    private static final String TAG = "GameListFragment";

    private static final String CREATE_GAME_DIALOG_TAG = "CreateGameDialog";
    private static final String JOIN_GAME_DIALOG_TAG = "JoinGameDialog";

    private static final int CREATE_GAME_DIALOG_CODE = 0;
    private static final int JOIN_GAME_DIALOG_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_list, container, false);

        mGameListRecyclerView = v.findViewById(R.id.game_list_recycler_view);
        mGameListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCreateGameButton = v.findViewById(R.id.create_game_button);
        mCreateGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                CreateGameDialog dialog = CreateGameDialog.newInstance();
                dialog.setTargetFragment(GameListFragment.this, CREATE_GAME_DIALOG_CODE);
                dialog.show(manager, CREATE_GAME_DIALOG_TAG);
//                dialog.hideDefaultButtons();

                GameListPoller.instance().stop();
            }
        });
        mJoinGameButton = v.findViewById(R.id.join_game_button);
        mJoinGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mCurrentlySelectedGame == null){
                    showToast("Please Select a game first");
                    return;
                }

                Bundle args = new Bundle();
                args.putString("GameId", mCurrentlySelectedGame.getGameID());

                GameListPoller.instance().stop();

                FragmentManager manager = getFragmentManager();
                JoinGameDialog dialog = JoinGameDialog.newInstance();
                dialog.setArguments(args);
                dialog.setTargetFragment(GameListFragment.this, JOIN_GAME_DIALOG_CODE);
                dialog.show(manager, JOIN_GAME_DIALOG_TAG);
                /*if (mCurrentlySelectedGame != null) {
                    mPresenter.joinGame(mCurrentlySelectedGame.getGameID());
                }*/
            }
        });

        mCurrentlySelectedGame = null;

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }
    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == CREATE_GAME_DIALOG_CODE) {

            String gameName = data.getStringExtra(CreateGameDialog.EXTRA_GAME_NAME);
            String displayName = data.getStringExtra(CreateGameDialog.EXTRA_DISPLAY_NAME);
            int maxPlayers = data.getIntExtra(CreateGameDialog.EXTRA_MAX_PLAYERS, 2);
            int color = data.getIntExtra(CreateGameDialog.EXTRA_PLAYER_COLOR, 0);

            PlayerColor playerColor = getColorChoice(color);

            mPresenter.createGame(gameName, displayName, playerColor, maxPlayers);
        }

        if (requestCode == JOIN_GAME_DIALOG_CODE) {
            String displayName = data.getStringExtra(JoinGameDialog.EXTRA_DISPLAY_NAME);
            int color = data.getIntExtra(JoinGameDialog.EXTRA_PLAYER_COLOR, 0);

            PlayerColor playerColor = getColorChoice(color);

            mPresenter.joinGame(displayName, playerColor, mCurrentlySelectedGame.getGameID());
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
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void _updateGamesList(List<Game> games){
        if (games == null) {return;}
        mGameListAdapter = new GameListAdapter(games);
        mGameListRecyclerView.setAdapter(mGameListAdapter);
        if (mCurrentlySelectedGame != null) {
            if (!currentlySelectedIsInList(games)) {
                mCurrentlySelectedGame = null;
            }
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

    @Override
    public void setPresenter(IGameListPresenter presenter) {
        mPresenter = presenter;
    }

    private boolean currentlySelectedIsInList(List<Game> games) {
        for (Game game : games) {
            if (game.getGameID() == mCurrentlySelectedGame.getGameID()) {
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
            mGameIdView.setBackgroundColor(getResources().getColor(R.color.train_light_grey));
            mGameNameView.setBackgroundColor(getResources().getColor(R.color.train_light_grey));
            mGamePlayerNumView.setBackgroundColor(getResources().getColor(R.color.train_light_grey));
        }

        public void select(){
            mGameIdView.setBackgroundColor(getResources().getColor(R.color.blue));
            mGameNameView.setBackgroundColor(getResources().getColor(R.color.blue));
            mGamePlayerNumView.setBackgroundColor(getResources().getColor(R.color.blue));
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
            if (mCurrentHolder != null){
                mCurrentHolder.deselect();
            }
            mCurrentlySelectedGame = mGame;
            mCurrentHolder = this;
            this.select();
        }
    }

    private class GameListAdapter extends RecyclerView.Adapter<GameHolder> {

        private List<Game> mGames;
        public GameListAdapter(List<Game> games) {mGames = games;}
        @NonNull
        @Override
        public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new GameHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull GameHolder holder, int position) {
            Game game = mGames.get(position);
            holder.bind(game);
        }

        @Override
        public int getItemCount() {
            return mGames.size();
        }
    }
}
