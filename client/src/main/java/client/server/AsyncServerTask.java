package client.server;

import android.os.AsyncTask;
import android.util.Log;

import client.server.communication.ServerProxy;
import shared.command.ICommand;
import shared.exception.ServerProxyException;

/**
 * Created by jtyler17 on 6/11/18.
 */

public class AsyncServerTask extends AsyncTask<Object, Void, Exception> {
    private final String TAG = "AsyncServerTask";

    public interface AsyncCaller {
        void onServerResponseComplete(Exception exception);
    }
    private AsyncCaller caller;
    public AsyncServerTask(AsyncCaller caller) {this.caller = caller;}

    @Override
    protected Exception doInBackground(Object[] objects) {
        Exception returnMessage = null;
        for (Object o : objects) {
            if (o instanceof ICommand) {
                try {
                    ServerProxy.instance().sendCommand((ICommand) o);
                } catch (ServerProxyException e) {
                    Log.i(TAG, "Server-side exception thrown");
                    e.printStackTrace();
                    returnMessage = e;
                }
            }
        }
        return returnMessage;
    }

    @Override
    protected void onPostExecute(Exception exception) {
        if (exception != null) {
            caller.onServerResponseComplete(exception);
        }
    }
}
