package client.view.fragment.train_card_select;

import client.view.fragment.ILoadingScreenFragment;
import shared.model.decks.TrainCard;

public interface ITrainCardSelectView extends ILoadingScreenFragment {

    public void clearCards();
    public void switchToGameMap();
    public void setCardSelectEnabled(boolean value);
    public void setEnabledCloseDialog(boolean value);
    public void setSelectionSubmitEnabled(boolean value);
    public void showToast(String message);
    public boolean addCard(TrainCard card);

}
