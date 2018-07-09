package server.ServerFacade;

import java.util.UUID;

//Package private, only ServerFacade should touch these
class LoginFacade {

    public static UUID login(String userName, String password)
    {
        return UUID.fromString("Hello");
    }

    public static UUID register(String username, String password)
    {
        return UUID.fromString("Hello");
    }
}
