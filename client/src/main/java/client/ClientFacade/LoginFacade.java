package client.ClientFacade;

import client.server.communication.ServerProxy;
import shared.Command.GenericCommand;
import shared.CustomExceptions.ServerProxyException;
import shared.configuration.ConfigurationManager;

public class LoginFacade {
    static ServerProxy serverProxy = ServerProxy.instance();

    public static void login(String username, String password) throws ServerProxyException {
        String[] paramTypes = {String.class.getCanonicalName(), String.class.getCanonicalName()};
        Object[] paramValues = {username, password};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_login_method"),
                paramTypes,
                paramValues,
                null);
        serverProxy.sendCommand(command);
    }

    public static void register(String username, String password) throws ServerProxyException {
        String[] paramTypes = {String.class.getCanonicalName(), String.class.getCanonicalName()};
        Object[] paramValues = {username, password};

        GenericCommand command = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                ConfigurationManager.getString("server_register_method"),
                paramTypes,
                paramValues,
                null);
        serverProxy.sendCommand(command);
    }
}
