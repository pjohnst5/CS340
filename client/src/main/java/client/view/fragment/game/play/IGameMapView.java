package client.view.fragment.game.play;

import client.presenter.game.play.IGameMapPresenter;

/**
 * Created by jtyler17 on 7/21/18.
 */

public interface IGameMapView {
    public void setPresenter(IGameMapPresenter presenter);
    public void showToast(String message);
}
