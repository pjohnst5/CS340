package shared.model.wrapper;

import java.util.ArrayList;
import java.util.List;

import shared.model.decks.DestCard;

public class ThreeDestCardWrapper {

    private DestCard card1;
    private DestCard card2;
    private DestCard card3;

    public void setCards(List<DestCard> cards){
        card1 = cards.get(0);
        card2 = cards.get(1);
        card3 = cards.get(2);
    }

    public List<DestCard> getDestCards(){
        ArrayList<DestCard> cards = new ArrayList<>();

        cards.add(card1);
        cards.add(card2);
        cards.add(card3);

        return cards;
    }
}
