package shared.enumeration;

import java.util.ArrayList;
import java.util.List;

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
        _routes = new ArrayList<>();
//        _routes.add(new Route(CityName.LOS_ANGELES, CityName.NEW_ORLEANS, 0, BLUE)); //FIXME: i don't think this one is right
        _routes.add(new Route(CityName.VANCOUVER, CityName.CALGARY, 3, GRAY));
        _routes.add(new Route(CityName.VANCOUVER, CityName.SEATTLE, 1, GRAY));
        _routes.add(new Route(CityName.VANCOUVER, CityName.SEATTLE, 1, GRAY));
        _routes.add(new Route(CityName.SEATTLE, CityName.CALGARY, 4, GRAY));
        _routes.add(new Route(CityName.SEATTLE, CityName.HELENA, 6, YELLOW));
        _routes.add(new Route(CityName.SEATTLE, CityName.PORTLAND, 1, GRAY));
        _routes.add(new Route(CityName.SEATTLE, CityName.PORTLAND, 1, GRAY));
        _routes.add(new Route(CityName.PORTLAND, CityName.SALT_LAKE_CITY, 6, BLUE));
        _routes.add(new Route(CityName.PORTLAND, CityName.SAN_FRANCISCO, 5, GREEN));
        _routes.add(new Route(CityName.PORTLAND, CityName.SAN_FRANCISCO, 5, PINK));
        _routes.add(new Route(CityName.SALT_LAKE_CITY, CityName.SAN_FRANCISCO, 5, ORANGE));
        _routes.add(new Route(CityName.SALT_LAKE_CITY, CityName.SAN_FRANCISCO, 5, WHITE));
        _routes.add(new Route(CityName.LOS_ANGELES, CityName.SAN_FRANCISCO, 3, PINK));
        _routes.add(new Route(CityName.LOS_ANGELES, CityName.SAN_FRANCISCO, 3, YELLOW));
        _routes.add(new Route(CityName.LAS_VEGAS, CityName.LOS_ANGELES, 2, GRAY));
        _routes.add(new Route(CityName.PHOENIX, CityName.LOS_ANGELES, 3, GRAY));
        _routes.add(new Route(CityName.EL_PASO, CityName.LOS_ANGELES, 6, BLACK));
        _routes.add(new Route(CityName.WINNIPEG, CityName.CALGARY, 6, WHITE));
        _routes.add(new Route(CityName.HELENA, CityName.CALGARY, 4, GRAY));
        _routes.add(new Route(CityName.HELENA, CityName.WINNIPEG, 4, BLUE));
        _routes.add(new Route(CityName.HELENA, CityName.DULUTH, 6, ORANGE));
        _routes.add(new Route(CityName.HELENA, CityName.OMAHA, 5, RED));
        _routes.add(new Route(CityName.HELENA, CityName.DENVER, 4, GREEN));
        _routes.add(new Route(CityName.HELENA, CityName.SALT_LAKE_CITY, 3, PINK));
        _routes.add(new Route(CityName.DENVER, CityName.SALT_LAKE_CITY, 3, RED));

        _routes.add(new Route(CityName.DENVER, CityName.SALT_LAKE_CITY, 3, YELLOW));
        _routes.add(new Route(CityName.LAS_VEGAS, CityName.SALT_LAKE_CITY, 3, ORANGE));
        _routes.add(new Route(CityName.PHOENIX, CityName.EL_PASO, 3, GRAY));
        _routes.add(new Route(CityName.PHOENIX, CityName.SANTA_FE, 3, GRAY));
        _routes.add(new Route(CityName.PHOENIX, CityName.DENVER, 5, WHITE));
        _routes.add(new Route(CityName.EL_PASO, CityName.HOUSTON, 6, GREEN));
        _routes.add(new Route(CityName.EL_PASO, CityName.DALLAS, 4, RED));
        _routes.add(new Route(CityName.EL_PASO, CityName.OKLAHOMA_CITY, 5, YELLOW));
        _routes.add(new Route(CityName.EL_PASO, CityName.SANTA_FE, 2, GRAY));
        _routes.add(new Route(CityName.SANTA_FE, CityName.OKLAHOMA_CITY, 3, BLUE));
        _routes.add(new Route(CityName.SANTA_FE, CityName.DENVER, 2, GRAY));
        _routes.add(new Route(CityName.OKLAHOMA_CITY, CityName.DENVER, 4, RED));
        _routes.add(new Route(CityName.OKLAHOMA_CITY, CityName.DENVER, 4, PINK));
        _routes.add(new Route(CityName.KANSAS_CITY, CityName.DENVER, 4, BLACK));
        _routes.add(new Route(CityName.KANSAS_CITY, CityName.DENVER, 4, ORANGE));
        _routes.add(new Route(CityName.WINNIPEG, CityName.DULUTH, 4, BLACK));
        _routes.add(new Route(CityName.WINNIPEG, CityName.SAULT_STE_MARIE, 6, GRAY));
        _routes.add(new Route(CityName.DULUTH, CityName.SAULT_STE_MARIE, 3, GRAY));
        _routes.add(new Route(CityName.DULUTH, CityName.TORONTO, 6, PINK));
        _routes.add(new Route(CityName.DULUTH, CityName.CHICAGO, 3, RED));
        _routes.add(new Route(CityName.DULUTH, CityName.OMAHA, 2, GRAY));
        _routes.add(new Route(CityName.DULUTH, CityName.OMAHA, 2, GRAY));
        _routes.add(new Route(CityName.CHICAGO, CityName.OMAHA, 4, BLUE));
        _routes.add(new Route(CityName.KANSAS_CITY, CityName.OMAHA, 1, GRAY));
        _routes.add(new Route(CityName.KANSAS_CITY, CityName.OMAHA, 1, GRAY));

        _routes.add(new Route(CityName.KANSAS_CITY, CityName.ST_LOUIS, 2, BLUE));
        _routes.add(new Route(CityName.KANSAS_CITY, CityName.ST_LOUIS, 2, PINK));
        _routes.add(new Route(CityName.KANSAS_CITY, CityName.OKLAHOMA_CITY, 2, GRAY));
        _routes.add(new Route(CityName.KANSAS_CITY, CityName.OKLAHOMA_CITY, 2, GRAY));
        _routes.add(new Route(CityName.LITTLE_ROCK, CityName.OKLAHOMA_CITY, 2, GRAY));
        _routes.add(new Route(CityName.DALLAS, CityName.OKLAHOMA_CITY, 2, GRAY));
        _routes.add(new Route(CityName.DALLAS, CityName.OKLAHOMA_CITY, 2, GRAY));
        _routes.add(new Route(CityName.DALLAS, CityName.LITTLE_ROCK, 2, GRAY));
        _routes.add(new Route(CityName.DALLAS, CityName.HOUSTON, 2, GRAY));
        _routes.add(new Route(CityName.DALLAS, CityName.HOUSTON, 2, GRAY));
        _routes.add(new Route(CityName.NEW_ORLEANS, CityName.HOUSTON, 2, GRAY));
        _routes.add(new Route(CityName.SAULT_STE_MARIE, CityName.MONTREAL, 5, BLACK));
        _routes.add(new Route(CityName.SAULT_STE_MARIE, CityName.TORONTO, 2, GRAY));
        _routes.add(new Route(CityName.CHICAGO, CityName.TORONTO, 4, WHITE));
        _routes.add(new Route(CityName.CHICAGO, CityName.PITTSBURGH, 3, ORANGE));
        _routes.add(new Route(CityName.CHICAGO, CityName.PITTSBURGH, 3, BLACK));
        _routes.add(new Route(CityName.CHICAGO, CityName.ST_LOUIS, 2, GREEN));
        _routes.add(new Route(CityName.CHICAGO, CityName.ST_LOUIS, 2, WHITE));
        _routes.add(new Route(CityName.PITTSBURGH, CityName.ST_LOUIS, 5, GREEN));
        _routes.add(new Route(CityName.NASHVILLE, CityName.ST_LOUIS, 2, GRAY));
        _routes.add(new Route(CityName.LITTLE_ROCK, CityName.ST_LOUIS, 2, GRAY));
        _routes.add(new Route(CityName.LITTLE_ROCK, CityName.NASHVILLE, 3, WHITE));
        _routes.add(new Route(CityName.LITTLE_ROCK, CityName.NEW_ORLEANS, 3, GREEN));
        _routes.add(new Route(CityName.TORONTO, CityName.MONTREAL, 3, GRAY));
        _routes.add(new Route(CityName.TORONTO, CityName.PITTSBURGH, 2, GRAY));

        _routes.add(new Route(CityName.PITTSBURGH, CityName.NEW_YORK_CITY, 2, WHITE));
        _routes.add(new Route(CityName.PITTSBURGH, CityName.NEW_YORK_CITY, 2, GREEN));
        _routes.add(new Route(CityName.PITTSBURGH, CityName.WASHINGTON, 2, GRAY));
        _routes.add(new Route(CityName.PITTSBURGH, CityName.RALEIGH, 2, GRAY));
        _routes.add(new Route(CityName.PITTSBURGH, CityName.NASHVILLE, 4, YELLOW));
        _routes.add(new Route(CityName.NASHVILLE, CityName.RALEIGH, 3, BLACK));
        _routes.add(new Route(CityName.NASHVILLE, CityName.ATLANTA, 1, GRAY));
        _routes.add(new Route(CityName.NEW_ORLEANS, CityName.ATLANTA, 4, YELLOW));
        _routes.add(new Route(CityName.NEW_ORLEANS, CityName.ATLANTA, 4, ORANGE));
        _routes.add(new Route(CityName.NEW_ORLEANS, CityName.MIAMI, 6, RED));
        _routes.add(new Route(CityName.ATLANTA, CityName.MIAMI, 5, BLUE));
        _routes.add(new Route(CityName.CHARLESTON, CityName.MIAMI, 4, PINK));
        _routes.add(new Route(CityName.CHARLESTON, CityName.ATLANTA, 2, GRAY));
        _routes.add(new Route(CityName.RALEIGH, CityName.ATLANTA, 2, GRAY));
        _routes.add(new Route(CityName.RALEIGH, CityName.ATLANTA, 2, GRAY));
        _routes.add(new Route(CityName.RALEIGH, CityName.CHARLESTON, 2, GRAY));
        _routes.add(new Route(CityName.RALEIGH, CityName.WASHINGTON, 2, GRAY));
        _routes.add(new Route(CityName.RALEIGH, CityName.WASHINGTON, 2, GRAY));
        _routes.add(new Route(CityName.NEW_YORK_CITY, CityName.WASHINGTON, 2, BLACK));
        _routes.add(new Route(CityName.NEW_YORK_CITY, CityName.WASHINGTON, 2, ORANGE));
        _routes.add(new Route(CityName.NEW_YORK_CITY, CityName.MONTREAL, 3, BLUE));
        _routes.add(new Route(CityName.NEW_YORK_CITY, CityName.BOSTON, 2, YELLOW));
        _routes.add(new Route(CityName.NEW_YORK_CITY, CityName.BOSTON, 2, RED));
        _routes.add(new Route(CityName.MONTREAL, CityName.BOSTON, 2, GRAY));
        _routes.add(new Route(CityName.MONTREAL, CityName.BOSTON, 2, GRAY));

        markDoubleRoutes();
    }
    public List<Route> getRoutes(){
        return _routes;
    }

    private void markDoubleRoutes() { // FIXME: remove double routes for games with less than 4 players
        int len = _routes.size();
        for (int i = 0; i < len; i++) {
            Route r1 = _routes.get(i);
            if (r1.getDoubleRoute() > 0) continue;
            for (int j = i+1; j < len; j++) {
                Route r2 = _routes.get(j);
                if (r2.getDoubleRoute() > 0) continue;
                if ((r1.get_source() == r2.get_source() && r1.get_dest() == r2.get_dest()) ||
                        (r1.get_source() == r2.get_dest() && r1.get_dest() == r2.get_source())) {
                    r1.setDoubleRoute(1);
                    r2.setDoubleRoute(2);
                    break;
                }
            }
        }
    }
}
