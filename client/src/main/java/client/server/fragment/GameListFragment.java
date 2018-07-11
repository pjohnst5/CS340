package client.server.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pjohnst5icloud.tickettoride.R;

import java.util.List;

import client.server.presenter.IGameListPresenter;
import shared.Game;

public class GameListFragment extends Fragment implements IGameListView {
    private RecyclerView mGameListRecyclerView;
    private Button mCreateGameButton;
    private Button mJoinGameButton;
    private Game mCurrentlySelectedGame;

    private GameListAdapter mGameListAdapter;
    private IGameListPresenter mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_list, container, false);

        mGameListRecyclerView = v.findViewById(R.id.game_list_recycler_view);
        mGameListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCreateGameButton = v.findViewById(R.id.create_game_button);
        mJoinGameButton = v.findViewById(R.id.join_game_button);

        mCurrentlySelectedGame = null;

        return v;
    }

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

    private boolean currentlySelectedIsInList(List<Game> games) {
        for (Game game : games) {
            if (game.getGameID() == mCurrentlySelectedGame.getGameID()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setPresenter(IGameListPresenter presenter) {
        mPresenter = presenter;
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
