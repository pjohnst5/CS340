package server.ServerFacade;

import server.ServerModel.ServerModel;
import server.exception.ServerException;
import shared.CustomEnumerations.PlayerColor;
import shared.Game;
import shared.JoinRequest;
import shared.Response.CommandResponse;
import shared.Response.IResponse;

//Only ServerFacade should touch these
class GameListFacade {

    public static IResponse createGame(Game game)
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();
        try {
            //adds game to serverModel
            serverModel.addGame(game);

            //Make commands to add game for client CommandFacade
            

        } catch(ServerException e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }

        return null;
    }

    public static IResponse joinGame(JoinRequest joinRequest)
    {
        return null;
    }


}
