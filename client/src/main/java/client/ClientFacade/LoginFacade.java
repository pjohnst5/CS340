package client.ClientFacade;

import shared.Command.GenericCommand;
import shared.configuration.ConfigurationManager;

public class LoginFacade {

    public static void login(String username, String password) {
        String[] paramTypes = {String.class.getCanonicalName(), String.class.getCanonicalName()};
        Object[] paramValues = {username, password};

        GenericCommand loginCommand = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                "login",
                paramTypes,
                paramValues,
                null);
    }

    public static void register(String username, String password){
        String[] paramTypes = {String.class.getCanonicalName(), String.class.getCanonicalName()};
        Object[] paramValues = {username, password};

        GenericCommand loginCommand = new GenericCommand(
                ConfigurationManager.getString("server_facade_name"),
                "register",
                paramTypes,
                paramValues,
                null);
    }
}
