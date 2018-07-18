package shared.model.decks;

import java.util.ArrayList;
import java.util.List;

import shared.exception.DeckException;
import shared.model.City;

public class DestCardDeck {
    private List<DestCard> _cards;

    public DestCardDeck()
    {
        _cards = new ArrayList<>();
        _cards.add(new DestCard(new City("Los Angeles"), new City("New York City"), 21));
        _cards.add(new DestCard(new City("Duluth"), new City("Houston"), 8));
        _cards.add(new DestCard(new City("Sault Ste Marie"), new City("Nashville"), 8));
        _cards.add(new DestCard(new City("New York City"), new City("Atlanta"), 6));
        _cards.add(new DestCard(new City("Portland"), new City("Nashville"), 17));
        _cards.add(new DestCard(new City("Vancouver"), new City("Montréal"), 20));
        _cards.add(new DestCard(new City("Duluth"), new City("El Paso"), 10));
        _cards.add(new DestCard(new City("Toronto"), new City("Miami"), 10));
        _cards.add(new DestCard(new City("Portland"), new City("Phoenix"), 11));
        _cards.add(new DestCard(new City("Dallas"), new City("New York City"), 11));
        _cards.add(new DestCard(new City("Calgary"), new City("Salt Lake City"), 7));
        _cards.add(new DestCard(new City("Calgary"), new City("Phoenix"), 13));
        _cards.add(new DestCard(new City("Los Angeles"), new City("Miami"), 20));
        _cards.add(new DestCard(new City("Winnipeg"), new City("Little Rock"), 11));
        _cards.add(new DestCard(new City("San Francisco"), new City("Atlanta"), 17));
        _cards.add(new DestCard(new City("Kansas City"), new City("Houston"), 5));
        _cards.add(new DestCard(new City("Los Angeles"), new City("Chicago"), 16));
        _cards.add(new DestCard(new City("Denver"), new City("Pittsburgh"), 11));
        _cards.add(new DestCard(new City("Chicago"), new City("Santa Fe"), 9));
        _cards.add(new DestCard(new City("Vancouver"), new City("Santa Fe"), 13));
        _cards.add(new DestCard(new City("Boston"), new City("Miami"), 12));
        _cards.add(new DestCard(new City("Chicago"), new City("New Orleans"), 7));
        _cards.add(new DestCard(new City("Montreal"), new City("Atlanta"), 9));
        _cards.add(new DestCard(new City("Seattle"), new City("New York"), 22));
        _cards.add(new DestCard(new City("Denver"), new City("El Paso"), 4));
        _cards.add(new DestCard(new City("Helena"), new City("Los Angeles"), 8));
        _cards.add(new DestCard(new City("Winnipeg"), new City("Houston"), 12));
        _cards.add(new DestCard(new City("Montreal"), new City("New Orleans"), 13));
        _cards.add(new DestCard(new City("Sault Ste. Marie"), new City("Oklahoma City"), 9));
        _cards.add(new DestCard(new City("Seattle"), new City("Los Angeles"), 9));
    }

    public final List<DestCard> getThreeCards() throws DeckException
    {
        List<DestCard> three = new ArrayList<>();

        if (_cards.size() < 3)
        {
            throw new DeckException("THere are only " + _cards.size() + " cards in the dest card deck");
        }

        for (int i = 0; i < 3; i++){
            three.add(_cards.remove(0));
        }

        return three;
    }

    public void putDestCardBack(DestCard card) throws DeckException {
        for (int i = 0; i < _cards.size(); i++){
            if (card.get_cities().equals(_cards.get(i).get_cities())){
                throw new DeckException("Dest card already exists in deck, cannot put it back");
            }

            if (_cards.size() == 30)
            {
                throw new DeckException("Dest card deck already has 30 cards");
            }
            _cards.add(card);
        }
    }

}
