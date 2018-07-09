package client.server.communication.poll;

import shared.configuration.ConfigurationManager;

public class GameListPoller extends Poller {

    private static final String TASK_ID = GameListPoller.class.getName();
    private static final int TASK_INTERVAL = ConfigurationManager.getInt("poller_interval");

    private PollerTask _task;

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
        // TODO: Implement Server Calls
        System.out.println("Generated Output from " + TASK_ID);
    }


}
