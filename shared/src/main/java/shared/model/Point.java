package shared.model;

/**
 * Created by jtyler17 on 7/21/18.
 */

public class Point {
    private int _x;
    private int _y;

    public Point(int x, int y) {
        _x = x;
        _y = y;
    }

    public int x() {
        return _x;
    }
    public int y() {
        return _y;
    }
}
