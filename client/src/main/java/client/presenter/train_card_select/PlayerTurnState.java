package client.presenter.train_card_select;

import java.util.ArrayList;
import java.util.List;

import client.facade.ServicesFacade;
import client.model.ClientModel;
import shared.enumeration.GameState;
import shared.enumeration.TrainColor;
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
        presenter().setEnableCloseDialog(true);
        if (!presenter().trainCardsLoaded()){
            List<TrainCard> modelFaceUpCards = _model.getCurrentGame().getTrainDeck().getFaceUpTrainCards();

            // This is important so that we don't add the face down card to the deck
            List<TrainCard> faceUpCards = new ArrayList<>(modelFaceUpCards);
            faceUpCards.add(new TrainCard(TrainColor.CARD_BACK));
            presenter().setCards(faceUpCards);
        }
    }

    @Override
    public void exitState() {
        presenter().setEnableSelectionSubmit(false);
    }

    @Override
    public void update() {
        if (_model.getCurrentGame().get_state() == GameState.FINISHED) {
            presenter().switchToGameMap();
        }
        if (!_model.isMyTurn()) {
            presenter().setState(new PlayerTurnWaitingState(presenter()));
        }

        if (!presenter().trainCardsLoaded()){
            List<TrainCard> faceUpCards = _model.getCurrentGame().getTrainDeck().getFaceUpTrainCards();
            presenter().setCards(faceUpCards);
        }
    }

    @Override
    public void submitData(TrainCard keep) {
        if (keep.get_color() == TrainColor.CARD_BACK){
            _facade.drawFaceDownCard(presenter(), _model.getCurrentPlayer());
        } else {
            _facade.drawFaceUpCard(presenter(), keep, _model.getCurrentPlayer());
        }
        presenter().setState(new PlayerDrewCardState(presenter()));
    }
}
