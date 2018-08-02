package client.view.fragment.select_dest_card;

import shared.model.decks.DestCard;

public interface IDestCardSelectView {

    public void switchToGameMap();
    public void  disableCardSelect();
    public void disableSubmitButton();

    public void hideOverlayMessage();
    public void showOverlayMessage(String message);
    public void setNumCardsRequiredText(String number);
    public void showToast(String message);
    public boolean addCard(DestCard card);
}
