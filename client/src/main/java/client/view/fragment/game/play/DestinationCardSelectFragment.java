package client.view.fragment.game.play;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pjohnst5icloud.tickettoride.R;

import java.util.ArrayList;
import java.util.List;

import shared.enumeration.CityName;
import shared.model.City;
import shared.model.decks.DestCard;
import shared.model.decks.ICard;

public class DestinationCardSelectFragment extends Fragment implements IDestinationCardSelectView {

    private List<ICard> _cards;

    private static final int CARD_WIDTH = 264;
    private static final int CARD_HEIGHT = 166;

    private void testCode(){
        _cards = new ArrayList<>();
        _cards.add(new DestCard(new City(CityName.LOS_ANGELES), new City(CityName.NEW_YORK_CITY), 21));
        _cards.add(new DestCard(new City(CityName.DULUTH), new City(CityName.HOUSTON), 8));
        _cards.add(new DestCard(new City(CityName.SAULT_STE_MARIE), new City(CityName.NASHVILLE), 8));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_destination_card_select, container, false);



        return v;
    }

/*
    private class CardItemHolder extends RecyclerView.ViewHolder {

        private DestCard card;


        CardItemHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.card_item))

            int width = parent.getWidth();
            int numColumns = width / 266;

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            params.height = h;
            itemView.setLayoutParams(params);

            return

        }

    }
*/


//    @Override
//    public void setPresenter(ILoginPresenter presenter) {
//        mPresenter = presenter;
//    }

//
//    @Override
//    public void showToast(String message) {
//        getActivity().runOnUiThread(() -> Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show());
//    }
}
