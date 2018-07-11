package server.ServerFacade;

import shared.Command.GenericCommand;
import shared.Command.ICommand;
import shared.CustomEnumerations.PlayerColor;
import shared.GameRequest;

//Package private, only ServerFacade should touch these
class GameListFacade {

    public static ICommand createGame(GameRequest gameRequest)
    {
        String[] array = new String[2];
        return new GenericCommand("","",array,null,null);
    }

    public static ICommand joinGame(String username, String gameID, PlayerColor color)
    {
        String[] array = new String[2];
        return new GenericCommand("","",array,null,null);
    }


}
