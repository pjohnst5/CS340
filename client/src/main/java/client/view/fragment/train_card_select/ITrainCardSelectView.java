package client.view.fragment.train_card_select;

import shared.model.decks.TrainCard;

public interface ITrainCardSelectView {

    public void switchToGameMap();
    public void disableCardSelect();
    public void disableSubmitButton();
    public void showToast(String message);
    public boolean addCard(TrainCard card);

}
