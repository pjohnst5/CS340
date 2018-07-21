package shared.model;

import java.util.HashMap;
import java.util.List;

import shared.enumeration.ListOfRoutes;

public class GameMap {
    private HashMap<Integer, Route> _routes = new HashMap<>();

    public GameMap() {
        List<Route> routeList =  new ListOfRoutes().getRoutes();
        for(int i = 0; i < routeList.size(); i++){
            _routes.put(routeList.get(i).getId(), routeList.get(i));
        }
    }

    public HashMap<Integer, Route> get_routes() {
        return _routes;
    }

    public boolean isRouteClaimed(int routeId){
        Route route = _routes.get(routeId);
        return route.isClaimed();
    }

    public void claimRoute(int routeId, String playerId){
        Route route = _routes.get(routeId);
        route.claimRoute(playerId);
    }
}
