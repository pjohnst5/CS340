package client.view.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pjohnst5icloud.tickettoride.R;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import client.facade.GameMapService;
import client.model.ClientModel;
import client.util.ColorPicker;
import shared.enumeration.TrainColor;
import shared.exception.InvalidGameException;
import shared.model.Game;
import shared.model.Pair;
import shared.model.Player;
import shared.model.Route;
import shared.model.decks.TrainCard;

public class ClaimRouteDialog extends DialogFragment {

    private static final String ARG_ROUTE = "route";
    private static final String ARG_PLAYER = "player";
    public static final String EXTRA_SELECTED_CARDS = "client.view.dialog.JoinGameDialog.selectedCards";

    private Button _closeButton;
    private Button _cancelButton;
    private Button _acceptButton;
    private TextView _routeDetailsView;
    private RecyclerView _cardRecyclerView;

    private Route _route;
    private Player _player;
    private Map<TrainColor, Integer> _cards;
    private Map<TrainColor, Integer> _selectedCards;


    public static ClaimRouteDialog newInstance(UUID routeId, String playerId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ROUTE, routeId);
        args.putString(ARG_PLAYER, playerId);

        ClaimRouteDialog dialog = new ClaimRouteDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_claim_route, null);

        Bundle args = getArguments();
        Game game = ClientModel.getInstance().getCurrentGame();
        String playerId = args.getString(ARG_PLAYER);
        UUID routeId = (UUID) args.getSerializable(ARG_ROUTE);
        try {
            _player = game.getPlayer(playerId);
        } catch (InvalidGameException e) {
            throw new RuntimeException(e);
        }
        _route = game.getMap().getRoute(routeId);
        _cards = _player.countNumTrainCards();
        _selectedCards = getInitialValues(_cards, _route);

        _closeButton = v.findViewById(R.id.claim_route_close_dialog);
        _cancelButton = v.findViewById(R.id.claim_route_cancel_dialog);
        _acceptButton = v.findViewById(R.id.claim_route_accept_dialog);

        _cardRecyclerView = v.findViewById(R.id.claim_route_card_recycler);
        _cardRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        _cardRecyclerView.setAdapter(new TrainCardAdapter(_cards, _selectedCards));

        _routeDetailsView = v.findViewById(R.id.claim_route_route_name);

        AlertDialog ad = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setCancelable(true)
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {})
                .setPositiveButton(R.string.ok, ((dialogInterface, i) -> {
                    sendResult(Activity.RESULT_OK, _selectedCards);
                }))
                .create();


        _closeButton.setOnClickListener(
                (view) -> ad.getButton(Dialog.BUTTON_NEGATIVE).performClick()
        );
        _cancelButton.setOnClickListener(
                (view) -> ad.getButton(Dialog.BUTTON_NEGATIVE).performClick()
        );
        _acceptButton.setOnClickListener(
                (view) -> ad.getButton(Dialog.BUTTON_POSITIVE).performClick()
        );

        updateDisplayCounts();

        ad.show();
        ad.getButton(Dialog.BUTTON_POSITIVE).setVisibility(View.GONE);
        ad.getButton(Dialog.BUTTON_NEGATIVE).setVisibility(View.GONE);
        ad.hide();

        return ad;
    }

    private void updateDisplayCounts() {
        StringBuilder sb = new StringBuilder();
        sb.append(ColorPicker.convertCityNameToString(_route.get_source()));
        sb.append(" - ");
        sb.append(ColorPicker.convertCityNameToString(_route.get_dest()));
        sb.append("   (");
        sb.append(ColorPicker.trainCardColorName(getResources(), _route.get_color()));
        sb.append(")   ");
        int totalCardCount = countCards(_selectedCards.values());
        sb.append(totalCardCount).append("/").append(_route.get_pathLength());
        _routeDetailsView.setText(sb.toString());
        List<TrainCard> cards = _player.getCardsFromCounts(_selectedCards);
        GameMapService service;
        try {
            service = new GameMapService();
        } catch (InvalidGameException e) {
            throw new RuntimeException(e);
        }
        if (service.canBeClaimed(_route, cards)) {
            _routeDetailsView.setTextColor(0xff000000);
            _acceptButton.setEnabled(true);
        } else {
            _routeDetailsView.setTextColor(getResources().getColor(R.color.red));
            _acceptButton.setEnabled(false);
        }
    }

    private void sendResult(int resultCode, Map<TrainColor, Integer> selectedCards) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_SELECTED_CARDS, (HashMap)selectedCards);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    private class TrainCardHolder extends RecyclerView.ViewHolder {
        private LinearLayout _trainColorContainer;
//        private TextView _trainColorView;
        private TextView _numCardsView;
        private Button _minusButton;
        private Button _plusButton;
        private TextView _indicatorView;

        private int _maxCards;
        private int _numCards;
        private TrainColor _trainColor;
        public TrainCardHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.claim_route_list_item, parent, false));
            _trainColorContainer = itemView.findViewById(R.id.claim_route_color_container);
//            _trainColorView = itemView.findViewById(R.id.list_item_train_card_color);
            _numCardsView = itemView.findViewById(R.id.list_item_select_num_cards);
            _minusButton = itemView.findViewById(R.id.claim_route_minus_button);
            _plusButton = itemView.findViewById(R.id.claim_route_plus_button);
            _indicatorView = itemView.findViewById(R.id.list_item_selected_indicator);
            _minusButton.setOnClickListener((view) -> {
                decrCount();
            });
            _plusButton.setOnClickListener((view) -> {
                incrCount();
            });
        }
        public void bind(Pair<TrainColor, Integer> cards, int initialValue) {
            Bitmap bm = BitmapFactory.decodeResource(getResources(), ColorPicker.trainCardDrawable(cards.getKey()));
            Bitmap modifiedBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), ColorPicker.spToPx(getResources(), 24));
            _trainColorContainer.setBackground(new BitmapDrawable(getResources(), modifiedBm));
            _trainColor = cards.getKey();
//            _trainColorView.setText(ColorPicker.trainCardColorName(getResources(), _trainColor));
            _numCards = initialValue;
            _maxCards = cards.getValue();
            _numCardsView.setText(Integer.toString(_numCards));
            refreshText();
        }
        private void refreshText() {
            _numCardsView.setText(Integer.toString(_numCards));
            _indicatorView.setText(Integer.toString(_numCards) + "/" + Integer.toString(_maxCards));
        }
        private void decrCount() {
            if (_numCards > 0) {
                _numCards--;
                onValueChange();
            }
        }
        private void incrCount() {
            if (_numCards < _maxCards) {
                _numCards++;
                onValueChange();
            }
        }
        public void onValueChange() {
            _selectedCards.put(_trainColor, _numCards);
            refreshText();
            updateDisplayCounts();
        }
    }

    private class TrainCardAdapter extends RecyclerView.Adapter<TrainCardHolder> {
        private Map<TrainColor, Integer> _playerCards;
        private Map<TrainColor, Integer> _initialValues;
        public TrainCardAdapter(Map<TrainColor, Integer> cards, Map<TrainColor, Integer> initial) {
            _playerCards = cards;
            _initialValues = initial;
        }
        @NonNull
        @Override
        public TrainCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new TrainCardHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TrainCardHolder holder, int position) {
            Iterator<Map.Entry<TrainColor, Integer>> it = _playerCards.entrySet().iterator();
            for (int i = 0; i < position; i++) {
                it.next();
            }
            Map.Entry<TrainColor, Integer> cardCount = it.next();
            Pair<TrainColor, Integer> cardCountPair = new Pair<>(cardCount.getKey(), cardCount.getValue());
            holder.bind(cardCountPair, _initialValues.get(cardCount.getKey()));
        }

        @Override
        public int getItemCount() {
            return _playerCards.size();
        }
    }

    private Map<TrainColor, Integer> getInitialValues(Map<TrainColor, Integer> cards, Route route) {
        Map<TrainColor, Integer> initialValues = new HashMap<>();
        for (TrainColor color : cards.keySet()) {
            initialValues.put(color, 0);
        }

        TrainColor routeColor = route.get_color();
        if(routeColor == TrainColor.GRAY) {
            return initialValues;
        }

        if (!cards.containsKey(routeColor)) {
            return initialValues;
        }

        int matchingTrainColorNum = cards.get(routeColor);
        int amountNeeded = route.get_pathLength();
        if (matchingTrainColorNum >= amountNeeded) {
            initialValues.put(routeColor, amountNeeded);
            return initialValues;
        }

        initialValues.put(routeColor, matchingTrainColorNum);
        int amountShort = amountNeeded - matchingTrainColorNum;
        int locomotiveCount = cards.get(TrainColor.LOCOMOTIVE);
        if (locomotiveCount >= amountShort) {
            initialValues.put(TrainColor.LOCOMOTIVE, amountShort);
            return initialValues;
        }

        initialValues.put(TrainColor.LOCOMOTIVE, locomotiveCount);
        return initialValues;
    }

    private int countCards(Collection<Integer> collection) {
        int returnVal = 0;
        for (Integer i : collection) {
            returnVal += i;
        }
        return returnVal;
    }
}
