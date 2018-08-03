package client.view.fragment.select_dest_card;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import client.presenter.select_dest_card.DestCardSelectPresenter;
import client.presenter.select_dest_card.IDestCardSelectPresenter;
import client.view.fragment.game_map.GameMapFragment;
import shared.model.decks.DestCard;

public class DestCardSelectFragment extends Fragment implements IDestCardSelectView {

    private final int CARD_LENGTH = 284;
    private static final int CARD_WIDTH = 264;
    private static final int CARD_HEIGHT = 166;

    private List<DestCard> _cards;
    private Set<CardItemHolder> _selectedCards;
    private Set<CardItemHolder> _unselectedCards;

    private Button _submitButton;
    private CardAdapter _cardAdapter;
    private TextView _overlayMessage;
    private TextView _numCardsReqText;
    private RecyclerView _cardsRecyclerView;
    private LinearLayout _recyclerViewContainer;
    private IDestCardSelectPresenter _presenter;

    private boolean _viewInitialized = false;
    private String _cardsWaitingRequest;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_destination_card_select, container, false);

        // Initialize Simple Members
        _cards = new ArrayList<>();
        _cardAdapter = new CardAdapter();
        _selectedCards = new HashSet<>();
        _unselectedCards = new HashSet<>();
        _presenter = new DestCardSelectPresenter(this);

        // Initialize View Members
        _recyclerViewContainer = v.findViewById(R.id.dest_card_linear_layout);
        _cardsRecyclerView = v.findViewById(R.id.dest_card_select_recycler_view);
        _cardsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        _cardsRecyclerView.setAdapter(_cardAdapter);
        _overlayMessage = v.findViewById(R.id.dest_card_overlay_message);
        _submitButton = v.findViewById(R.id.dest_card_frag_select_cards);
        _numCardsReqText = v.findViewById(R.id.dest_card_frag_subtitle);


        // Modify View Members
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        float density = getResources().getDisplayMetrics().density;
        setGridlayoutSpan(density);

        hideOverlayMessage();
        _submitButton.setEnabled(false);

        // Set View OnClickListeners
        _submitButton.setOnClickListener((view) -> {

            if (_selectedCards.size() < _presenter.getNumCardsRequired()) return;

            List<DestCard> selectedCards = new ArrayList<>();
            for (CardItemHolder holder : _selectedCards){
                selectedCards.add(holder.getDestCard());
            }

            List<DestCard> unselectedCards = new ArrayList<>();
            for (CardItemHolder holder : _unselectedCards){
                unselectedCards.add(holder.getDestCard());
            }

            _presenter.submitData(selectedCards, unselectedCards);
        });

        _viewInitialized = true;
        if (_cardsWaitingRequest != null){
            setNumCardsRequiredText(_cardsWaitingRequest);
            _cardsWaitingRequest = null;
        }

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        _viewInitialized = false;
        _cardsWaitingRequest = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        _presenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        _presenter.pause();
    }

    @Override
    public void disableSubmitButton() {
        getActivity().runOnUiThread(() -> _submitButton.setEnabled(false));
    }

    @Override
    public void showOverlayMessage(String message) {
        getActivity().runOnUiThread(() -> {
            _overlayMessage.setVisibility(View.VISIBLE);
            _overlayMessage.setText(message);
        });
    }

    @Override
    public void hideOverlayMessage() {
        getActivity().runOnUiThread(() -> _overlayMessage.setVisibility(View.GONE));
    }

    @Override
    public void disableCardSelect() {
        for (CardItemHolder card : _selectedCards){
            card.disable();
        }

        for (CardItemHolder card : _unselectedCards){
            card.disable();
        }
    }

    @Override
    public void switchToGameMap(){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.game_content_container, new GameMapFragment())
        .commit();
    }

    private void setGridlayoutSpan(float dpDensity) {

        ViewTreeObserver vto = _recyclerViewContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(() -> {

            float dpWidth = _recyclerViewContainer.getWidth() / dpDensity;
            int numColumns = (int)Math.floor(dpWidth / CARD_LENGTH);
            if (numColumns == 0) numColumns = 1;

            ((GridLayoutManager) _cardsRecyclerView.getLayoutManager()).setSpanCount(numColumns);
        });
    }

    @Override
    public void setNumCardsRequiredText(String number) {
        getActivity().runOnUiThread(() -> {
            if (_viewInitialized) {
                _numCardsReqText.setText(number);
            } else {
                _cardsWaitingRequest = number;
            }
        });
    }

    @Override
    public boolean addCard(DestCard card) {
        if (_cards.contains(card)) return false;
        _cards.add(card);

        getActivity().runOnUiThread(() -> {
            _cardAdapter.notifyDataSetChanged();
        });
        return true;
    }

    private class CardItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final int CARD_LENGTH = 294;

        private DestCard _card;
        private RelativeLayout _cardBorder;
        private ImageView _cardHolder;
        private TextView _destinationTitle;
        private TextView _points;

        CardItemHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.card_item, parent, false));

            _cardHolder = itemView.findViewById(R.id.card_item_image_view);
            _cardBorder = itemView.findViewById(R.id.card_item_border);
            _destinationTitle = itemView.findViewById(R.id.card_item_destination_text);
            _points = itemView.findViewById(R.id.dest_card_point_value);

            itemView.setOnClickListener(this);
            _unselectedCards.add(this);

        }

        public void bind(DestCard card){

            if (card == null) {
                _unselectedCards.remove(this);
                return;
            }

            _card = card;

            int points = card.get_worth();
            String destinationName = card.toString();

            _destinationTitle.setText(destinationName);
            _points.setText(Integer.toString(points));

        }

        public DestCard getDestCard(){
            return this._card;
        }

        public void disable(){
            itemView.setOnClickListener(null);
        }

        @Override
        public void onClick(View view) {
            if (_selectedCards.size() == 0){
                //_selectedCards.remove(this);
                _selectedCards.add(this);
                _unselectedCards.remove(this);
                _cardBorder.setBackground(getResources().getDrawable(R.drawable.card_item_blue));
            } else if (_selectedCards.size() > 0 && _selectedCards.contains(this)){
                //_selectedCards.add(this);
                _selectedCards.remove(this);
                _unselectedCards.add(this);
                _cardBorder.setBackground(getResources().getDrawable(R.drawable.card_item_black));
            } else if (_selectedCards.size() > 0 && !_selectedCards.contains(this)){
                _selectedCards.add(this);
                _unselectedCards.remove(this);
                _cardBorder.setBackground(getResources().getDrawable(R.drawable.card_item_blue));
            }

            if (_selectedCards.size() < _presenter.getNumCardsRequired()){
                _submitButton.setEnabled(false);
            } else {
                _submitButton.setEnabled(true);
            }
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
            DestCard card = _cards.get(position);
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
