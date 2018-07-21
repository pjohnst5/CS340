package client.presenter.game.play;

import java.util.Observable;
import java.util.Observer;

import client.server.AsyncServerTask;

public class DestinationCardSelectPresenter
        implements IDestinationCardSelectPresenter, Observer, AsyncServerTask.AsyncCaller {


    @Override
    public void onServerResponseComplete(Exception exception) {

    }

    @Override
    public void update(Observable o, Object arg) {

    }
}
