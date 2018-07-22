package shared.model;

import shared.enumeration.CityName;
import shared.enumeration.TrainColor;

public class Route {
    private static int _id = 0;
    private String _claimedBy;
    private boolean _isClaimed;
    private int _pathLength;
    private TrainColor _color;
    private CityName _source;
    private CityName _dest;
    private boolean _isDoubleRoute;

    public Route(CityName city1, CityName city2, int pathLength, TrainColor color) {
        this._source = city1;
        this._dest = city2;
        this._isClaimed = false;
        this._pathLength = pathLength;
        this._color = color;
        _id = _id + 1;
    }

    public int getId(){
        return _id;
    }

    public boolean isClaimed(){
        return _isClaimed;
    }

    public void claimRoute(String playerId){
        set_claimedBy(playerId);
        this._isClaimed = true;
        //TODO: do we change the color after it has been claimed?
    }
    public void set_claimedBy(String _claimedBy) {
        this._claimedBy = _claimedBy;
    }

    public String get_claimedBy() {
        return _claimedBy;
    }

    public int get_pathLength() {
        return _pathLength;
    }

    public TrainColor get_color() {
        return _color;
    }

    public CityName get_source() {
        return _source;
    }

    public CityName get_dest() {
        return _dest;
    }

    public Route setDoubleRoute(Boolean isDouble) {
        _isDoubleRoute = isDouble;
        return this;
    }
}
