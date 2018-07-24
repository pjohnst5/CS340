package client.view.fragment.game.play;

import shared.model.decks.DestCard;

public interface IDestinationCardSelectView {

    public void switchToGameMap();
    public void showToast(String message);
    public boolean addCard(DestCard card);
}
