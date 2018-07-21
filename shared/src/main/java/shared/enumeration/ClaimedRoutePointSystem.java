package shared.enumeration;

import java.util.HashMap;
import java.util.Map;


public class ClaimedRoutePointSystem {
    private Map<Integer, Integer> _pointSystem = new HashMap<>();

    public ClaimedRoutePointSystem(){
        this._pointSystem.put(1, 1);
        this._pointSystem.put(2, 2);
        this._pointSystem.put(3, 4);
        this._pointSystem.put(4, 7);
        this._pointSystem.put(5, 10);
        this._pointSystem.put(6, 15);
    }

    public int getPoints(int routeLength){
        return this._pointSystem.get(routeLength);
    }
}
