package server.facade;

import java.util.List;

import javax.print.DocFlavor;

import server.model.ServerModel;
import server.exception.ServerException;
import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.enumeration.GameState;
import shared.exception.InvalidGameException;
import shared.configuration.ConfigurationManager;
import shared.model.Game;
import shared.model.Message;
import shared.model.Player;
import shared.model.response.CommandResponse;
import shared.model.response.IResponse;

//Only ServerFacade should touch these
class GameLobbyService {

    public static IResponse startGame(String gameID, String playerID)
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        try {
            Game game = serverModel.getGame(gameID);

            switch (game.get_state()) {
                case NOT_READY:
                    throw new ServerException("Game is not full, can't start without max players");
                case STARTED:
                    throw new ServerException("Game has already started, can't start it twice.");
                case FINISHED:
                    throw new ServerException("Error game has already finished");
            }

            //This is really all start game does
            game.start();

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

        } catch(Exception e){
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

    public static IResponse sendMessage(Message message)
    {
        //TODO:Implement me
        return null;
    }

    public static IResponse leaveGame(String gameId, String playerId) {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();
        try {
            Game game = serverModel.getGame(gameId);
            if (game.get_state() == GameState.STARTED) {
                throw new ServerException("Can't leave game; game already started!");
            }
            Player player = game.getPlayer(playerId);
            game.removePlayer(playerId);
            serverModel.removePlayer(playerId);

            //replace existing game in server
            serverModel.replaceExistingGame(game);

            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_leave_game_method");
            String[] paramTypes = {Player.class.getCanonicalName()};
            Object[] paramValues = {player};

            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);

            //add command to list of commands for game
            serverModel.addCommand(game.getGameID(), command);

            //gets all commands for this game and player
            List<ICommand> commands = serverModel.getCommands(gameId, playerId);

            //sets all the commands of this response to be the list
            response.setCommands(commands);
            response.setSuccess(true);

        } catch(Exception e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }

}

