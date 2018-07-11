package client.server.communication;

import client.server.communication.poll.Poller;
import shared.Command.ICommand;
import shared.CustomExceptions.ServerProxyException;
import shared.Response.CommandResponse;

public class ServerProxy {

    private ServerProxy(){}
    private static ServerProxy _instance = new ServerProxy();

    public static ServerProxy instance(){
        return _instance;
    }

    private Poller _poller;

    public void sendCommand(ICommand command) throws ServerProxyException {
        CommandResponse response = (CommandResponse)ClientCommunicator.sendCommand(command);

        if (response.getSuccess()){
            ICommand[] commands = response.getCommands();

            for (int i = 0; i <= commands.length; i++){
                commands[i].execute();
            }

            return;
        }

        throw new ServerProxyException("Server failed to return a successful respnose");
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
