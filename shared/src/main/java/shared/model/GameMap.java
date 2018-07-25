package shared.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import shared.enumeration.ListOfRoutes;
import shared.enumeration.PlayerColor;

public class GameMap {
    private HashMap<UUID, Route> _routes = new HashMap<>();
    private List<Route> claimedRoutes = new ArrayList<>();

    public GameMap() {
        List<Route> routeList =  new ListOfRoutes().getRoutes();
        for(int i = 0; i < routeList.size(); i++){
            _routes.put(routeList.get(i).getId(), routeList.get(i));
        }
        System.out.println();
    }

    public HashMap<UUID, Route> get_routes() {
        return _routes;
    }

    public boolean isRouteClaimed(UUID routeId){
        Route route = _routes.get(routeId);
        return route.isClaimed();
    }

    public void claimRoute(UUID routeId, String playerId, PlayerColor playerColor){
        Route route = _routes.get(routeId);
        route.claimRoute(playerId, playerColor);
        claimedRoutes.add(route);
    }

    public List<Route> getClaimedRoutes() {
        return claimedRoutes;
    }
}
