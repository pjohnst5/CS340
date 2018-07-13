package server.ServerFacade;


import java.util.List;
import java.util.Map;

import server.ServerModel.ServerModel;
import server.exception.ServerException;
import shared.Command.GenericCommand;
import shared.Command.ICommand;
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

    public static IResponse getCommandList(String gameID, int index)
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        try{
            List<ICommand> commands = serverModel.getCommands(gameID, index);

            for (int i = 0; i < commands.size(); i++) {
                response.addCommand(commands.get(i));
            }
            response.setSuccess(true);

        }catch(ServerException e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }

        return response;
    }
}
