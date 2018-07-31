package client.presenter.train_card_select;

import java.util.List;

import shared.model.decks.TrainCard;

public class PlayerTurnWaitingState extends TrainCardSelectState {

    public PlayerTurnWaitingState(ITrainCardSelectPresenter presenter) {
        super(presenter);
    }

    @Override
    public void update() {

    }

    @Override
    public void submitData(List<TrainCard> keep, List<TrainCard> discard) {

    }
}
