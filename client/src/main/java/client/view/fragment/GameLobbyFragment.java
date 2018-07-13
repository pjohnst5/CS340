package client.view.fragment;

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
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import java.util.List;

import client.presenter.IGameLobbyPresenter;
import shared.model.Game;
import shared.model.Message;
import shared.model.Player;

public class GameLobbyFragment extends Fragment implements IGameLobbyView {

    private Button mStartButton;
    private Button mLeaveButton;
    private EditText mSendChatText;
    private Button mSendChatButton;

    private RecyclerView mPlayerListRecyclerView;
    private RecyclerView mChatRecyclerView;
    private PlayerListAdapter mPlayerListAdapter;
    private ChatAdapter mChatAdapter;

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

        //mLeaveButton = v.findViewById(R.id.leave_game_button);
        mLeaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.leaveGame();
            }
        });

        mSendChatText = v.findViewById(R.id.send_chat_text_view);
        mSendChatButton = v.findViewById(R.id.send_chat_button);
        mSendChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.sendMessage(mSendChatText.getText().toString());
            }
        });

        mPlayerListRecyclerView = v.findViewById(R.id.player_list_recycler_view);
        mPlayerListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mChatRecyclerView = v.findViewById(R.id.chat_list_recycler_view);
        mChatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    private void updateView() {
        if (mCurrentGame == null) { return; }
        mPlayerListAdapter = new PlayerListAdapter(mCurrentGame.getPlayers());
//        mChatAdapter = new ChatAdapter(mCurrentGame.getMessages());
        mPlayerListRecyclerView.setAdapter(mPlayerListAdapter);
    }

    @Override
    public void addMessage(Message message) {
        // FIXME: just put messages in the game object?
    }

    @Override
    public void updateGame(Game game) {
        mCurrentGame = game;
        updateView();
    }

    @Override
    public void startGame() {
        // FIXME: drop off for now; start the game!
    }

    @Override
    public void leaveGame() {
        // FIXME: return to game list fragment
    }

    @Override
    public void setPresenter(IGameLobbyPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }


    private class PlayerHolder extends RecyclerView.ViewHolder {

        public PlayerHolder(View itemView) {
            super(itemView);
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
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull PlayerHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
    private class MessageHolder extends RecyclerView.ViewHolder {

        public MessageHolder(View itemView) {
            super(itemView);
        }
    }
    private class ChatAdapter extends RecyclerView.Adapter<MessageHolder> {

        @NonNull
        @Override
        public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull MessageHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
