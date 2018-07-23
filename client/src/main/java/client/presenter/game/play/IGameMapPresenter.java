package client.presenter.game.play;

import shared.model.Route;

public interface IGameMapPresenter {
    public void routeSelected(Route route);
    public void destroy();
}
