package client.presenter.game.play;

import java.util.Observable;
import java.util.Observer;

import client.model.ClientModel;
import client.server.AsyncServerTask;
import client.view.fragment.game.play.IDestinationCardSelectView;
import shared.enumeration.CityName;
import shared.model.City;
import shared.model.decks.DestCard;

public class DestinationCardSelectPresenter
        implements IDestinationCardSelectPresenter, Observer, AsyncServerTask.AsyncCaller {

    private IDestinationCardSelectView _selectView;
    private ClientModel _model;

    public DestinationCardSelectPresenter(IDestinationCardSelectView selectView){

        _selectView = selectView;
        _model = ClientModel.getInstance();
        _model.addObserver(this);
        //update();

    }

    @Override
    public void destroy() {
        _model.deleteObserver(this);
    }

    @Override
    public void onServerResponseComplete(Exception exception) {

    }

    private void update() {
        _selectView.addCard(new DestCard(new City(CityName.LOS_ANGELES), new City(CityName.NEW_YORK_CITY), 21));
        _selectView.addCard(new DestCard(new City(CityName.DULUTH), new City(CityName.HOUSTON), 13));
        _selectView.addCard(new DestCard(new City(CityName.SAULT_STE_MARIE), new City(CityName.NASHVILLE), 8));
    }

    @Override
    public void update(Observable o, Object arg) {
        update();
    }
}
