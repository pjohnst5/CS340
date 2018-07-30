package client.view.fragment.game_map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pjohnst5icloud.tickettoride.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.model.ClientModel;
import client.presenter.game_map.GameMapPresenter;
import client.presenter.game_map.IGameMapPresenter;
import client.util.ColorPicker;
import client.view.dialog.ClaimRouteDialog;
import client.view.dialog.GameOverDialog;
import client.view.fragment.game_map.customview.GameMapView;
import shared.enumeration.CityManager;
import shared.enumeration.TrainColor;
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

    private Button _claimRouteButton;

    private static final String CLAIM_ROUTE_DIALOG_TAG = "ClaimRouteDialog";
    private static final String GAME_OVER_DIALOG_TAG = "GameOverDialog";
    private static final int CLAIM_ROUTE_DIALOG_CODE = 0;
    private static final int GAME_OVER_DIALOG_CODE = 1;

    private boolean _gameOver;

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

        _claimRouteButton = v.findViewById(R.id.game_map_claim_route_button);
        _claimRouteButton.setVisibility(View.INVISIBLE);
        _claimRouteButton.setEnabled(false);
        _claimRouteButton.setOnClickListener((view) -> {
            FragmentManager manager = getFragmentManager();
            ClaimRouteDialog dialog = ClaimRouteDialog.newInstance(_gameMap.getSelectedRoute().getId(), _presenter.getMyPlayer().getPlayerID());
            dialog.setTargetFragment(GameMapFragment.this, CLAIM_ROUTE_DIALOG_CODE);
            dialog.show(manager, CLAIM_ROUTE_DIALOG_TAG);
        });


        _gameMap = v.findViewById(R.id.game_map);
        GameMap mapModel = ClientModel.getInstance().getCurrentGame().getMap();
        List<Route> routes = new ArrayList<>(mapModel.get_routes().values());
        _gameMap.initializeData(this, CityManager.getInstance().getCities(), routes);
        _presenter = new GameMapPresenter(this);

        _players = _presenter.getPlayers();

        _gameOver = false;

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        _presenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        _presenter.pause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == CLAIM_ROUTE_DIALOG_CODE) {
            Map<TrainColor, Integer> selectedCards = (HashMap<TrainColor, Integer>) data.getSerializableExtra(ClaimRouteDialog.EXTRA_SELECTED_CARDS);
            _presenter.claimRoute(_gameMap.getSelectedRoute(), selectedCards);
        }
    }

    @Override
    public void RouteSelected(Route route) {
        if (route == null) {
            _claimRouteButton.setVisibility(View.INVISIBLE);
            _claimRouteButton.setEnabled(false);
        } else {
            _claimRouteButton.setVisibility(View.VISIBLE);
            _claimRouteButton.setEnabled(false);
            _presenter.routeSelected(route);
        }
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

    @Override
    public void setClaimRouteButtonEnabled(boolean enabled) {
        _claimRouteButton.setEnabled(enabled);
    }

    @Override
    public void gameOver() {
        if (!_gameOver) {
            _gameOver = true;
            FragmentManager manager = getFragmentManager();
            GameOverDialog dialog = GameOverDialog.newInstance();
            dialog.setTargetFragment(GameMapFragment.this, GAME_OVER_DIALOG_CODE);
            dialog.show(manager, GAME_OVER_DIALOG_TAG);
        }
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
