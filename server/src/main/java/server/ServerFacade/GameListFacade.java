package server.ServerFacade;

import shared.CustomEnumerations.PlayerColor;
import shared.GameRequest;
import shared.Response.CommandResponse;
import shared.Response.GameListResponse;

//Package private, only ServerFacade should touch these
class GameListFacade {

    public static GameListResponse createGame(GameRequest gameRequest)
    {
        return null;
    }

    public static CommandResponse joinGame(String username, String gameID, PlayerColor color)
    {
        return null;
    }


}
