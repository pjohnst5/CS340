package client.presenter.train_card_select;

import java.util.List;

import client.model.ClientModel;
import shared.model.decks.TrainCard;

public class PlayerTurnWaitingState extends TrainCardSelectState {

    private ClientModel _model;

    public PlayerTurnWaitingState(ITrainCardSelectPresenter presenter) {
        super(presenter);
        _model = ClientModel.getInstance();
    }

    @Override
    public void enterState() {
        presenter().setEnableCloseDialog(true);
        presenter().setEnableCardSelect(false);
        presenter().setEnableSelectionSubmit(false);
    }

    @Override
    public void update() {
        if (_model.isMyTurn()){
            presenter().setState(new PlayerTurnState(presenter()));
        }

            List<TrainCard> faceUpCards = _model.getCurrentGame().getTrainDeck().getFaceUpTrainCards();
            presenter().setCards(faceUpCards);
    }

    @Override
    public void submitData(TrainCard keep) { }
}
