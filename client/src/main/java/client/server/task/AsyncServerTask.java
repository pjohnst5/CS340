package client.server.task;

import android.os.AsyncTask;
import android.util.Log;

import client.ClientFacade.GameListFacade;
import client.ClientFacade.GameLobbyFacade;
import client.ClientFacade.LoginFacade;
import shared.model.Game;
import shared.model.Message;
import shared.model.request.JoinRequest;

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
                GameListFacade.createGame((Game) object);
            } else if (object instanceof JoinRequest) {
                Log.i(TAG, "Calling GameListFacade.joinGame()...");
                GameListFacade.joinGame((JoinRequest) object); // FIXME: apply fix to gamelistview
            } else if (object instanceof StartGameTask) {
                Log.i(TAG, "Calling GameLobbyFacade.startGame()...");
                String gameId = ((StartGameTask) object).get_gameId();
                GameLobbyFacade.startGame(gameId);
            } else if (object instanceof LeaveGameTask) {
                Log.i(TAG, "Calling GameLobbyFacade.leaveGame()...");
                String gameId = ((LeaveGameTask) object).get_gameId();
                String username = ((LeaveGameTask) object).get_username();
                GameLobbyFacade.leaveGame(gameId, username);
            } else if (object instanceof Message) {
                Log.i(TAG, "Calling GameLobbyFacade.sendMessage()...");
                GameLobbyFacade.sendMessage((Message) object);
            }
        } catch (Exception e) {
            Log.i(TAG, "Server-side exception thrown");
            e.getStackTrace();
            return e;
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
