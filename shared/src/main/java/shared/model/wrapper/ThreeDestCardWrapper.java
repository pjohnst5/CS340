package shared.model.wrapper;

import java.util.ArrayList;
import java.util.List;

import shared.model.decks.DestCard;

public class ThreeDestCardWrapper {

    private int numCards;
    private DestCard card1;
    private DestCard card2;
    private DestCard card3;

    public ThreeDestCardWrapper(){ }

    public ThreeDestCardWrapper(List<DestCard> cards){
        setCards(cards);
    }

    public void setCards(List<DestCard> cards){
        numCards = cards.size();

        card1 = null;
        card2 = null;
        card3 = null;

        switch (numCards){
            case 3:
                card3 = cards.get(2);

            case 2:
                card2 = cards.get(1);

            case 1:
                card1 = cards.get(0);
        }
    }

    public List<DestCard> getDestCards(){
        ArrayList<DestCard> cards = new ArrayList<>();

        switch (numCards){
            case 3:
                cards.add(card3);

            case 2:
                cards.add(card2);

            case 1:
                cards.add(card1);

        }

        return cards;
    }
}
