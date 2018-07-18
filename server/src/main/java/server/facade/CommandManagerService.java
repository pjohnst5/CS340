package server.facade;


import java.util.List;
import java.util.Map;

import server.model.ServerModel;
import server.exception.ServerException;
import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.configuration.ConfigurationManager;
import shared.model.Game;
import shared.model.GamesWrapper;
import shared.model.response.CommandResponse;
import shared.model.response.IResponse;

//Package private, only ServerFacade should touch these
class CommandManagerService {

    public static IResponse getActiveGames()
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        Map<String,Game> activeGames = serverModel.getGames();

        GamesWrapper wrapper = new GamesWrapper();

        wrapper.setGames(activeGames);

        String className = ConfigurationManager.getString("client_facade_name");
        String methodName = ConfigurationManager.getString("client_set_games_method");
        String[] paramTypes = {wrapper.getClass().getCanonicalName()};
        Object[] paramValues = {wrapper};

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
            //reset index
            int oldIndex = serverModel.getPlayer(playerID).getIndex();

            serverModel.getPlayer(playerID).setIndex(oldIndex + commands.size());
            response.setCommands(commands);
            response.setSuccess(true);

        }catch(ServerException e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }

        return response;
    }
}
