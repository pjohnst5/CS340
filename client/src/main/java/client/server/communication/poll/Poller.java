package client.server.communication.poll;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public abstract class Poller {
    private static boolean _initialized = false;

    private static Timer _timer;
    private static Map<String, PollerTask> _callbacks;

    public abstract boolean start();
    public abstract boolean stop();


    protected Poller(){
        if (!_initialized) {
            _callbacks = new HashMap<>();
            _timer = new Timer();
            _initialized = true;
        }
    }

    protected boolean _start(String identifier, int interval){

        if (!_callbacks.containsKey(identifier)) return false;

        PollerTask task = _callbacks.get(identifier);
        if (task.isRunning()) return true;

        task.setIsRunning(true);

        _timer.schedule(timerTask(identifier), task.getInterval());

        return true;
    }

    private TimerTask timerTask(String identifier) {
        PollerTask task = _callbacks.get(identifier);
        return new TimerTask() {

            private String _identifier = identifier;

            @Override
            public void run() {
                if (!_callbacks.get(_identifier).isRunning()) return;

                _callbacks.get(identifier).getCallback().run();
                _timer.schedule(timerTask(identifier), _callbacks.get(_identifier).getInterval());

            }
        };
    }

    protected boolean _stop(String identifier){

        if (!_callbacks.containsKey(identifier)) return false;

        _callbacks.get(identifier).setIsRunning(false);
        return true;

    }

    protected void addCallback (String identifier, PollerTask task){
            _callbacks.put(identifier, task);
    }

    @Override
    public String toString() {
        return this.getClass().getCanonicalName();
    }

    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }
}
