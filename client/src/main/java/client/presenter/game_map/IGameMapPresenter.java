package client.presenter.game_map;

import java.util.List;
import java.util.Map;

import shared.enumeration.TrainColor;
import shared.model.Player;
import shared.model.Route;

public interface IGameMapPresenter {
    public void routeSelected(Route route);
    public List<Player> getPlayers();
    public String getCurrentTurnPlayerId();
    public void destroy();
    public Player getMyPlayer();
    public void claimRoute(Route route, Map<TrainColor, Integer> selectedCards);
}
