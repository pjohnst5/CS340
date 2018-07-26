package client.view.fragment.game_map;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import java.util.ArrayList;
import java.util.List;

import client.model.ClientModel;
import client.presenter.game_map.GameMapPresenter;
import client.presenter.game_map.IGameMapPresenter;
import client.util.ColorPicker;
import client.view.fragment.game_map.customview.GameMapView;
import shared.enumeration.CityManager;
import shared.model.GameMap;
import shared.model.Player;
import shared.model.Route;

/**
 * Created by jtyler17 on 7/21/18.
 */

public class GameMapFragment extends Fragment implements IGameMapView, GameMapView.HostFragment {

    private GameMapView _gameMap;
    private IGameMapPresenter _presenter;
    private RecyclerView _turnIndicator;
    private List<Player> _players;

    private TextView _destCardCount;
    private TextView _trainCardCount;

    public static GameMapFragment newInstance() {
        return new GameMapFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_game_map, container, false);

        _turnIndicator = v.findViewById(R.id.game_map_player_turn_recycler_view);
        _turnIndicator.setLayoutManager(new LinearLayoutManager(getActivity()));
        _turnIndicator.setAdapter(new PlayerTurnAdapter());

        _destCardCount = v.findViewById(R.id.game_map_dest_card_count);
        _trainCardCount = v.findViewById(R.id.game_map_train_card_count);


        _gameMap = v.findViewById(R.id.game_map);
        GameMap mapModel = ClientModel.getInstance().getCurrentGame().getMap();
        List<Route> routes = new ArrayList<>(mapModel.get_routes().values());
        _gameMap.initializeData(this, CityManager.getInstance().getCities(), routes);
        _presenter = new GameMapPresenter(this);

        _players = _presenter.getPlayers();

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
        getActivity().runOnUiThread(() -> _gameMap.redraw());
    }

    @Override
    public void setPresenter(IGameMapPresenter presenter) {
        _presenter = presenter;
    }

    @Override
    public void showToast(String message) {
        getActivity().runOnUiThread(() -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void updatePlayerTurn() {
        _players = _presenter.getPlayers();

        getActivity().runOnUiThread(() -> _turnIndicator.setAdapter(new PlayerTurnAdapter()) );


    }

    private class PlayerTurnIndicatorHolder extends RecyclerView.ViewHolder {

        private LinearLayout _container;
        private TextView _playerTurnItemLabel;

        public PlayerTurnIndicatorHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.player_turn_indicator_item, parent, false));

            _container = itemView.findViewById(R.id.player_turn_item_highlight);
            _playerTurnItemLabel = itemView.findViewById(R.id.player_turn_item_label);
        }

        public void bind(Player player){

            Drawable playerColor = ColorPicker.turnOrderIndicator(getResources(), player.getColor());

            Drawable containerColor = getResources().getDrawable(R.color.train_black_border);

            if (player.getPlayerID().equals(_presenter.getCurrentTurnPlayerId())){
                containerColor = getResources().getDrawable(R.drawable.button_action_blue);
            }

            _playerTurnItemLabel.setText(player.getDisplayName());
            _playerTurnItemLabel.setBackground(playerColor);
            _container.setBackground(containerColor);
        }
    }

    @Override
    public void updateDeckCount(int destCards, int trainCards) {
        getActivity().runOnUiThread(() ->{
            _destCardCount.setText(Integer.toString(destCards));
            _trainCardCount.setText(Integer.toString(trainCards));
        });
    }

    private class PlayerTurnAdapter extends RecyclerView.Adapter<PlayerTurnIndicatorHolder> {
        @NonNull
        @Override
        public PlayerTurnIndicatorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new PlayerTurnIndicatorHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull PlayerTurnIndicatorHolder holder, int position) {
            holder.bind(_players.get(position));
        }

        @Override
        public int getItemCount() {
            return _players.size();
        }
    }
}
