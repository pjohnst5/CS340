package client.view.fragment.game_map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import java.util.ArrayList;
import java.util.List;

import client.model.ClientModel;
import client.presenter.game_map.GameMapPresenter;
import client.presenter.game_map.IGameMapPresenter;
import client.view.fragment.game_map.customview.GameMapView;
import shared.enumeration.CityManager;
import shared.model.GameMap;
import shared.model.Route;

/**
 * Created by jtyler17 on 7/21/18.
 */

public class GameMapFragment extends Fragment implements IGameMapView, GameMapView.HostFragment {

    private GameMapView _gameMap;
    private IGameMapPresenter _presenter;

    public static GameMapFragment newInstance() {
        return new GameMapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_map, container, false);

        _gameMap = v.findViewById(R.id.game_map);
        GameMap mapModel = ClientModel.getInstance().getCurrentGame().getMap();
        List<Route> routes = new ArrayList<>(mapModel.get_routes().values());
//        List<Route> routes = new ListOfRoutes().getRoutes();
        _gameMap.initializeData(this, CityManager.getInstance().getCities(), routes);
        _presenter = new GameMapPresenter(this);

        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        _presenter.destroy();
    }

    @Override
    public void RouteSelected(Route route) {
        _presenter.routeSelected(route);
    }

    @Override
    public void updateMap() {
        _gameMap.redraw();
    }

    @Override
    public void setPresenter(IGameMapPresenter presenter) {
        _presenter = presenter;
    }

    @Override
    public void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
    }
}
