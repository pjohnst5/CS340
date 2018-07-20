package shared.enumeration;

import java.util.List;

import shared.model.City;
import shared.model.Route;

import static shared.enumeration.TrainColor.BLACK;
import static shared.enumeration.TrainColor.BLUE;
import static shared.enumeration.TrainColor.GRAY;
import static shared.enumeration.TrainColor.GREEN;
import static shared.enumeration.TrainColor.ORANGE;
import static shared.enumeration.TrainColor.PINK;
import static shared.enumeration.TrainColor.RED;
import static shared.enumeration.TrainColor.WHITE;
import static shared.enumeration.TrainColor.YELLOW;

public class ListOfRoutes {
    private List<Route> _routes = null;

    public ListOfRoutes(){
        _routes.add(new Route(new City(CityName.VANCOUVER), new City(CityName.CALGARY), 3, GRAY));
        _routes.add(new Route(new City(CityName.VANCOUVER), new City(CityName.SEATTLE), 1, GRAY));
        _routes.add(new Route(new City(CityName.VANCOUVER), new City(CityName.SEATTLE), 1, GRAY));
        _routes.add(new Route(new City(CityName.SEATTLE), new City(CityName.CALGARY), 4, GRAY));
        _routes.add(new Route(new City(CityName.SEATTLE), new City(CityName.HELENA), 6, YELLOW));
        _routes.add(new Route(new City(CityName.SEATTLE), new City(CityName.PORTLAND), 1, GRAY));
        _routes.add(new Route(new City(CityName.SEATTLE), new City(CityName.PORTLAND), 1, GRAY));
        _routes.add(new Route(new City(CityName.PORTLAND), new City(CityName.SALT_LAKE_CITY), 6, BLUE));
        _routes.add(new Route(new City(CityName.PORTLAND), new City(CityName.SAN_FRANCISCO), 5, GREEN));
        _routes.add(new Route(new City(CityName.PORTLAND), new City(CityName.SAN_FRANCISCO), 5, PINK));
        _routes.add(new Route(new City(CityName.SALT_LAKE_CITY), new City(CityName.SAN_FRANCISCO), 5, ORANGE));
        _routes.add(new Route(new City(CityName.SALT_LAKE_CITY), new City(CityName.SAN_FRANCISCO), 5, WHITE));
        _routes.add(new Route(new City(CityName.LOS_ANGELES), new City(CityName.SAN_FRANCISCO), 3, PINK));
        _routes.add(new Route(new City(CityName.LOS_ANGELES), new City(CityName.SAN_FRANCISCO), 3, YELLOW));
        _routes.add(new Route(new City(CityName.LAS_VEGAS), new City(CityName.LOS_ANGELES), 2, GRAY));
        _routes.add(new Route(new City(CityName.PHOENIX), new City(CityName.LOS_ANGELES), 3, GRAY));
        _routes.add(new Route(new City(CityName.EL_PASO), new City(CityName.LOS_ANGELES), 6, BLACK));
        _routes.add(new Route(new City(CityName.WINNIPEG), new City(CityName.CALGARY), 6, WHITE));
        _routes.add(new Route(new City(CityName.HELENA), new City(CityName.CALGARY), 4, GRAY));
        _routes.add(new Route(new City(CityName.HELENA), new City(CityName.WINNIPEG), 4, BLUE));
        _routes.add(new Route(new City(CityName.HELENA), new City(CityName.DULUTH), 6, ORANGE));
        _routes.add(new Route(new City(CityName.HELENA), new City(CityName.OMAHA), 5, RED));
        _routes.add(new Route(new City(CityName.HELENA), new City(CityName.DENVER), 4, GREEN));
        _routes.add(new Route(new City(CityName.HELENA), new City(CityName.SALT_LAKE_CITY), 3, PINK));
        _routes.add(new Route(new City(CityName.DENVER), new City(CityName.SALT_LAKE_CITY), 3, RED));

        _routes.add(new Route(new City(CityName.DENVER), new City(CityName.SALT_LAKE_CITY), 3, YELLOW));
        _routes.add(new Route(new City(CityName.LAS_VEGAS), new City(CityName.SALT_LAKE_CITY), 3, ORANGE));
        _routes.add(new Route(new City(CityName.PHOENIX), new City(CityName.EL_PASO), 3, GRAY));
        _routes.add(new Route(new City(CityName.PHOENIX), new City(CityName.SANTA_FE), 3, GRAY));
        _routes.add(new Route(new City(CityName.PHOENIX), new City(CityName.DENVER), 5, WHITE));
        _routes.add(new Route(new City(CityName.EL_PASO), new City(CityName.HOUSTON), 6, GREEN));
        _routes.add(new Route(new City(CityName.EL_PASO), new City(CityName.DALLAS), 4, RED));
        _routes.add(new Route(new City(CityName.EL_PASO), new City(CityName.OKLAHOMA_CITY), 5, YELLOW));
        _routes.add(new Route(new City(CityName.EL_PASO), new City(CityName.SANTA_FE), 2, GRAY));
        _routes.add(new Route(new City(CityName.SANTA_FE), new City(CityName.OKLAHOMA_CITY), 3, BLUE));
        _routes.add(new Route(new City(CityName.SANTA_FE), new City(CityName.DENVER), 2, GRAY));
        _routes.add(new Route(new City(CityName.OKLAHOMA_CITY), new City(CityName.DENVER), 4, RED));
        _routes.add(new Route(new City(CityName.KANSAS_CITY), new City(CityName.DENVER), 4, BLACK));
        _routes.add(new Route(new City(CityName.KANSAS_CITY), new City(CityName.DENVER), 4, ORANGE));
        _routes.add(new Route(new City(CityName.OKLAHOMA_CITY), new City(CityName.DENVER), 4, PINK));
        _routes.add(new Route(new City(CityName.WINNIPEG), new City(CityName.DULUTH), 4, BLACK));
        _routes.add(new Route(new City(CityName.WINNIPEG), new City(CityName.SAULT_STE_MARIE), 6, GRAY));
        _routes.add(new Route(new City(CityName.DULUTH), new City(CityName.SAULT_STE_MARIE), 3, GRAY));
        _routes.add(new Route(new City(CityName.DULUTH), new City(CityName.TORONTO), 6, PINK));
        _routes.add(new Route(new City(CityName.DULUTH), new City(CityName.CHICAGO), 3, RED));
        _routes.add(new Route(new City(CityName.DULUTH), new City(CityName.OMAHA), 2, GRAY));
        _routes.add(new Route(new City(CityName.DULUTH), new City(CityName.OMAHA), 2, GRAY));
        _routes.add(new Route(new City(CityName.CHICAGO), new City(CityName.OMAHA), 4, BLUE));
        _routes.add(new Route(new City(CityName.KANSAS_CITY), new City(CityName.OMAHA), 1, GRAY));
        _routes.add(new Route(new City(CityName.KANSAS_CITY), new City(CityName.OMAHA), 1, GRAY));

        _routes.add(new Route(new City(CityName.KANSAS_CITY), new City(CityName.ST_LOUIS), 2, BLUE));
        _routes.add(new Route(new City(CityName.KANSAS_CITY), new City(CityName.ST_LOUIS), 2, PINK));
        _routes.add(new Route(new City(CityName.KANSAS_CITY), new City(CityName.OKLAHOMA_CITY), 2, GRAY));
        _routes.add(new Route(new City(CityName.KANSAS_CITY), new City(CityName.OKLAHOMA_CITY), 2, GRAY));
        _routes.add(new Route(new City(CityName.LITTLE_ROCK), new City(CityName.OKLAHOMA_CITY), 2, GRAY));
        _routes.add(new Route(new City(CityName.DALLAS), new City(CityName.OKLAHOMA_CITY), 2, GRAY));
        _routes.add(new Route(new City(CityName.DALLAS), new City(CityName.OKLAHOMA_CITY), 2, GRAY));
        _routes.add(new Route(new City(CityName.DALLAS), new City(CityName.LITTLE_ROCK), 2, GRAY));
        _routes.add(new Route(new City(CityName.DALLAS), new City(CityName.HOUSTON), 2, GRAY));
        _routes.add(new Route(new City(CityName.DALLAS), new City(CityName.HOUSTON), 2, GRAY));
        _routes.add(new Route(new City(CityName.NEW_ORLEANS), new City(CityName.HOUSTON), 2, GRAY));
        _routes.add(new Route(new City(CityName.SAULT_STE_MARIE), new City(CityName.MONTREAL), 5, BLACK));
        _routes.add(new Route(new City(CityName.SAULT_STE_MARIE), new City(CityName.TORONTO), 2, GRAY));
        _routes.add(new Route(new City(CityName.CHICAGO), new City(CityName.TORONTO), 4, WHITE));
        _routes.add(new Route(new City(CityName.CHICAGO), new City(CityName.PITTSBURGH), 3, ORANGE));
        _routes.add(new Route(new City(CityName.CHICAGO), new City(CityName.PITTSBURGH), 3, BLACK));
        _routes.add(new Route(new City(CityName.CHICAGO), new City(CityName.ST_LOUIS), 2, GREEN));
        _routes.add(new Route(new City(CityName.CHICAGO), new City(CityName.ST_LOUIS), 2, WHITE));
        _routes.add(new Route(new City(CityName.PITTSBURGH), new City(CityName.ST_LOUIS), 5, GREEN));
        _routes.add(new Route(new City(CityName.NASHVILLE), new City(CityName.ST_LOUIS), 2, GRAY));
        _routes.add(new Route(new City(CityName.LITTLE_ROCK), new City(CityName.ST_LOUIS), 2, GRAY));
        _routes.add(new Route(new City(CityName.LITTLE_ROCK), new City(CityName.NASHVILLE), 3, WHITE));
        _routes.add(new Route(new City(CityName.LITTLE_ROCK), new City(CityName.NEW_ORLEANS), 3, GREEN));
        _routes.add(new Route(new City(CityName.TORONTO), new City(CityName.MONTREAL), 3, GRAY));
        _routes.add(new Route(new City(CityName.TORONTO), new City(CityName.PITTSBURGH), 2, GRAY));

        _routes.add(new Route(new City(CityName.PITTSBURGH), new City(CityName.NEW_YORK_CITY), 2, WHITE));
        _routes.add(new Route(new City(CityName.PITTSBURGH), new City(CityName.NEW_YORK_CITY), 2, GREEN));
        _routes.add(new Route(new City(CityName.PITTSBURGH), new City(CityName.WASHINGTON), 2, GRAY));
        _routes.add(new Route(new City(CityName.PITTSBURGH), new City(CityName.RALEIGH), 2, GRAY));
        _routes.add(new Route(new City(CityName.PITTSBURGH), new City(CityName.NASHVILLE), 4, YELLOW));
        _routes.add(new Route(new City(CityName.NASHVILLE), new City(CityName.RALEIGH), 3, BLACK));
        _routes.add(new Route(new City(CityName.NASHVILLE), new City(CityName.ATLANTA), 1, GRAY));
        _routes.add(new Route(new City(CityName.NEW_ORLEANS), new City(CityName.ATLANTA), 4, YELLOW));
        _routes.add(new Route(new City(CityName.NEW_ORLEANS), new City(CityName.ATLANTA), 4, ORANGE));
        _routes.add(new Route(new City(CityName.NEW_ORLEANS), new City(CityName.MIAMI), 6, RED));
        _routes.add(new Route(new City(CityName.ATLANTA), new City(CityName.MIAMI), 5, BLUE));
        _routes.add(new Route(new City(CityName.CHARLESTON), new City(CityName.MIAMI), 4, PINK));
        _routes.add(new Route(new City(CityName.CHARLESTON), new City(CityName.ATLANTA), 2, GRAY));
        _routes.add(new Route(new City(CityName.RALEIGH), new City(CityName.ATLANTA), 2, GRAY));
        _routes.add(new Route(new City(CityName.RALEIGH), new City(CityName.ATLANTA), 2, GRAY));
        _routes.add(new Route(new City(CityName.RALEIGH), new City(CityName.CHARLESTON), 2, GRAY));
        _routes.add(new Route(new City(CityName.RALEIGH), new City(CityName.WASHINGTON), 2, GRAY));
        _routes.add(new Route(new City(CityName.RALEIGH), new City(CityName.WASHINGTON), 2, GRAY));
        _routes.add(new Route(new City(CityName.NEW_YORK_CITY), new City(CityName.WASHINGTON), 2, BLACK));
        _routes.add(new Route(new City(CityName.NEW_YORK_CITY), new City(CityName.WASHINGTON), 2, ORANGE));
        _routes.add(new Route(new City(CityName.NEW_YORK_CITY), new City(CityName.MONTREAL), 3, BLUE));
        _routes.add(new Route(new City(CityName.NEW_YORK_CITY), new City(CityName.BOSTON), 2, YELLOW));
        _routes.add(new Route(new City(CityName.NEW_YORK_CITY), new City(CityName.BOSTON), 2, RED));
        _routes.add(new Route(new City(CityName.MONTREAL), new City(CityName.BOSTON), 2, GRAY));
        _routes.add(new Route(new City(CityName.MONTREAL), new City(CityName.BOSTON), 2, GRAY));
    }
    public List<Route> getRoutes(){
        return _routes;
    }
}
