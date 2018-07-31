package shared.model.decks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import shared.enumeration.CityName;
import shared.exception.DeckException;
import shared.model.City;

public class DestDeck {
    private List<DestCard> _cards;

    public DestDeck()
    {
        _cards = new ArrayList<>();
        _cards.add(new DestCard(new City(CityName.LOS_ANGELES), new City(CityName.NEW_YORK_CITY), 21));
        _cards.add(new DestCard(new City(CityName.DULUTH), new City(CityName.HOUSTON), 8));
        _cards.add(new DestCard(new City(CityName.SAULT_STE_MARIE), new City(CityName.NASHVILLE), 8));
        _cards.add(new DestCard(new City(CityName.NEW_YORK_CITY), new City(CityName.ATLANTA), 6));
        _cards.add(new DestCard(new City(CityName.PORTLAND), new City(CityName.NASHVILLE), 17));
        _cards.add(new DestCard(new City(CityName.VANCOUVER), new City(CityName.MONTREAL), 20));
        _cards.add(new DestCard(new City(CityName.DULUTH), new City(CityName.EL_PASO), 10));
        _cards.add(new DestCard(new City(CityName.TORONTO), new City(CityName.MIAMI), 10));
        _cards.add(new DestCard(new City(CityName.PORTLAND), new City(CityName.PHOENIX), 11));
        _cards.add(new DestCard(new City(CityName.DALLAS), new City(CityName.NEW_YORK_CITY), 11));
        _cards.add(new DestCard(new City(CityName.CALGARY), new City(CityName.SALT_LAKE_CITY), 7));
        _cards.add(new DestCard(new City(CityName.CALGARY), new City(CityName.PHOENIX), 13));
        _cards.add(new DestCard(new City(CityName.LOS_ANGELES), new City(CityName.MIAMI), 20));
        _cards.add(new DestCard(new City(CityName.WINNIPEG), new City(CityName.LITTLE_ROCK), 11));
        _cards.add(new DestCard(new City(CityName.SAN_FRANCISCO), new City(CityName.ATLANTA), 17));
        _cards.add(new DestCard(new City(CityName.KANSAS_CITY), new City(CityName.HOUSTON), 5));
        _cards.add(new DestCard(new City(CityName.LOS_ANGELES), new City(CityName.CHICAGO), 16));
        _cards.add(new DestCard(new City(CityName.DENVER), new City(CityName.PITTSBURGH), 11));
        _cards.add(new DestCard(new City(CityName.CHICAGO), new City(CityName.SANTA_FE), 9));
        _cards.add(new DestCard(new City(CityName.VANCOUVER), new City(CityName.SANTA_FE), 13));
        _cards.add(new DestCard(new City(CityName.BOSTON), new City(CityName.MIAMI), 12));
        _cards.add(new DestCard(new City(CityName.CHICAGO), new City(CityName.NEW_ORLEANS), 7));
        _cards.add(new DestCard(new City(CityName.MONTREAL), new City(CityName.ATLANTA), 9));
        _cards.add(new DestCard(new City(CityName.SEATTLE), new City(CityName.NEW_YORK_CITY), 22));
        _cards.add(new DestCard(new City(CityName.DENVER), new City(CityName.EL_PASO), 4));
        _cards.add(new DestCard(new City(CityName.HELENA), new City(CityName.LOS_ANGELES), 8));
        _cards.add(new DestCard(new City(CityName.WINNIPEG), new City(CityName.HOUSTON), 12));
        _cards.add(new DestCard(new City(CityName.MONTREAL), new City(CityName.NEW_ORLEANS), 13));
        _cards.add(new DestCard(new City(CityName.SAULT_STE_MARIE), new City(CityName.OKLAHOMA_CITY), 9));
        _cards.add(new DestCard(new City(CityName.SEATTLE), new City(CityName.LOS_ANGELES), 9));

        shuffleDeck();
    }

    private void shuffleDeck(){
        Random rand = new Random();
        for (int i = 0; i < _cards.size(); i++){
            int  n = rand.nextInt(_cards.size()-1);

            DestCard tempCard = _cards.get(i);
            _cards.set(i,_cards.get(n));
            _cards.set(n, tempCard);
        }


    }

    public final List<DestCard> getThreeCards() throws DeckException
    {
        List<DestCard> three = new ArrayList<>();

        if (_cards.size() == 0)
        {
            throw new DeckException("There are only no more cards left in the Destination Deck");
        }

        if (_cards.size() < 3)
        {
            int cardsLeft = _cards.size();
            for (int i = 0; i < cardsLeft; i++) {
                three.add(_cards.remove(0));
            }

        } else {
            for (int i = 0; i < 3; i++){
                three.add(_cards.remove(0));
            }
        }

        return three;
    }

    public void putDestCardBack(DestCard card) throws DeckException {
        if (_cards.size() == 30){
            throw new DeckException("Dest card deck already has 30 cards");
        }

        if (_cards.contains(card)){
            throw new DeckException("Dest card already exists in deck, cannot put it back");
        }
        _cards.add(card);
    }

    public List<DestCard> get_cards() {
        return _cards;
    }
}
