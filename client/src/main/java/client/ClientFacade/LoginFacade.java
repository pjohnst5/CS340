package client.ClientFacade;

import shared.Command.GenericCommand;

public class LoginFacade {

    public static void login(String username, String password) {
        String[] paramTypes = {String.class.getCanonicalName(), String.class.getCanonicalName()};
        Object[] paramValues = {username, password};

        GenericCommand loginCommand = new GenericCommand(
                "server.ServerFacade.ServerFacade",
                "login",
                paramTypes,
                paramValues,
                null);
    }

    public static void register(String username, String password){
        String[] paramTypes = {String.class.getCanonicalName(), String.class.getCanonicalName()};
        Object[] paramValues = {username, password};

        GenericCommand loginCommand = new GenericCommand(
                "server.ServerFacade.ServerFacade",
                "register",
                paramTypes,
                paramValues,
                null);
    }
}
