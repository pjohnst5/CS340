package server.ServerFacade;

import server.ServerModel.ServerModel;
import server.exception.ServerException;
import shared.Command.GenericCommand;
import shared.Command.ICommand;
import shared.CustomExceptions.InvalidGameException;
import shared.configuration.ConfigurationManager;
import shared.model.Game;
import shared.model.Message;
import shared.model.response.CommandResponse;
import shared.model.response.IResponse;

//Only ServerFacade should touch these
class GameLobbyFacade {

    public static IResponse startGame(String gameID)
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        try {
            Game game = serverModel.getGame(gameID);
            if (game.getStarted()){
                throw new ServerException("Game has already started, can't start it twice.");
            }
            game.setStarted(true);

            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_start_game_method");
            String[] paramTypes = {String.class.getCanonicalName()};
            Object[] paramValues = {gameID};

            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);

            response.addCommand(command);
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

