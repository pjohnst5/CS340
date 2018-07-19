package shared.model;

import shared.enumeration.CityName;

public class City {
    //TODO: Implement City

    private CityName _name;

    public City(CityName name){
        _name = name;
    }

    public CityName get_name()
    {
        return _name;
    }

}
