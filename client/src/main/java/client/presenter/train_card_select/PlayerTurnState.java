package client.presenter.train_card_select;

import java.util.List;

import client.facade.ServicesFacade;
import client.model.ClientModel;
import shared.model.decks.TrainCard;

public class PlayerTurnState extends TrainCardSelectState {

    private ClientModel _model;
    private ServicesFacade _facade;

    public PlayerTurnState(ITrainCardSelectPresenter presenter) {
        super(presenter);
        _model = ClientModel.getInstance();
        _facade = new ServicesFacade();
    }

    @Override
    public void enterState() {

        if (!presenter().trainCardsLoaded()){
            List<TrainCard> faceUpCards = _model.getCurrentGame().getTrainDeck().getFaceUpTrainCards();
            presenter().setCards(faceUpCards);
        }
    }

    @Override
    public void update() {
        if (!_model.isMyTurn()) {
            presenter().setState(new PlayerTurnWaitingState(presenter()));
            return;
        }

        if (!presenter().trainCardsLoaded()){
            List<TrainCard> faceUpCards = _model.getCurrentGame().getTrainDeck().getFaceUpTrainCards();
            presenter().setCards(faceUpCards);
        }
    }

    @Override
    public void submitData(TrainCard keep) {
        if (keep == null){
            _facade.drawFaceDownCard(presenter(), _model.getCurrentPlayer());
            presenter().setState(new PlayerDrewCardState(presenter()));
        } else {
            _facade.drawFaceUpCard(presenter(), keep, _model.getCurrentPlayer());
        }
    }
}
