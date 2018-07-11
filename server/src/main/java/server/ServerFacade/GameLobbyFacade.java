package server.ServerFacade;

import shared.Command.GenericCommand;
import shared.Command.ICommand;
import shared.Response.GenericResponse;

//Package private, only ServerFacade should touch these
class GameLobbyFacade {

    public static GenericResponse leaveGame(String username, String gameID)
    {
        return null;
    }

    public static GenericResponse startGame(String gameID)
    {
        return null;
    }

    public static GenericResponse sendMessage(String gameID, String message)
    {
        return null;
    }
}
