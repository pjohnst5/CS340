package client.server.communication;

import client.server.communication.poll.Poller;
import shared.Command.ICommand;

public class ServerProxy {

    private Poller _poller;

    public void sendCommand(ICommand command){
        ClientCommunicator.sendCommand(command);
    }

    public void usePoller(Poller poller){

        if (poller == _poller) return;
        if (_poller != null) poller.stop();

        _poller = poller;
        _poller.start();

    }

    public void stopPoller(){
        if (_poller != null){
            _poller.stop();
            _poller = null;
        }
    }
}
