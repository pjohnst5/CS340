package client.view.fragment.game.play;

import shared.model.decks.DestCard;

public interface IDestCardSelectView {

    public void switchToGameMap();
    public void  disableCardSelect();
    public void disableSubmitButton();

    public void hideOverlayMessage();
    public void showOverlayMessage(String message);
    public void showToast(String message);
    public boolean addCard(DestCard card);
}
