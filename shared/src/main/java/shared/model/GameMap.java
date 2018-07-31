package shared.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import shared.enumeration.ListOfRoutes;
import shared.enumeration.PlayerColor;

public class GameMap {
    private HashMap<UUID, Route> _routes = new HashMap<>();
    private List<Route> _claimedRoutes = new ArrayList<>();

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
        _claimedRoutes.add(route);
    }

    public Route getRoute(UUID routeId) {
        return _routes.get(routeId);
    }

    public List<Route> getClaimedRoutes() {
        return _claimedRoutes;
    }

    public Map<UUID, Route> getRoutesClaimedByPlayer(String playerID) {
        Map<UUID, Route> result = new HashMap<>();

        for (int i = 0; i < _claimedRoutes.size(); i++) {
            if (_claimedRoutes.get(i).isClaimed() && _claimedRoutes.get(i).get_claimedBy().equals(playerID)){
                result.put(_claimedRoutes.get(i).getId(), _claimedRoutes.get(i));
            }
        }
        return result;
    }
}
