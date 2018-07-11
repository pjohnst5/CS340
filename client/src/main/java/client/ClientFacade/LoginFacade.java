package client.ClientFacade;

import shared.Command.GenericCommand;

public class LoginFacade {

    public static void login(String userName, String password) {
        String[] paramTypes = {String.class.getCanonicalName(), String.class.getCanonicalName()};
        Object[] paramValues = {userName, password};

        GenericCommand loginCommand = new GenericCommand(
                "server.ServerFacade.ServerFacade",
                "login",
                paramTypes,
                paramValues,
                null);
    }

    public static void register(String username, String password){
    }
}
