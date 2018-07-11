package server.ServerFacade;

import shared.Command.GenericCommand;
import shared.Command.ICommand;
import shared.CustomEnumerations.PlayerColor;
import shared.GameRequest;
import shared.Response.GenericResponse;

//Package private, only ServerFacade should touch these
class GameListFacade {

    public static GenericResponse createGame(GameRequest gameRequest)
    {
        return null;
    }

    public static GenericResponse joinGame(String username, String gameID, PlayerColor color)
    {
        return null;
    }


}
