package client.view.fragment.game.play;

import shared.model.GameAction;

public interface IGameHistoryView {

    public void showToast(String message);
    public boolean addAction(GameAction action);
}
