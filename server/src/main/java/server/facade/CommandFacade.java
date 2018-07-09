package server.facade;

import java.util.ArrayList;
import java.util.List;

import shared.Command.GenericCommand;
import shared.Game;

public class CommandFacade {

    public static List<Game> getCurrentGames()
    {
        return new ArrayList<>();
    }

    public static List<GenericCommand> getCommandsFromGame(String gameID, int index)
    {
        return new ArrayList<>();
    }
}
