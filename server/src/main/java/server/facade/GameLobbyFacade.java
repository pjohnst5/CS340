package server.facade;

import shared.Command.GenericCommand;
import shared.Command.ICommand;

public class GameLobbyFacade {

    public static ICommand leaveGame(String username, String gameID)
    {
        String[] array = new String[2];
        return new GenericCommand("","",array,null,null);
    }

    public static ICommand startGame(String gameID)
    {
        String[] array = new String[2];
        return new GenericCommand("","",array,null,null);
    }

    public static ICommand sendMessage(String gameID, String message)
    {
        String[] array = new String[2];
        return new GenericCommand("","",array,null,null);
    }
}
