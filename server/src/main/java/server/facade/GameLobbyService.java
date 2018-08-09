package server.facade;

import java.util.List;

import server.helper.SnapshotHelper;
import server.model.ServerModel;
import server.exception.ServerException;
import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.enumeration.GameState;
import shared.configuration.ConfigurationManager;
import shared.exception.DatabaseException;
import shared.exception.InvalidGameException;
import shared.model.Game;
import shared.model.GameAction;
import shared.model.Message;
import shared.model.Player;
import shared.model.request.LeaveGameRequest;
import shared.model.request.MessageRequest;
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
                case SETUP:
                    throw new ServerException("Game has already started, can't start it twice.");
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

            //Makes game action object
            GameAction action = new GameAction(serverModel.getPlayer(playerID).getDisplayName(), " started the game", gameID);

            //adds game action into server model
            serverModel.addGameAction(gameID, action);

            //makes command to do same on client
            String className2 = ConfigurationManager.getString("client_facade_name");
            String methodName2 = ConfigurationManager.getString("client_add_game_action_method");
            String[] paramTypes2 = {GameAction.class.getCanonicalName()};
            Object[] paramValues2 = {action};
            ICommand command2 = new GenericCommand(className2, methodName2, paramTypes2, paramValues2, null);

            //adds commands to list of commands for game
            serverModel.addCommand(game.getGameID(), command);
            int commandIndex = serverModel.getCommands(game.getGameID()).size() - 1;
            serverModel.addCommand(game.getGameID(), command2);
            int command2Index = serverModel.getCommands(game.getGameID()).size() - 1;

            //gets all commands for this game and player
            List<ICommand> commands = serverModel.getCommands(gameID, playerID);

            //sets all the commands of this response to be the list
            response.setCommands(commands);
            response.setSuccess(true);


            //------------------------------------Database stuff--------------------------------------------------//
            String[] paramTypesServer = {String.class.getCanonicalName(), String.class.getCanonicalName()};
            Object[] paramValuesServer = {gameID, playerID};

            GenericCommand serverCommand = new GenericCommand(
                    ConfigurationManager.getString("server_facade_name"),
                    ConfigurationManager.getString("server_start_game_method"),
                    paramTypesServer,
                    paramValuesServer,
                    null);
            int commandServerIndex = serverModel.getGame(gameID).getCommandCountSinceSnapshot();
            SnapshotHelper.addServerCommandToDatabase(gameID, serverCommand, commandServerIndex);

            SnapshotHelper.addClientCommandToDatabase(gameID, command, commandIndex);
            SnapshotHelper.addClientCommandToDatabase(gameID, command2, command2Index);

        } catch(ServerException | InvalidGameException e){
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        } catch(DatabaseException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }


    public static IResponse sendMessage(MessageRequest request)
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        try{
            //checks to see if message gameID is same as messageRequest gameID
            if (!request.get_gameID().equals(request.get_message().getGameID()))
            {
                throw new ServerException("for some reason, the message gameID and messageRequest gameID don't match");
            }

            //add message to game
            serverModel.addMessage(request.get_gameID(), request.get_message());
            request.get_message().setTimeStamp();

            //make command to do the same for clients
            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_send_message_method");
            String[] paramTypes = {Message.class.getCanonicalName()};
            Object[] paramValues = {request.get_message()};
            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);

            //add newly made command to list
            serverModel.addCommand(request.get_gameID(), command);
            int commandIndex = serverModel.getCommands(request.get_gameID()).size() - 1;


            //gets list of new commands for player and sets them as the response commands
            response.setCommands(serverModel.getCommands(request.get_gameID(),request.get_playerID()));
            response.setSuccess(true);


            //------------------------------------Database stuff--------------------------------------------------//
            String[] paramTypesServer = {Message.class.getCanonicalName()};
            Object[] paramValuesServer = {request};

            GenericCommand serverCommand = new GenericCommand(
                    ConfigurationManager.getString("server_facade_name"),
                    ConfigurationManager.getString("server_send_message_method"),
                    paramTypesServer,
                    paramValuesServer,
                    null);
            int commandServerIndex = serverModel.getGame(request.get_gameID()).getCommandCountSinceSnapshot();
            SnapshotHelper.addServerCommandToDatabase(request.get_gameID(), serverCommand, commandServerIndex);

            SnapshotHelper.addClientCommandToDatabase(request.get_gameID(), command, commandIndex);

        } catch (ServerException e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        } catch (DatabaseException e) {
            System.out.println(e.getMessage());
        }

        return response;
    }


    public static IResponse leaveGame(LeaveGameRequest request) {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();
        String gameId = request.get_gameID();
        String playerId = request.get_playerID();

        try {
            Game game = serverModel.getGame(gameId);
            if (game.get_state() == GameState.STARTED) {
                throw new ServerException("Can't leave game; game already started!");
            }

            //removes player from server model
            Player player = game.getPlayer(playerId);
            game.removePlayer(playerId);
            serverModel.removePlayer(playerId);

            //replace existing game in server
            serverModel.replaceExistingGame(game);

            //makes command to remove player from client's version of game
            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_leave_game_method");
            String[] paramTypes = {Player.class.getCanonicalName()};
            Object[] paramValues = {player};
            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);

            //Makes game action object
            GameAction action = new GameAction(serverModel.getPlayer(request.get_playerID()).getDisplayName(), " left the game", request.get_gameID());

            //adds game action into server model
            serverModel.addGameAction(request.get_gameID(), action);

            //makes command to do same on client
            String className2 = ConfigurationManager.getString("client_facade_name");
            String methodName2 = ConfigurationManager.getString("client_add_game_action_method");
            String[] paramTypes2 = {GameAction.class.getCanonicalName()};
            Object[] paramValues2 = {action};
            ICommand command2 = new GenericCommand(className2, methodName2, paramTypes2, paramValues2, null);


            //add commands to list of commands for game
            serverModel.addCommand(game.getGameID(), command);
            int commandIndex = serverModel.getCommands(request.get_gameID()).size() - 1;
            serverModel.addCommand(game.getGameID(), command2);
            int command2Index = serverModel.getCommands(request.get_gameID()).size() - 1;


            //gets all commands for this game and player
            List<ICommand> commands = serverModel.getCommands(gameId, playerId);

            //sets all the commands of this response to be the list
            response.setCommands(commands);
            response.setSuccess(true);


            //------------------------------------Database stuff--------------------------------------------------//
            String[] paramTypesServer = {String.class.getCanonicalName(), String.class.getCanonicalName()};
            Object[] paramValuesServer = {request.get_gameID(), playerId};

            GenericCommand serverCommand = new GenericCommand(
                    ConfigurationManager.getString("server_facade_name"),
                    ConfigurationManager.getString("server_leave_game_method"),
                    paramTypesServer,
                    paramValuesServer,
                    null);
            int commandServerIndex = serverModel.getGame(request.get_gameID()).getCommandCountSinceSnapshot();
            SnapshotHelper.addServerCommandToDatabase(request.get_gameID(), serverCommand, commandServerIndex);

            SnapshotHelper.addClientCommandToDatabase(request.get_gameID(), command, commandIndex);
            SnapshotHelper.addClientCommandToDatabase(request.get_gameID(), command2, command2Index);

        } catch(ServerException | InvalidGameException e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        } catch (DatabaseException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

}

