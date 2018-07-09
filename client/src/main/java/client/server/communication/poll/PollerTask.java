package client.server.communication.poll;

/**
 * This class is left intentionally as a package protected class
 */
class PollerTask {
    private Runnable _callback;
    private int _interval;
    private boolean _isRunning;
    private String _indentifier;

    public PollerTask(){}

    public PollerTask(Runnable _runnable, int _interval, boolean _isRunning, String _indentifier) {
        this._callback = _runnable;
        this._interval = _interval;
        this._isRunning = _isRunning;
        this._indentifier = _indentifier;
    }


    public Runnable getCallback() {
        return _callback;
    }

    public void setCallback(Runnable _runnable) {
        this._callback = _runnable;
    }

    public int getInterval() {
        return _interval;
    }

    public void setInterval(int _interval) {
        this._interval = _interval;
    }

    public boolean isRunning() {
        return _isRunning;
    }

    public void setIsRunning(boolean _isRunning) {
        this._isRunning = _isRunning;
    }

    public String getIndentifier() {
        return _indentifier;
    }

    public void setIdentifier(String _indentifier) {
        this._indentifier = _indentifier;
    }
}
