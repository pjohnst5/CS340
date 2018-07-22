package shared.enumeration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import shared.model.City;

/**
 * Created by jtyler17 on 7/21/18.
 */

public class CityManager {
    private static CityManager _instance = null;

    private List<City> _cities = null;

    public static CityManager getInstance() {
        if (_instance == null) {
            _instance = new CityManager();
        }
        return _instance;
    }

    private CityManager() {
        List<City> newCities = new ArrayList<>();
        // The coordinates set here are hard-coded; do not delete!
        newCities.add(new City(CityName.ATLANTA, 1194, 678));
        newCities.add(new City(CityName.BOSTON, 1495, 261));
        newCities.add(new City(CityName.CALGARY, 379, 73));
        newCities.add(new City(CityName.CHARLESTON, 1339, 676));
        newCities.add(new City(CityName.CHICAGO, 1048, 409));
        newCities.add(new City(CityName.DALLAS, 820, 781));
        newCities.add(new City(CityName.DENVER, 550, 514));
        newCities.add(new City(CityName.DULUTH, 909, 238));
        newCities.add(new City(CityName.EL_PASO, 502, 802));
        newCities.add(new City(CityName.HELENA, 471, 223));
        newCities.add(new City(CityName.HOUSTON, 849, 898));
        newCities.add(new City(CityName.KANSAS_CITY, 867, 537));
        newCities.add(new City(CityName.LAS_VEGAS, 249, 615));
        newCities.add(new City(CityName.LITTLE_ROCK, 942, 684));
        newCities.add(new City(CityName.LOS_ANGELES, 142, 658));
        newCities.add(new City(CityName.MIAMI, 1369, 936));
        newCities.add(new City(CityName.MONTREAL, 1380, 178));
        newCities.add(new City(CityName.NASHVILLE, 1131, 598));
        newCities.add(new City(CityName.NEW_ORLEANS, 1024, 847));
        newCities.add(new City(CityName.NEW_YORK_CITY, 1438, 331));
        newCities.add(new City(CityName.OKLAHOMA_CITY, 807, 661));
        newCities.add(new City(CityName.OMAHA, 813, 435));
        newCities.add(new City(CityName.PHOENIX, 339, 708));
        newCities.add(new City(CityName.PITTSBURGH, 1273, 394));
        newCities.add(new City(CityName.PORTLAND, 112, 213));
        newCities.add(new City(CityName.RALEIGH, 1350, 562));
        newCities.add(new City(CityName.SALT_LAKE_CITY, 355, 471));
        newCities.add(new City(CityName.SAN_FRANCISCO, 55, 528));
        newCities.add(new City(CityName.SANTA_FE, 535, 655));
        newCities.add(new City(CityName.SAULT_STE_MARIE, 1111, 229));
        newCities.add(new City(CityName.SEATTLE, 147, 150));
        newCities.add(new City(CityName.ST_LOUIS, 990, 522));
        newCities.add(new City(CityName.TORONTO, 1260, 288));
        newCities.add(new City(CityName.VANCOUVER, 150, 67));
        newCities.add(new City(CityName.WASHINGTON, 1375, 432));
        newCities.add(new City(CityName.WINNIPEG, 756, 118));

        _cities = Collections.unmodifiableList(newCities);
    }

    public List<City> newBoardSize(int width, int height) {
        for (City city : _cities) {
            city.setAspectCoordinates(width, height);
        }
        return _cities;
    }

    public List<City> getCities() {
        return _cities;
    }

    public City get(CityName cityName) {
        for (City city : _cities) {
            if (city.get_name() == cityName) {
                return city;
            }
        }
        return null;
    }
}
