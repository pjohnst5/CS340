package client.server.task;

import android.os.AsyncTask;
import android.util.Log;

import client.ClientFacade.LoginFacade;

/**
 * Created by jtyler17 on 6/11/18.
 */

public class AsyncServerTask extends AsyncTask<Object, Void, Void> {
    private final String TAG = "AsyncServerTask";

    @Override
    protected Void doInBackground(Object[] objects) {
        Object object = objects[0];
        if (object instanceof LoginTask) {
            Log.i(TAG, "Calling LoginFacade.login()...");
            LoginTask request = (LoginTask) object;
            LoginFacade.login(request.get_username(), request.get_password());
        } else if (object instanceof RegisterTask) {
            Log.i(TAG, "Calling LoginFacade.register()...");
            RegisterTask request = (RegisterTask) object;
            LoginFacade.register(request.get_username(), request.get_password());
        }
        Log.e(TAG, "Invalid argument");
        return null;
    }
}
