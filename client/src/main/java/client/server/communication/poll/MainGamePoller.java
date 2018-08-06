package client.server.communication.poll;

import client.model.ClientModel;
import client.server.communication.ServerProxy;
import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.configuration.ConfigurationManager;
import shared.exception.ServerProxyException;

public class MainGamePoller extends Poller {

    private static final String TASK_ID = MainGamePoller.class.getName();
    private static final int TASK_INTERVAL = ConfigurationManager.getInt("poller_interval");

    private PollerTask _task;
    private ICommand _serverRequest;

    private static MainGamePoller _instance = new MainGamePoller();
    public static MainGamePoller instance() { return _instance; }

    private MainGamePoller(){
        super();

        System.out.println("Class NAME: " + TASK_ID);

        // Add Poller Object to Timer
        _task = new PollerTask();
        _task.setCallback(() -> callback());
        _task.setIsRunning(false);
        _task.setIdentifier(TASK_ID);
        _task.setInterval(TASK_INTERVAL);

        addCallback(TASK_ID, _task);

        String[] paramTypes = {
                String.class.getCanonicalName(),
                String.class.getCanonicalName()
        };

        Object[] paramValues = {
                ClientModel.getInstance().getCurrentGame().getGameID(),
                ClientModel.getInstance().getUser().get_playerId()

        };

        _serverRequest = new GenericCommand(
                ConfigurationManager.get("server_facade_name"),
                ConfigurationManager.get("server_get_commands_method"),
                paramTypes,
                paramValues,
                null
        );

    }

    public boolean start() {

        // Update for when the game and playerId's change
        String[] paramTypes = {
                String.class.getCanonicalName(),
                String.class.getCanonicalName()
        };

        Object[] paramValues = {
                ClientModel.getInstance().getCurrentGame().getGameID(),
                ClientModel.getInstance().getUser().get_playerId()

        };

        _instance._serverRequest = new GenericCommand(
                ConfigurationManager.get("server_facade_name"),
                ConfigurationManager.get("server_get_commands_method"),
                paramTypes,
                paramValues,
                null
        );

        return _instance._start(TASK_ID, TASK_INTERVAL);
    }

    public boolean stop() {
        return _instance._stop(TASK_ID);
    }

    private static void callback(){
        try {
            ServerProxy.instance().sendCommand(_instance._serverRequest);
            System.out.println("Generated Output from " + TASK_ID);
        } catch (ServerProxyException e) {
            // TODO: Give real feedback
            System.out.println("Poller " + TASK_ID + " failed");
        }
    }
}
