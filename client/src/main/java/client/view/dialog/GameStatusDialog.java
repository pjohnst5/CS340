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

public class GameStatusDialog extends DialogFragment {

    private RecyclerView _playerRecyclerView;
    private RecyclerView _cardRecyclerView;
    private RecyclerView _destCardRecyclerView;
    private Player _myPlayer;

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
        for (Player p : players) {
            if (p.getPlayerID().equals(playerId)) {
                _myPlayer = p;
                break;
            }
        }

        _playerRecyclerView = v.findViewById(R.id.game_status_stats_recycler_view);
        _playerRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        _playerRecyclerView.setAdapter(new PlayerAdapter(currentGame));

        Map<TrainColor, Integer> cardCounts = _myPlayer.countNumTrainCards();

        _cardRecyclerView = v.findViewById(R.id.game_status_card_recycler_view);
        _cardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        _cardRecyclerView.setAdapter(new CardAdapter(cardCounts));

        List<TrainCard> faceUpCards = ClientModel.getInstance().getCurrentGame().getTrainDeck().getFaceUpTrainCards();
        ImageView[] trainCards = {
                v.findViewById(R.id.game_status_face_up_card1),
                v.findViewById(R.id.game_status_face_up_card2),
                v.findViewById(R.id.game_status_face_up_card3),
                v.findViewById(R.id.game_status_face_up_card4),
                v.findViewById(R.id.game_status_face_up_card5),
        };

        for (int i = 0; i < faceUpCards.size(); i++) {
            TrainCard card = faceUpCards.get(i);
            switch (card.get_color()){
                case BLACK:
                    trainCards[i].setImageResource(R.drawable.train_card_black);
                    break;

                case BLUE:
                    trainCards[i].setImageResource(R.drawable.train_card_blue);
                    break;

                case GREEN:
                    trainCards[i].setImageResource(R.drawable.train_card_green);
                    break;

                case RED:
                    trainCards[i].setImageResource(R.drawable.train_card_red);
                    break;

                case YELLOW:
                    trainCards[i].setImageResource(R.drawable.train_card_yellow);
                    break;

                case PINK:
                    trainCards[i].setImageResource(R.drawable.train_card_purple);
                    break;

                case WHITE:
                    trainCards[i].setImageResource(R.drawable.train_card_white);
                    break;

                case ORANGE:
                    trainCards[i].setImageResource(R.drawable.train_card_orange);
                    break;

                case LOCOMOTIVE:
                    trainCards[i].setImageResource(R.drawable.train_card_loco);
                    break;
            }

        }

        _destCardRecyclerView = v.findViewById(R.id.game_status_dest_card_recycler_view);
        _destCardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        _destCardRecyclerView.setAdapter(new DestCardAdapter(_myPlayer.getDestCards()));

        ad.show();
        ad.getButton(Dialog.BUTTON_NEGATIVE).setVisibility(View.GONE);

        ad.hide();

        return ad;

    }

    private class PlayerHolder extends RecyclerView.ViewHolder {
        private TextView _playerName;
        private TextView _points;
        private TextView _trains;
        private TextView _trainCards;
        private TextView _destCards;
        private TextView _routes;
        public PlayerHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.player_status_list_item, parent, false));
            _playerName = itemView.findViewById(R.id.game_status_player_name);
            _points = itemView.findViewById(R.id.game_status_player_points);
            _trains = itemView.findViewById(R.id.game_status_player_trains);
            _trainCards = itemView.findViewById(R.id.game_status_player_train_cards);
            _destCards = itemView.findViewById(R.id.game_status_player_dest_cards);
            _routes = itemView.findViewById(R.id.game_status_player_routes);
        }

        public void bind(Player player, int numRoutes) {
            _playerName.setText(player.getDisplayName());
            _points.setText(Integer.toString(player.getPoints()));
            _trains.setText(Integer.toString(player.getTrainCars().getCount()));
            _trainCards.setText(Integer.toString(player.getNumTrainCards()));
            _destCards.setText(Integer.toString(player.getDestCards().size()));
            _routes.setText(Integer.toString(numRoutes));
            PlayerColor color = player.getColor();
            _playerName.setTextColor(ColorPicker.claimedColor(getResources(), color));
            if (player.getDisplayName().equals(_myPlayer.getDisplayName())) {
                itemView.setBackgroundColor(0xffbbbbbb);
//                ((View)_playerName.getParent()).setBackgroundColor(0xffbbbbbb);
            }
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
            holder.bind(player, _currentGame.getNumClaimedRoutes(player.getPlayerID()));
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

    private class DestCardHolder extends RecyclerView.ViewHolder{

        private final int CARD_LENGTH = 294;

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
