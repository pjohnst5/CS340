package server.facade;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import server.model.ServerModel;
import server.exception.ServerException;

import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.exception.MaxPlayersException;
import shared.exception.PlayerException;
import shared.configuration.ConfigurationManager;
import shared.model.Game;
import shared.model.GamesWrapper;
import shared.model.Player;
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

            //Add player who's in game to serverModel
            serverModel.addPlayer(game.getPlayers().get(0));

            //Get map of active games
            GamesWrapper games = new GamesWrapper();
            games.setGames(serverModel.getGames());
            //Map<String, Game> activeGames = serverModel.getGames();

            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_set_games_method");
            //String[] paramTypes = { activeGames.getClass().getCanonicalName() };
            String[] paramTypes = { games.getClass().getCanonicalName() };
            Object[] paramValues = { games };
            //Object[] paramValues = { activeGames };

            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);
//-----------------------
            //New command to join the game the player just created.
            String className2 = ConfigurationManager.getString("client_facade_name");
            String methodName2 = ConfigurationManager.getString("client_join_game_method");
            String[] paramTypes2 = {Player.class.getCanonicalName()};
            Object[] paramValues2 = {game.getPlayers().get(0)}; //gets first player

            //Client will check if the player joining a game is him/herself. If it is, it sets current game
            ICommand command2 = new GenericCommand(className2, methodName2, paramTypes2, paramValues2, null);
//--------------------------
            response.addCommand(command);
            response.addCommand(command2);
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

            //If game is full, can't add more people
            if (game.getReady()){
                throw new ServerException("Game is full, can't add new player");
            }

            //Error checking
            for (int i = 0; i < players.size(); i++){
                if(players.get(i).getUserName().equals(jr.getUserName())){
                    throw new ServerException("A player with that username is already in the game");
//                    response.setSuccess(true);
//                    //Command sent back sets curent game.
//                    String className = ConfigurationManager.getString("client_facade_name");
//                    String methodName = ConfigurationManager.getString("client_join_game_method");
//                    String[] paramTypes = {Player.class.getCanonicalName()};
//                    Object[] paramValues = {players.get(i)};
//
//                    //Client will check if the player joining a game is him/herself. If it is, it sets current game
//                    ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);
//
//                    response.addCommand(command);
                }
                if (players.get(i).getColor().equals(jr.getColor())){
                    throw new ServerException("A player with that color already exists in the game");
                }

                if(players.get(i).getDisplayName().equals(jr.getDisplayName())){
                    throw new ServerException("A player with that display name already exists in the game");
                }

            }

            //By here, no player in the game had same info as new player
            //Good to make new player
            Player player = new Player(jr.getUserName(),jr.getDisplayName(),jr.getColor(),game.getGameID(), UUID.randomUUID().toString());

            //Player's index is -1 at this point

            //Command for client, player has index -1 (only server needs an updated version of index)
            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_join_game_method");
            String[] paramTypes = {Player.class.getCanonicalName()};
            Object[] paramValues = {player};

            //Client will check if the player joining a game is him/herself. If it is, it sets current game
            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);

            //Add "player joined command" in the command manager mapped to the gameID
            serverModel.addCommand(jr.getGameID(),command);

            //Get list of commands from game the player just joined
            List<ICommand> commands = serverModel.getCommands(player.getGameID());

            //Sets player's index to size of commands for that game minus 1 because it's an index.
            player.setIndex(commands.size() - 1);

            //add player to game (this player being added to server model is same as client gets except it has an updated index)
            game.addPlayer(player);

            //add game back in to server model
            serverModel.replaceExistingGame(game);

            //add player to model
            serverModel.addPlayer(player);

            //add all the commands for the game the user just joined.
            response.setCommands(commands);
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
