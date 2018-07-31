package client.presenter.train_card_select;

import client.facade.ServicesFacade;
import client.model.ClientModel;
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
    public void update() {

    }

    @Override
    public void submitData(TrainCard keep) {

    }
}
