package server.ServerFacade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import server.ServerModel.ServerModel;
import server.exception.ServerException;

import shared.Command.GenericCommand;
import shared.Command.ICommand;
import shared.CustomExceptions.MaxPlayersException;
import shared.CustomExceptions.PlayerException;
import shared.configuration.ConfigurationManager;
import shared.model.Game;
import shared.model.Player;
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
            serverModel.addNewGame(game);

            //Make map of active games
            Map<String, Game> activeGames = serverModel.getGames();

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

    public static IResponse joinGame(JoinRequest jr)
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();

        try {
            Game game = serverModel.getGame(jr.getGameID());
            List<Player> players = game.getPlayers();

            //Error checking
            for (int i = 0; i < players.size(); i++){
                if (players.get(i).getColor().equals(jr.getColor())){
                    throw new ServerException("A player with that color already exists in the game");
                }

                if(players.get(i).getDisplayName().equals(jr.getDisplayName())){
                    throw new ServerException("A player with that display name already exists in the game");
                }

                if(players.get(i).getUserName().equals(jr.getUserName())){
                    throw new ServerException("A player with that username is already in the game");
                }
            }

            //By here, no player in the game had same info as new player
            //Good to make new player
            Player player = new Player(jr.getUserName(),jr.getDisplayName(),jr.getColor(),game.getGameID(), UUID.randomUUID().toString());

            int sizeOfCommands = serverModel.getCommands(player.getGameID(), -2).size();
            int oldIndex = player.getIndex();

            //Accounts for him getting his own command
            player.setIndex(oldIndex + sizeOfCommands + 1);

            //Command for client
            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_start_game_method");
            String[] paramTypes = {Player.class.getCanonicalName()};
            Object[] paramValues = {player};

            //Client will check if the player joining a game is him/herself. If it is, it sets current game
            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);

            //Add "player joined command" in the command manager mapped to the gameID
            serverModel.addCommand(jr.getGameID(),command);

            //Get list of commands from game the player just joined
            List<ICommand> commands = serverModel.getCommands(player.getGameID(), -2);

            //add player to game
            game.addPlayer(player);

            //add game back in to server model
            serverModel.replaceExistingGame(game);

            //add player into model
            serverModel.addPlayer(player);

            //add all the commands for the game the user just joined.
            for (int i = 0; i < commands.size(); i++) {
                response.addCommand(command);
            }
            response.setSuccess(true);

        } catch(ServerException e){
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        } catch(PlayerException e){
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        } catch(MaxPlayersException e){
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        }
        return response;
    }
}
