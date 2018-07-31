package client.view.fragment.train_card_select;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import client.presenter.train_card_select.ITrainCardSelectPresenter;
import client.presenter.train_card_select.TrainSelectPresenter;
import client.view.fragment.game_map.GameMapFragment;
import shared.enumeration.TrainColor;
import shared.model.decks.TrainCard;

public class TrainCardSelectFragment extends Fragment implements ITrainCardSelectView {

    private final int CARD_LENGTH = 294;

    private List<TrainCard> _cards;
    private Set<CardItemHolder> _selectedCards;
    private Set<CardItemHolder> _unselectedCards;

    private Button _closeButton;
    private Button _submitButton;
    private CardAdapter _cardAdapter;
    private RecyclerView _cardsRecyclerView;
    private LinearLayout _recyclerViewContainer;
    private ITrainCardSelectPresenter _presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_train_card_select, container, false);

        // Initialize Simple Members
        _cards = new ArrayList<>();
        _cardAdapter = new CardAdapter();
        _selectedCards = new HashSet<>();
        _unselectedCards = new HashSet<>();

        // Initialize View Members
        _recyclerViewContainer = v.findViewById(R.id.train_card_linear_layout);
        _cardsRecyclerView = v.findViewById(R.id.train_card_select_recycler_view);
        _submitButton = v.findViewById(R.id.train_card_frag_select_cards);
        _closeButton = v.findViewById(R.id.train_card_close_dialog);


        // Modify View Members
        _cardsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        _cardsRecyclerView.setAdapter(_cardAdapter);
        setGridLayoutSpan();
        _submitButton.setEnabled(false);

        // Set View OnClickListeners
        _submitButton.setOnClickListener((view) -> {

            if (_selectedCards.size() > 0) {

                TrainCard selectedCard = null;
                for (CardItemHolder holder : _selectedCards){
                    selectedCard = holder.getTrainCard();
                }

                _presenter.submitData(selectedCard);

            }

        });
        _closeButton.setOnClickListener((view) -> _presenter.switchToGameMap());

        _presenter = new TrainSelectPresenter(this);
        return v;
    }

    @Override
    public void onResume(){
        super.onResume();
        _presenter.resume();
    }

    @Override
    public void onPause(){
        super.onPause();
        _presenter.pause();
    }

    @Override
    public void setEnabledCloseDialog(boolean value) {

        getActivity().runOnUiThread(() -> {
            if (value){
                _closeButton.setVisibility(View.VISIBLE);
            } else {
                _closeButton.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void setCardSelectEnabled(boolean value) {
        getActivity().runOnUiThread(() -> {
            if (value) {
                for (CardItemHolder card : _selectedCards) {
                    card.disable();
                    _unselectedCards.add(card);
                    _selectedCards.remove(card);
                }
            } else {

                for (CardItemHolder card : _selectedCards){
                    card.enable();
                }

                for (CardItemHolder card : _unselectedCards){
                    card.enable();
                }
            }
        });

    }

    @Override
    public void setSelectionSubmitEnabled(boolean value) {
        getActivity().runOnUiThread(() -> _submitButton.setEnabled(value));
    }

    private void disableCardSelect() {
        for (CardItemHolder card : _selectedCards) {
            card.disable();
        }

        for (CardItemHolder card : _unselectedCards) {
            card.disable();
        }
    }

    @Override
    public void switchToGameMap() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.game_content_container, new GameMapFragment())
                .commit();
    }

    private void setGridLayoutSpan(){

        ViewTreeObserver vto = _recyclerViewContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(() -> {
            int numColumns = _recyclerViewContainer.getWidth() / CARD_LENGTH;
            if (numColumns == 0) numColumns = 1;

            ((GridLayoutManager) _cardsRecyclerView.getLayoutManager()).setSpanCount(numColumns);
        });
    }

    @Override
    public void clearCards() {
        _cards.clear();
        getActivity().runOnUiThread(() -> _cardAdapter.notifyDataSetChanged());
    }

    @Override
    public boolean addCard(TrainCard card) {
        if (_cards.contains(card)) return false;
        _cards.add(card);

        getActivity().runOnUiThread(() -> _cardAdapter.notifyDataSetChanged());
        return true;
    }

    private class CardItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TrainCard _card;

        private RelativeLayout _cardBorder;
        private ImageView _cardHolder;
        CardItemHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.card_item, parent, false));

            _cardHolder = itemView.findViewById(R.id.card_item_image_view);
            _cardBorder = itemView.findViewById(R.id.card_item_border);

            itemView.setOnClickListener(this);
            _unselectedCards.add(this);

        }

        public void bind(TrainCard card){

            if (card == null) {
                _unselectedCards.remove(this);
                return;
            }

            _card = card;
            switch (card.get_color()) {
                case BLACK:
                    _cardHolder.setImageResource(R.drawable.train_card_black);
                    break;

                case BLUE:
                    _cardHolder.setImageResource(R.drawable.train_card_blue);
                    break;

                case GREEN:
                    _cardHolder.setImageResource(R.drawable.train_card_green);
                    break;

                case RED:
                    _cardHolder.setImageResource(R.drawable.train_card_red);
                    break;

                case YELLOW:
                    _cardHolder.setImageResource(R.drawable.train_card_yellow);
                    break;

                case PINK:
                    _cardHolder.setImageResource(R.drawable.train_card_purple);
                    break;

                case WHITE:
                    _cardHolder.setImageResource(R.drawable.train_card_white);
                    break;

                case ORANGE:
                    _cardHolder.setImageResource(R.drawable.train_card_orange);
                    break;

                case LOCOMOTIVE:
                    _cardHolder.setImageResource(R.drawable.train_card_loco);
                    break;

                default:
                    _cardHolder.setImageResource(R.drawable.train_card_back);
                    break;

            }
        }

        public TrainCard getTrainCard(){
            return this._card;
        }

        public void disable() {
            itemView.setOnClickListener(null);
            _cardBorder.setBackground(getResources().getDrawable(R.drawable.card_item_black));
        }

        public void enable(){
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            for (CardItemHolder card : _selectedCards){
                card._cardBorder.setBackground(getResources().getDrawable(R.drawable.card_item_black));
                _selectedCards.remove(card);
                _unselectedCards.add(card);
            }

            _selectedCards.add(this);
            _cardBorder.setBackground(getResources().getDrawable(R.drawable.card_item_blue));
            _submitButton.setEnabled(true);

        }

    }
    private class CardAdapter extends RecyclerView.Adapter<CardItemHolder> {

        @NonNull
        @Override
        public CardItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new CardItemHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CardItemHolder holder, int position) {
            TrainCard card = _cards.get(position);
            holder.bind(card);
        }

        @Override
        public int getItemCount() {
            return _cards.size();
        }

    }

    @Override
    public void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show());
    }
}
