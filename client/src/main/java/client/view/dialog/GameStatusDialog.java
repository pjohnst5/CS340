package client.view.dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pjohnst5icloud.tickettoride.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import client.model.ClientModel;
import client.util.ColorPicker;
import shared.enumeration.PlayerColor;
import shared.enumeration.TrainColor;
import shared.model.Game;
import shared.model.Player;
import shared.model.decks.TrainCard;

public class GameStatusDialog extends DialogFragment {

    private RecyclerView _playerRecyclerView;
    private RecyclerView _cardRecyclerView;

    public static GameStatusDialog newInstance() {
        return new GameStatusDialog();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_game_status, null);

        Button closeDialogButton = v.findViewById(R.id.game_status_close_dialog);

        AlertDialog ad = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {})
                .create();

        closeDialogButton.setOnClickListener(
                (view) -> ad.getButton(Dialog.BUTTON_NEGATIVE).performClick()
        );

        // should probably take these as Bundle arguments instead...
        ClientModel model = ClientModel.getInstance();
        Game currentGame = model.getCurrentGame();
        List<Player> players = currentGame.getPlayers();
        String playerId = model.getUser().get_playerId();
        Player myPlayer = null;
        for (Player p : players) {
            if (p.getPlayerID().equals(playerId)) {
                myPlayer = p;
                break;
            }
        }

        _playerRecyclerView = v.findViewById(R.id.game_status_stats_recycler_view);
        _playerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        _playerRecyclerView.setAdapter(new PlayerAdapter(players));

        Map<TrainColor, Integer> cardCounts = countNumCards(myPlayer.getTrainCards());

        _cardRecyclerView = v.findViewById(R.id.game_status_card_recycler_view);
        _cardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        _cardRecyclerView.setAdapter(new CardAdapter(cardCounts));

        ad.show();
        ad.getButton(Dialog.BUTTON_NEGATIVE).setVisibility(View.GONE);

        ad.hide();

        return ad;

    }

    private class PlayerHolder extends RecyclerView.ViewHolder {
        private TextView _playerName;
        private TextView _points;
        private TextView _trains;
        private TextView _cards;
        private TextView _routes;
        public PlayerHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.player_status_list_item, parent, false));
            _playerName = itemView.findViewById(R.id.game_status_player_name);
            _points = itemView.findViewById(R.id.game_status_player_points);
            _trains = itemView.findViewById(R.id.game_status_player_trains);
            _cards = itemView.findViewById(R.id.game_status_player_cards);
            _routes = itemView.findViewById(R.id.game_status_player_routes);
        }

        public void bind(Player player) {
            _playerName.setText(player.getDisplayName());
            _points.setText(Integer.toString(player.getPoints()));
            _trains.setText(Integer.toString(player.getTrainCars().getCount()));
            _cards.setText(Integer.toString(player.getNumTrainCards()));
            _routes.setText(Integer.toString(player.getNumRoutes()));
            PlayerColor color = player.getColor();
            _playerName.setTextColor(ColorPicker.getRouteColor(getResources(), color));
        }
    }

    private class PlayerAdapter extends RecyclerView.Adapter<PlayerHolder> {
        private List<Player> _players;
        public PlayerAdapter(List<Player> players) {_players = players;}

        @NonNull
        @Override
        public PlayerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new PlayerHolder(inflater, parent);
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

    private class CardHolder extends RecyclerView.ViewHolder {
        private LinearLayout _container;
        private TextView _countView;
        public CardHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.card_count_list_item, parent, false));
            _container = itemView.findViewById(R.id.card_count_container);
            _countView = itemView.findViewById(R.id.card_count);
        }

        public void bind(Map.Entry<TrainColor, Integer> cardCount) {
            _container.setBackgroundColor(ColorPicker.getRouteColor(getResources(), cardCount.getKey()));
            _countView.setText(Integer.toString(cardCount.getValue()));
        }
    }

    private class CardAdapter extends RecyclerView.Adapter<CardHolder> {
        private Map<TrainColor, Integer> _counts;
        public CardAdapter(Map<TrainColor, Integer> counts) {
            _counts = counts;
        }

        @NonNull
        @Override
        public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new CardHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CardHolder holder, int position) {
            Iterator<Map.Entry<TrainColor, Integer>> it = _counts.entrySet().iterator();
            for (int i = 0; i < position; i++) {
                it.next();
            }
            Map.Entry<TrainColor, Integer> cardCount = it.next();
            holder.bind(cardCount);
        }

        @Override
        public int getItemCount() {
            return _counts.size();
        }
    }

    private Map<TrainColor, Integer> countNumCards(List<TrainCard> cards) {
        Map<TrainColor, Integer> counts = new HashMap<>();
        for (TrainColor color : TrainColor.values()) {
            counts.put(color, 0);
        }
        for (TrainCard c : cards) {
            TrainColor color = c.get_color();
            int prevCount = counts.get(color);
            prevCount++;
            counts.put(color, prevCount);
        }
        return counts;
    }
}
