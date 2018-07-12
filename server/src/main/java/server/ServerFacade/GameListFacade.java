package server.ServerFacade;

import server.ServerModel.ServerModel;
import server.exception.ServerException;

import shared.model.Game;
import shared.model.request.JoinRequest;
import shared.model.response.IResponse;
import shared.model.response.CommandResponse;



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
