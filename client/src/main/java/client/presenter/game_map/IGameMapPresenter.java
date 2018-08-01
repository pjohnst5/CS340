package client.presenter.game_map;

import java.util.List;
import java.util.Map;

import shared.enumeration.TrainColor;
import shared.model.Player;
import shared.model.Route;

public interface IGameMapPresenter {
    public void pause();
    public void resume();
    public void update();
    public Player getMyPlayer();
    public List<Player> getPlayers();
    public void routeSelected(Route route);
    public String getCurrentTurnPlayerId();
    public boolean isLastRound();
    public void claimRoute(Route route, Map<TrainColor, Integer> selectedCards);
}
