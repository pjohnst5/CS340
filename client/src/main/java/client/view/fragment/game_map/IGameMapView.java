package client.view.fragment.game_map;

import java.util.List;

import client.presenter.game_map.IGameMapPresenter;
import shared.model.Player;
import shared.model.Route;

/**
 * Created by jtyler17 on 7/21/18.
 */

public interface IGameMapView {
    public void updateMap(List<Route> routes);
    public void updatePlayerTurn();
    public void updateDeckCount(int destCards, int trainCards);
    public void setClaimRouteButtonEnabled(boolean enabled);
    public void setSelectTrainCardEnabled(boolean enabled);
    public void setSelectDestCardEnabled(boolean enabled);
    public void setPresenter(IGameMapPresenter presenter);
    public void showToast(String message);
    public void gameOver();
}
