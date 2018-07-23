package client.view.fragment.game.play;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import client.presenter.game.play.DestinationCardSelectPresenter;
import client.presenter.game.play.IDestinationCardSelectPresenter;
import shared.model.decks.DestCard;

public class DestinationCardSelectFragment extends Fragment implements IDestinationCardSelectView {

    private List<DestCard> _cards;
    private Set<CardItemHolder> _selectedCards;

    private static final int CARD_WIDTH = 264;
    private static final int CARD_HEIGHT = 166;
    private final int CARD_LENGTH = 294;


    private IDestinationCardSelectPresenter _presenter;
    private RecyclerView _cardsRecyclerView;
    private CardAdapter _cardAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_destination_card_select, container, false);

        _cards = new ArrayList<>();
        _cardAdapter = new CardAdapter();
        _cardsRecyclerView = v.findViewById(R.id.dest_card_select_recycler_view);
        _cardsRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        _cardsRecyclerView.setAdapter(_cardAdapter);

        _selectedCards = new HashSet<>();

        _presenter = new DestinationCardSelectPresenter(this);

        return v;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        _presenter.destroy();
    }

    private class CardItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final int CARD_LENGTH = 294;

        private DestCard _card;
        private int _parentWidth;
        private int _numColumns;

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

            _parentWidth = parent.getWidth();
            _numColumns = _parentWidth / CARD_LENGTH;

            ((GridLayoutManager)_cardsRecyclerView.getLayoutManager()).setSpanCount(_numColumns);
            itemView.setOnClickListener(this);
        }

        public void bind(DestCard card){

            _card = card;

            int points = card.get_worth();
            String destinationName = card.toString();

            _destinationTitle.setText(destinationName);
            _points.setText(Integer.toString(points));

        }



        @Override
        public void onClick(View view) {
            if (_selectedCards.contains(this)){
                _selectedCards.remove(this);
                _cardBorder.setBackground(getResources().getDrawable(R.drawable.card_item_black));
            } else {
                _selectedCards.add(this);
                _cardBorder.setBackground(getResources().getDrawable(R.drawable.card_item_blue));
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
