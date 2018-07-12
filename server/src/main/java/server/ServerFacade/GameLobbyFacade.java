package server.ServerFacade;

import shared.Message;
import shared.Response.IResponse;

//Only ServerFacade should touch these
class GameLobbyFacade {

    public static IResponse leaveGame(String username, String gameID)
    {
        return null;
    }

    public static IResponse startGame(String gameID)
    {
        return null;
    }

    public static IResponse sendMessage(Message message)
    {
        return null;
    }
}
