package client.presenter.train_card_select;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import client.facade.ServicesFacade;
import client.model.ClientModel;
import shared.enumeration.GameState;
import shared.enumeration.TrainColor;
import shared.model.decks.TrainCard;

public class PlayerDrewCardState extends TrainCardSelectState {

    private ClientModel _model;
    private ServicesFacade _facade;
    private boolean submitted;
    private TrainCard keep;
    private boolean cardsLoaded;

    public PlayerDrewCardState(ITrainCardSelectPresenter presenter) {
        super(presenter);
        _model = ClientModel.getInstance();
        _facade = new ServicesFacade();
    }

    @Override
    public void enterState() {
        presenter().setEnableCloseDialog(false);
        cardsLoaded = false;
        update();
    }

    @Override
    public void update() {
        if (!_model.isMyTurn() || _model.getCurrentGame().get_state() == GameState.FINISHED) {
            presenter().setState(new PlayerTurnComplete(presenter()));

        } else if (!cardsLoaded) {

            List<TrainCard> modelFaceUpCards = _model.getCurrentGame().getTrainDeck().getFaceUpTrainCards();

            // This is important so that we don't add the face down card to the deck
            List<TrainCard> faceUpCards = new ArrayList<>(modelFaceUpCards);
            faceUpCards.add(new TrainCard(TrainColor.CARD_BACK));
            presenter().setCards(faceUpCards);
            cardsLoaded = true;
        }

    }

    @Override
    public void submitData(TrainCard keep) {
        this.keep = keep;
        submitted = true;
        if (keep.get_color() == TrainColor.CARD_BACK){
            _facade.drawFaceDownCard(presenter(), _model.getCurrentPlayer());
        } else {
            _facade.drawFaceUpCard(presenter(), keep, _model.getCurrentPlayer());
        }
        presenter().setEnableSelectionSubmit(false);
    }

    @Override
    public void serverResponseSuccess() {
        update();
    }

    @Override
    public void serverResponseFailure() {
        if (!submitted) return;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (keep.get_color() == TrainColor.CARD_BACK){
                    _facade.drawFaceDownCard(presenter(), _model.getCurrentPlayer());
                } else {
                    _facade.drawFaceUpCard(presenter(), keep, _model.getCurrentPlayer());
                }
                timer.cancel();
            }
        }, 100);
    }
}
