package client.presenter.game_map;

import shared.model.Route;

public interface IGameMapPresenter {
    public void routeSelected(Route route);
    public void destroy();
}
