package shared.model.decks;


import shared.model.City;
import shared.model.Pair;

public class DestCard implements ICard {
    private Pair<City, City> _cities;
    private int _worth;
    private boolean _completed;

    public DestCard(City city1, City city2, int worth){
        _cities = new Pair<>(city1, city2);
        _worth = worth;
        _completed = false;
    }

    public int get_worth() {
        return _worth;
    }

    public Pair<City, City> get_cities()
    {
        return _cities;
    }

    public void set_completed(boolean b)
    {
        _completed = b;
    }

    public boolean getCompleted()
    {
        return _completed;
    }

    @Override
    public String toString(){
        return _cities.getKey().toString() + " - " + _cities.getValue().toString();
    }

}
