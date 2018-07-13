package server.ServerFacade;

import shared.CustomEnumerations.PlayerColor;
import shared.GameRequest;
import shared.Response.CommandResponse;
import shared.Response.IResponse;

//Only ServerFacade should touch these
class GameListFacade {

    public static IResponse createGame(GameRequest gameRequest)
    {
        return null;
    }

    public static IResponse joinGame(String username, String gameID, PlayerColor color)
    {
        return null;
    }


}
