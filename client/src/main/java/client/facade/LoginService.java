package client.facade;

import client.server.AsyncServerTask;
import shared.command.GenericCommand;
import shared.configuration.ConfigurationManager;

public class LoginService {

    public static void login(AsyncServerTask.AsyncCaller caller, String username, String password) {
        String[] paramTypes = {String.class.getCanonicalName(), String.class.getCanonicalName()};
        Object[] paramValues = {username, password};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_login_method"),
                paramTypes,
                paramValues,
                null);

        new AsyncServerTask(caller).execute(command);
    }

    public static void register(AsyncServerTask.AsyncCaller caller, String username, String password) {
        String[] paramTypes = {String.class.getCanonicalName(), String.class.getCanonicalName()};
        Object[] paramValues = {username, password};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_register_method"),
                paramTypes,
                paramValues,
                null);

        new AsyncServerTask(caller).execute(command);
    }
}
