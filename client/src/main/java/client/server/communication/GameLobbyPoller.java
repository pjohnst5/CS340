package client.server.communication;

public class GameLobbyPoller extends Poller {

    private static final String TASK_ID = GameLobbyPoller.class.getName();
    private static final int TASK_INTERVAL = 2000;

    private PollerTask _task;

    private static GameLobbyPoller _instance = new GameLobbyPoller();
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
    }

    public static void start(){
        _instance._start(TASK_ID, TASK_INTERVAL);
    }

    public static void stop(){
        _instance._stop(TASK_ID);
    }

    private static void callback(){
        // TODO: Setup Callback function - Setup Server Calls
        System.out.println("\u001B[31m" + "Generated Output from " + TASK_ID + "\u001B[0m");
    }


}