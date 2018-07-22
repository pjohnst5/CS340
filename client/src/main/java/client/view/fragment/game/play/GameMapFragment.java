package client.view.fragment.game.play;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.pjohnst5icloud.tickettoride.R;

import client.view.fragment.game.play.customview.GameMapView;
import client.view.fragment.game.play.customview.RouteView;
import shared.enumeration.CityManager;
import shared.model.GameMap;

/**
 * Created by jtyler17 on 7/21/18.
 */

public class GameMapFragment extends Fragment {

    private GameMapView _gameMap;

    public static GameMapFragment newInstance() {
        return new GameMapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_map, container, false);

        _gameMap = v.findViewById(R.id.game_map);
        _gameMap.initializeData(null);
//        _gameMap.setRouteOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (view instanceof RouteView) {
//                    RouteView rv = (RouteView) view;
//                    ViewParent parent = rv.getParent();
//                    if (parent == _gameMap) { // in this case, we DO want to compare references
//                        _gameMap.routeSelected(rv);
//                        rv.setSelected(true);
//                    }
//                }
//            }
//        });
        return v;
    }
}
