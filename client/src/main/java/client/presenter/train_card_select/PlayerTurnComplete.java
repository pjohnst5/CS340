package client.presenter.train_card_select;

import shared.model.decks.TrainCard;

public class PlayerTurnComplete extends TrainCardSelectState {

    public PlayerTurnComplete(ITrainCardSelectPresenter presenter) {
        super(presenter);
    }

    @Override
    public void enterState() {
        presenter().setEnableCloseDialog(true);
        presenter().setEnableCardSelect(false);
        presenter().setEnableSelectionSubmit(false);
        presenter().switchToGameMap();
    }

    @Override
    public void update() {

    }

    @Override
    public void submitData(TrainCard keep) {

    }

    @Override
    public void serverResponseSuccess() {

    }

    @Override
    public void serverResponseFailure() {

    }
}
