package shared.model;

import shared.configuration.ConfigurationManager;
import shared.enumeration.CityName;

public class City {
    //TODO: Implement City

    private CityName _name;
    private Point _coordinates;
    private Point _aspectCoordinates;

    private static final int BASE_WIDTH;
    private static final int BASE_HEIGHT;
    static {
//        BASE_WIDTH = ConfigurationManager.getInt("board_width"); // FIXME: why does this throw an error?
//        BASE_HEIGHT = ConfigurationManager.getInt("board_height");
        BASE_WIDTH = 1600;
        BASE_HEIGHT = 1088;
    }

    public City(CityName name) {
        _name = name;
    }

    public City(CityName name, int x, int y){
        _name = name;
        _coordinates = new Point(x, y);
        _aspectCoordinates = _coordinates;
    }

    public void setAspectCoordinates(int width, int height) {
        int x = (int)(_coordinates.x() * ((double)width / (double)BASE_WIDTH));
        int y = (int)(_coordinates.y() * ((double)height / (double)BASE_HEIGHT));
        _aspectCoordinates = new Point(x, y);
    }

    public CityName get_name()
    {
        return _name;
    }

    public Point getAspectCoordinates() {
        return _aspectCoordinates;
    }

    public int getX() {
        return _aspectCoordinates.x();
    }
    public int getY() {
        return _aspectCoordinates.y();
    }
    @Override
    public String toString(){
        return _name.name().replace("_", " ");
    }
}
