package client.server.task;

import android.os.AsyncTask;
import android.util.Log;

import client.ClientFacade.GameListFacade;
import client.ClientFacade.LoginFacade;
import shared.Game;

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
        Object object = objects[0];
        try {
            if (object instanceof LoginTask) {
                Log.i(TAG, "Calling LoginFacade.login()...");
                LoginTask request = (LoginTask) object;
                LoginFacade.login(request.get_username(), request.get_password());
            } else if (object instanceof RegisterTask) {
                Log.i(TAG, "Calling LoginFacade.register()...");
                RegisterTask request = (RegisterTask) object;
                LoginFacade.register(request.get_username(), request.get_password());
            } else if (object instanceof Game) {
                Log.i(TAG, "Calling GameListFacade.createGame()...");
                //GameListFacade.createGame((Game) object);
            } else if (object instanceof JoinGameTask) {
                Log.i(TAG, "Calling GameListFacade.joinGame()...");
                String gameId = ((JoinGameTask) object).get_gameId();
                //GameListFacade.joinGame(gameId);
            }
        } catch (Exception e) {
            Log.i(TAG, "Server-side exception thrown");
            e.getStackTrace();

        }
        Log.e(TAG, "Invalid argument");
        return null;
    }

    @Override
    protected void onPostExecute(Exception exception) {
        if (exception != null) {
            caller.onServerResponseComplete(exception);
        }
    }
}
