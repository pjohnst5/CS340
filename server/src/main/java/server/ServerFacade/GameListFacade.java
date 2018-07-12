package server.ServerFacade;

import java.util.List;

import server.ServerModel.ServerModel;
import server.exception.ServerException;

import shared.Command.GenericCommand;
import shared.Command.ICommand;
import shared.configuration.ConfigurationManager;
import shared.model.Game;
import shared.model.User;
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

            //Make list of active games
            List<Game> activeGames = serverModel.getGames();

            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_set_games_method");
            String[] paramTypes = {activeGames.getClass().getCanonicalName()};
            Object[] paramValues = {activeGames};

            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);

            response.addCommand(command);
            response.setSuccess(true);

        } catch(ServerException e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public static IResponse joinGame(JoinRequest joinRequest)
    {
        return null;
    }


}
