package client.server.communication.poll;

import client.server.communication.ServerProxy;
import shared.Command.GenericCommand;
import shared.Command.ICommand;
import shared.configuration.ConfigurationManager;

public class GameLobbyPoller extends Poller {

    private static final String TASK_ID = GameLobbyPoller.class.getName();
    private static final int TASK_INTERVAL = ConfigurationManager.getInt("poller_interval");

    private PollerTask _task;
    private ICommand _serverRequest;

    private static GameLobbyPoller _instance = new GameLobbyPoller();
    public static GameLobbyPoller instance() { return _instance; }
    private GameLobbyPoller(){

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
                "GameLobbyPoll",
                paramTypes,
                paramValues,
                null
        );
    }

    public boolean start() {
        return _instance._start(TASK_ID, TASK_INTERVAL);
    }

    public boolean stop() {
        return _instance._start(TASK_ID, TASK_INTERVAL);
    }



    private static void callback(){
        // TODO: Setup Callback function - Setup Server Calls
        ServerProxy.instance().sendCommand(_instance._serverRequest);
        System.out.println("\u001B[31m" + "Generated Output from " + TASK_ID + "\u001B[0m");
    }


}