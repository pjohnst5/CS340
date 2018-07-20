package shared.enumeration;

import java.util.ArrayList;
import java.util.List;

import shared.model.City;
import shared.model.Route;

import static shared.enumeration.TrainColor.BLUE;

public class ListOfRoutes {
    private List<Route> _routes = null;

    public ListOfRoutes(){
        _routes = new ArrayList<>();
        _routes.add(new Route(new City(CityName.LOS_ANGELES), new City(CityName.NEW_ORLEANS), 0, BLUE));
    }
    public List<Route> getRoutes(){
        return _routes;
    }
}
