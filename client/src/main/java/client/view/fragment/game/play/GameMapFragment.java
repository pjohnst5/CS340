package client.view.fragment.game.play;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import client.presenter.game.play.ChatPresenter;
import client.presenter.game.play.GameMapPresenter;
import client.presenter.game.play.IGameMapPresenter;
import client.view.fragment.game.play.customview.GameMapView;
import client.view.fragment.game.play.customview.RouteView;
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
        _gameMap.initializeData(this);
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
