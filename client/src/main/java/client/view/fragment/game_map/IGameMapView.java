package client.view.fragment.game_map;

import client.presenter.game_map.IGameMapPresenter;

/**
 * Created by jtyler17 on 7/21/18.
 */

public interface IGameMapView {
    public void updateMap();
    public void updatePlayerTurn();
    public void updateDeckCount(int destCards, int trainCards);
    public void setClaimRouteButtonEnabled(boolean enabled);
    public void setPresenter(IGameMapPresenter presenter);
    public void showToast(String message);
}
