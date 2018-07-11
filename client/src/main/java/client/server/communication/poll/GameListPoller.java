package client.server.communication.poll;

import client.server.communication.ServerProxy;
import shared.Command.GenericCommand;
import shared.Command.ICommand;
import shared.CustomExceptions.ServerProxyException;
import shared.configuration.ConfigurationManager;

public class GameListPoller extends Poller {

    private static final String TASK_ID = GameListPoller.class.getName();
    private static final int TASK_INTERVAL = ConfigurationManager.getInt("poller_interval");

    private PollerTask _task;
    private ICommand _serverRequest;

    private static GameListPoller _instance = new GameListPoller();
    public static GameListPoller instance() { return _instance; }
    private GameListPoller(){

        super();

        System.out.println("Class NAME: " + TASK_ID);

        // Add Poller Object to Timer
        _task = new PollerTask();
        _task.setCallback(() -> callback());
        _task.setIsRunning(false);
        _task.setIdentifier(TASK_ID);
        _task.setInterval(TASK_INTERVAL);

        addCallback(TASK_ID, _task);

        String[] paramTypes = {};
        Object[] paramValues = {};

        _serverRequest = new GenericCommand(
                "server.CommandManager",
                "GameListPoll",
                paramTypes,
                paramValues,
                null
        );
    }

    @Override
    public boolean start(){
        return _instance._start(TASK_ID, TASK_INTERVAL);
    }

    @Override
    public boolean stop(){
        return _instance._stop(TASK_ID);
    }

    private static void callback(){
        try {

            ServerProxy.instance().sendCommand(_instance._serverRequest);
            System.out.println("Generated Output from " + TASK_ID);

        } catch (ServerProxyException e) {

            // TODO: Give real feedback
            System.out.println(TASK_ID + " Failed");

        }
    }


}
