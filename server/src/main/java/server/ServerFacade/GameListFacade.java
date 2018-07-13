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

            game.addPlayer(player);

            //add game back in to server model
            serverModel.replaceExistingGame(game);

            //add player to map
            serverModel.addPlayer(player);

            //Command for client
            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_add_player_method");
            String[] paramTypes = {Player.class.getCanonicalName()};
            Object[] paramValues = {player};

            //Client will check if the player joining a game is him/herself. If it is, it sets current game
            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);

            //Add "player joined command" in the command manager mapped to the gameID
            serverModel.addCommand(jr.getGameID(),command);

            //Player who just joined will get a command back that adds him to the game, the poller is responsible for getting the rest of the commands that may be associated with the game
            response.addCommand(command);
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
