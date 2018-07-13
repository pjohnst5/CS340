package server.facade;

import java.util.List;

import server.model.ServerModel;
import server.exception.ServerException;
import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.exception.InvalidGameException;
import shared.configuration.ConfigurationManager;
import shared.model.Game;
import shared.model.Message;
import shared.model.response.CommandResponse;
import shared.model.response.IResponse;

//Only ServerFacade should touch these
class GameLobbyFacade {

    public static IResponse startGame(String gameID, String playerID)
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        try {
            Game game = serverModel.getGame(gameID);
            if (game.getStarted()){
                throw new ServerException("Game has already started, can't start it twice.");
            }

            if (!game.getReady()) {
                throw new ServerException("Game is not full, can't start without max players");
            }

            //This is really all start game does
            game.setStarted(true);

            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_start_game_method");
            String[] paramTypes = {String.class.getCanonicalName()};
            Object[] paramValues = {gameID};

            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);

            //add command to list of commands for game
            serverModel.addCommand(game.getGameID(), command);

            //gets all commands for this game and player
            List<ICommand> commands = serverModel.getCommands(gameID, playerID);

            //sets all the commands of this response to be the list
            response.setCommands(commands);
            response.setSuccess(true);

        } catch(ServerException e){
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        } catch(InvalidGameException e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public static IResponse sendMessage(Message message)
    {
        return null;
    }

    //    public static IResponse leaveGame(String username, String gameID)
//    {
//        return null;
//    }

}

