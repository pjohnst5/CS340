package client.presenter.train_card_select;

import java.util.List;

import client.facade.ServicesFacade;
import client.model.ClientModel;
import shared.enumeration.TrainColor;
import shared.model.decks.TrainCard;

public class PlayerDrewCardState extends TrainCardSelectState {

    private ClientModel _model;
    private ServicesFacade _facade;

    public PlayerDrewCardState(ITrainCardSelectPresenter presenter) {
        super(presenter);
        _model = ClientModel.getInstance();
        _facade = new ServicesFacade();
    }

    @Override
    public void enterState() {
        presenter().setEnableCloseDialog(false);
    }

    @Override
    public void exitState() {
        presenter().switchToGameMap();
    }

    @Override
    public void update() {
        if (!_model.isMyTurn()) {
            exitState();
            return;
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
        exitState();
        return;
    }
}
