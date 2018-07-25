package client.presenter.game_map;

import java.util.List;

import shared.model.Player;
import shared.model.Route;

public interface IGameMapPresenter {
    public void routeSelected(Route route);
    public List<Player> getPlayers();
    public String getCurrentTurnPlayerId();
    public void destroy();
}
