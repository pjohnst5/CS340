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

        GenericCommand loginCommand = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                "login",
                paramTypes,
                paramValues,
                null);
        serverProxy.sendCommand(loginCommand);
    }

    public static void register(String username, String password) throws ServerProxyException {
        String[] paramTypes = {String.class.getCanonicalName(), String.class.getCanonicalName()};
        Object[] paramValues = {username, password};

        GenericCommand registerCommand = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                "register",
                paramTypes,
                paramValues,
                null);
        serverProxy.sendCommand(registerCommand);
    }
}
