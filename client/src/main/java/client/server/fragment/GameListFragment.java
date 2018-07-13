package client.server.fragment;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import java.util.List;

import client.server.presenter.IGameListPresenter;
import shared.CustomEnumerations.PlayerColor;
import shared.Game;

public class GameListFragment extends Fragment implements IGameListView {
    private RecyclerView mGameListRecyclerView;
    private Button mCreateGameButton;
    private Button mJoinGameButton;
    private Game mCurrentlySelectedGame;

    private GameListAdapter mGameListAdapter;
    private IGameListPresenter mPresenter;

    private static final String CREATE_GAME_DIALOG_TAG = "CreateGameDialog";

    private static final int CREATE_GAME_DIALOG_CODE = 0;

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
            }
        });
        mJoinGameButton = v.findViewById(R.id.join_game_button);
        mJoinGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentlySelectedGame != null) {
                    mPresenter.joinGame(mCurrentlySelectedGame.getGameID());
                }
            }
        });

        mCurrentlySelectedGame = null;

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == CREATE_GAME_DIALOG_CODE) {
            String gameName = data.getStringExtra(CreateGameDialog.EXTRA_GAME_NAME);
            int maxPlayers = data.getIntExtra(CreateGameDialog.EXTRA_MAX_PLAYERS, 2);
            int color = data.getIntExtra(CreateGameDialog.EXTRA_PLAYER_COLOR, 0);
            PlayerColor playerColor = PlayerColor.BLACK;
            switch (color) {
                case 1:
                    playerColor = PlayerColor.BLUE;
                    break;
                case 2:
                    playerColor = PlayerColor.GREEN;
                    break;
                case 3:
                    playerColor = PlayerColor.RED;
                    break;
                case 4:
                    playerColor = PlayerColor.YELLOW;
                    break;
            }
            mPresenter.createGame(gameName, playerColor, maxPlayers);
        }
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateGameList(List<Game> games) {
        // FIXME: called by presenter object to update the game list
        mGameListAdapter = new GameListAdapter(games);
        mGameListRecyclerView.setAdapter(mGameListAdapter);
        if (mCurrentlySelectedGame != null) {
            if (!currentlySelectedIsInList(games)) {
                mCurrentlySelectedGame = null;
            }
        }
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

        public GameHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.game_list_item, parent, false));
        }

        public void bind(Game game) {
            mGame = game;
        }

        @Override
        public void onClick(View view) {
            mCurrentlySelectedGame = mGame;
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
