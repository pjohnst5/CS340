package client.view.dialog;


import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pjohnst5icloud.tickettoride.R;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import client.model.ClientModel;
import client.util.ColorPicker;
import shared.enumeration.PlayerColor;
import shared.enumeration.TrainColor;
import shared.model.Game;
import shared.model.Player;
import shared.model.decks.DestCard;
import shared.model.decks.TrainCard;

public class GameOverDialog extends DialogFragment {

    private TextView _winnerTextView;
    private RecyclerView _playerRecyclerView;
    private Player _myPlayer;

    public static GameOverDialog newInstance() {
        return new GameOverDialog();
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
        for (Player p : players) {
            if (p.getPlayerID().equals(playerId)) {
                _myPlayer = p;
                break;
            }
        }

        _winnerTextView = v.findViewById(R.id.game_over_winner);
        _winnerTextView.setText(currentGame.getWinner() + " wins!"); // FIXME: implement

        _playerRecyclerView = v.findViewById(R.id.game_over_stats_recycler_view);
        _playerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        _playerRecyclerView.setAdapter(new PlayerAdapter(currentGame));

        ad.show();
        ad.getButton(Dialog.BUTTON_NEGATIVE).setVisibility(View.GONE);
        ad.hide();

        return ad;
    }

    private class PlayerHolder extends RecyclerView.ViewHolder {
        private TextView _playerName;
        private TextView _points;
        private RecyclerView _destCardRecyclerView;
        public PlayerHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.game_over_player_list_item, parent, false));
            _playerName = itemView.findViewById(R.id.game_over_player_name);
            _points = itemView.findViewById(R.id.game_over_player_points);
            _destCardRecyclerView = itemView.findViewById(R.id.game_over_dest_card_recycler_view);
            _destCardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),
                    LinearLayoutManager.HORIZONTAL, false));
        }

        public void bind(Player player) {
            _playerName.setText(player.getDisplayName());
            _points.setText(Integer.toString(player.getPoints()));
            DestCardAdapter adapter = new DestCardAdapter(player.getDestCards());
            _destCardRecyclerView.setAdapter(adapter);
        }
    }

    private class PlayerAdapter extends RecyclerView.Adapter<PlayerHolder> {
        private Game _currentGame;
        private List<Player> _players;
        public PlayerAdapter(Game currentGame) {
            _currentGame = currentGame;
            _players = _currentGame.getPlayers();
        }

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

    private class DestCardHolder extends RecyclerView.ViewHolder{
        private DestCard _card;
        private RelativeLayout _cardBorder;
        private ImageView _cardHolder;
        private TextView _destinationTitle;
        private TextView _points;

        DestCardHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.card_item, parent, false));

            _cardHolder = itemView.findViewById(R.id.card_item_image_view);
            _cardBorder = itemView.findViewById(R.id.card_item_border);
            _destinationTitle = itemView.findViewById(R.id.card_item_destination_text);
            _points = itemView.findViewById(R.id.dest_card_point_value);
        }

        public void bind(DestCard card){
            _card = card;

            int points = card.get_worth();
            String destinationName = card.toString();

            _destinationTitle.setText(destinationName);
            _points.setText(Integer.toString(points));
        }

        public DestCard getDestCard(){
            return this._card;
        }
    }

    private class DestCardAdapter extends RecyclerView.Adapter<DestCardHolder> {
        private List<DestCard> _cards;

        public DestCardAdapter(List<DestCard> cards) {
            _cards = cards;
        }

        @NonNull
        @Override
        public DestCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new DestCardHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull DestCardHolder holder, int position) {
            DestCard card = _cards.get(position);
            holder.bind(card);
        }

        @Override
        public int getItemCount() {
            return _cards.size();
        }
    }
}
