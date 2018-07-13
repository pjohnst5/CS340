package server.facade;


import java.util.List;
import java.util.Map;

import server.model.ServerModel;
import server.exception.ServerException;
import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.configuration.ConfigurationManager;
import shared.model.Game;
import shared.model.response.CommandResponse;
import shared.model.response.IResponse;

//Package private, only ServerFacade should touch these
public class CommandManagerFacade {

    public static IResponse getActiveGames()
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        Map<String,Game> activeGames = serverModel.getGames();

        String className = ConfigurationManager.getString("client_facade_name");
        String methodName = ConfigurationManager.getString("client_set_games_method");
        String[] paramTypes = {activeGames.getClass().getCanonicalName()};
        Object[] paramValues = {activeGames};

        ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);

        response.addCommand(command);
        response.setSuccess(true);

        return response;
    }

    public static IResponse getCommandList(String gameID, String playerID)
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        try{
            List<ICommand> commands = serverModel.getCommands(gameID, playerID);

            response.setCommands(commands);
            response.setSuccess(true);

        }catch(ServerException e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }

        return response;
    }
}
