package shared.model.decks;

import com.sun.tools.javac.util.Pair;

import shared.model.City;

public class DestCard implements ICard {
    private Pair<City, City> _cities;
    private int _worth;

    public DestCard(City city1, City city2, int worth){
        _cities = new Pair<>(city1, city2);
        _worth = worth;
    }

    public int get_worth() {
        return _worth;
    }

    public Pair<City, City> get_cities()
    {
        return _cities;
    }
}
