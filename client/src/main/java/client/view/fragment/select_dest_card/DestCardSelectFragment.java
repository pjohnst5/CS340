package client.view.fragment.select_dest_card;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

    private List<DestCard> _cards;
    private Set<CardItemHolder> _selectedCards;
    private Set<CardItemHolder> _unselectedCards;

    private static boolean setup = true;

    private static final int CARD_WIDTH = 264;
    private static final int CARD_HEIGHT = 166;
    private final int CARD_LENGTH = 294;
    private boolean _cardsLoaded;


    private IDestCardSelectPresenter _presenter;
    private RecyclerView _cardsRecyclerView;
    private CardAdapter _cardAdapter;
    private LinearLayout _recyclerViewContainer;
    private TextView _overlayMessage;
    private Button _submitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_destination_card_select, container, false);

        _cards = new ArrayList<>();
        _cardAdapter = new CardAdapter();
        _recyclerViewContainer = v.findViewById(R.id.dest_card_linear_layout);
        _cardsRecyclerView = v.findViewById(R.id.dest_card_select_recycler_view);
        _cardsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        _cardsRecyclerView.setAdapter(_cardAdapter);

        _selectedCards = new HashSet<>();
        _unselectedCards = new HashSet<>();
        _cardsLoaded = false;

        _presenter = new DestCardSelectPresenter(this);
        setGridlayoutSpan();

        _overlayMessage = v.findViewById(R.id.dest_card_overlay_message);
        hideOverlayMessage();

        _submitButton = v.findViewById(R.id.dest_card_frag_select_cards);
        _submitButton.setEnabled(false);
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

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _presenter.destroy();
    }

    @Override
    public void disableSubmitButton() {
        _submitButton.setEnabled(false);
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

    private void setGridlayoutSpan() {

        ViewTreeObserver vto = _recyclerViewContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(() -> {
            int numColumns = _recyclerViewContainer.getWidth() / CARD_LENGTH;
            if (numColumns == 0) numColumns = 1;

            ((GridLayoutManager) _cardsRecyclerView.getLayoutManager()).setSpanCount(numColumns);
        });
    }

    @Override
    public boolean addCard(DestCard card) {
        if (_cards.contains(card)) return false;
        _cards.add(card);

        getActivity().runOnUiThread(() -> {
            _cardAdapter.notifyItemChanged(_cards.size()-1);
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

            //_presenter.init();
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

        public void disable(){
            itemView.setOnClickListener(null);
        }

        @Override
        public void onClick(View view) {
            if (_selectedCards.contains(this)){
                _selectedCards.remove(this);
                _unselectedCards.add(this);
                _cardBorder.setBackground(getResources().getDrawable(R.drawable.card_item_black));
            } else {
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
