package shared.model;

import java.util.UUID;

import shared.enumeration.CityName;
import shared.enumeration.PlayerColor;
import shared.enumeration.TrainColor;

public class Route {
    private UUID _id;
    private String _claimedBy;
    private boolean _isClaimed;
    private PlayerColor _claimedColor;
    private int _pathLength;
    private TrainColor _color;
    private CityName _source;
    private CityName _dest;
    private int _doubleRouteIndex;
    private boolean _wasSrc; //used for determining if Dest Card was completed
    private boolean _visited; //used for determining if Dest Card was completed

    public Route(CityName city1, CityName city2, int pathLength, TrainColor color) {
        this._source = city1;
        this._dest = city2;
        this._isClaimed = false;
        this._pathLength = pathLength;
        this._color = color;
        _id = UUID.randomUUID();
    }

    public UUID getId(){
        return _id;
    }

    public boolean isClaimed(){
        return _isClaimed;
    }

    public void claimRoute(String playerId, PlayerColor playerColor){
        set_claimedBy(playerId);
        this._isClaimed = true;
        this._claimedColor = playerColor;
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

    public PlayerColor get_claimedColor() {
        return _claimedColor;
    }

    public CityName get_source() {
        return _source;
    }

    public CityName get_dest() {
        return _dest;
    }

    public Route setDoubleRoute(int index) {
        _doubleRouteIndex = index;
        return this;
    }

    public int getDoubleRoute() {
        return _doubleRouteIndex;
    }

    public boolean wasSrc() {
        return _wasSrc;
    }

    public boolean visited() {
        return _visited;
    }

    public void set_wasSrc(boolean b) {
        _wasSrc = b;
    }

    public void setVisited(boolean b) {
        _visited = b;
    }

    @Override
    public String toString() {
        String out = new String(_source.toString() + " to " + _dest.toString());
        return out;
    }

}
