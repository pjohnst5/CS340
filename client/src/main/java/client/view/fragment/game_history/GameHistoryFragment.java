package client.view.fragment.game_history;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import client.presenter.game_history.GameHistoryPresenter;
import client.presenter.game_history.IGameHistoryPresenter;
import client.view.fragment.SidebarFragment;
import shared.enumeration.PlayerColor;
import shared.model.GameAction;
import shared.model.Player;

public class GameHistoryFragment extends SidebarFragment implements IGameHistoryView {

    private List<GameAction> _actions;
    private GameHistoryAdapter _historyAdapter;
    private IGameHistoryPresenter _presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_history, container, false);

        setupSidebarButtons(ButtonType.GAME_HISTORY);

        // Initialize Simple Members
        _actions = new ArrayList<>();
        _historyAdapter = new GameHistoryAdapter();
        _presenter = new GameHistoryPresenter(this);

        // Initialize View Members
        RecyclerView _historyRecyclerView = v.findViewById(R.id.game_history_recycler_view);

        // Modify View Members
        _historyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ((LinearLayoutManager)_historyRecyclerView.getLayoutManager()).setReverseLayout(true);
        _historyRecyclerView.setAdapter(_historyAdapter);

        return v;
    }

    @Override
    public boolean addAction(GameAction action){
        if (_actions.contains(action)) return false;

        _actions.add(action);
        Collections.sort(_actions, GameAction.getDescendingComparator());

        getActivity().runOnUiThread(() -> _historyAdapter.notifyDataSetChanged());
        return true;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        _presenter.destroy();
    }

    @Override
    public void showToast(String message) {
        getActivity().runOnUiThread(()-> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
    }

    private class ActionItemHolder extends RecyclerView.ViewHolder {

        private TextView _description;
        private TextView _timestamp;
        private TextView _nameDisplay;
        private RelativeLayout _container;

        ActionItemHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.game_action_item, parent, false));

            _description = itemView.findViewById(R.id.game_action_item_description);
            _timestamp = itemView.findViewById(R.id.game_action_item_timestamp);
            _container = itemView.findViewById(R.id.game_action_item_parent);
            _nameDisplay = itemView.findViewById(R.id.game_action_item_display_name);
        }

        public void bind(GameAction action){

            Player player = _presenter.getPlayerByDisplayName(action.get_displayName());

            _description.setText(action.get_actionDescription());
            _timestamp.setText(action.getTimestamp().toString());
            _nameDisplay.setText(action.get_displayName());

            PlayerColor color = player.getColor();

            switch (color){
                case BLACK:
                    _container.setBackground(getResources().getDrawable(R.drawable.chat_list_item_black));
                    break;

                case BLUE:
                    _container.setBackground(getResources().getDrawable(R.drawable.chat_list_item_blue));
                    break;

                case GREEN:
                    _container.setBackground(getResources().getDrawable(R.drawable.chat_list_item_green));
                    break;

                case RED:
                    _container.setBackground(getResources().getDrawable(R.drawable.chat_list_item_red));
                    break;

                case YELLOW:
                    _container.setBackground(getResources().getDrawable(R.drawable.chat_list_item_yellow));
                    break;
            }
        }

    }

    private class GameHistoryAdapter extends RecyclerView.Adapter<ActionItemHolder> {

        @NonNull
        @Override
        public ActionItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new ActionItemHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ActionItemHolder holder, int position) {
            GameAction action = _actions.get(position);
            holder.bind(action);
        }

        @Override
        public int getItemCount() {
            return _actions.size();
        }
    }
}
