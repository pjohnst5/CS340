package shared.model;

import shared.enumeration.TrainColor;

public class Route {
    private static int _id = 0;
    private String _claimedBy;
    private boolean _isClaimed;
    private int _pathLength;
    private TrainColor _color;
    private City _source;
    private City _dest;

    public Route(City city1, City city2, int pathLength, TrainColor color) {
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

    public City get_source() {
        return _source;
    }

    public City get_dest() {
        return _dest;
    }
}
