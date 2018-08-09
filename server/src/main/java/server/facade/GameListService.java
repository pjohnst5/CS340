package server.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import server.helper.SnapshotHelper;
import server.model.ServerModel;
import server.exception.ServerException;

import shared.command.GenericCommand;
import shared.command.ICommand;
import shared.enumeration.GameState;
import shared.exception.DatabaseException;
import shared.exception.MaxPlayersException;
import shared.exception.PlayerException;
import shared.configuration.ConfigurationManager;
import shared.model.Game;
import shared.model.GameAction;
import shared.model.wrapper.GamesWrapper;
import shared.model.Player;
import shared.model.request.JoinRequest;
import shared.model.response.IResponse;
import shared.model.response.CommandResponse;
import shared.plugin.PluginManager;


//Only ServerFacade should touch these
class GameListService {

    public static IResponse createGame(Game game)
    {
        CommandResponse response = new CommandResponse();
        ServerModel serverModel = ServerModel.getInstance();
        try {
            //adds game to serverModel
            serverModel.addNewGame(game);

            //Add player who's in game to serverModel
            serverModel.addPlayer(game.getPlayers().get(0));
            serverModel.getUser(game.getPlayers().get(0).getUserName()).set_playerId(game.getPlayers().get(0).getPlayerID());

            //Get map of active games
            GamesWrapper games = new GamesWrapper();
            games.setGames(serverModel.getGames());

            //makes command to set games
            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_set_games_method");
            String[] paramTypes = { games.getClass().getCanonicalName() };
            Object[] paramValues = { games };
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

            //Makes game action object
            GameAction action = new GameAction(game.getPlayers().get(0).getDisplayName()," joined the game", game.getGameID());

            //makes command to do same on client
            String className3 = ConfigurationManager.getString("client_facade_name");
            String methodName3 = ConfigurationManager.getString("client_add_game_action_method");
            String[] paramTypes3 = {GameAction.class.getCanonicalName()};
            Object[] paramValues3 = {action};
            ICommand command3 = new GenericCommand(className3, methodName3, paramTypes3, paramValues3, null);
//-----------------------

            serverModel.addCommand(game.getGameID(), command); //set games
            serverModel.addCommand(game.getGameID(), command2); //join game
            int command2Index = serverModel.getCommands(game.getGameID()).size() - 1;
            serverModel.addCommand(game.getGameID(), command3); //game history entry
            int command3Index = serverModel.getCommands(game.getGameID()).size() - 1;

            //gets all the commands for that game for the newly joined player
            List<ICommand> commands = serverModel.getCommands(game.getGameID());

            //updates index of player, whenever you ask for all the commands, it doesn't update the index so you have to do it
            serverModel.getPlayer(game.getPlayers().get(0).getPlayerID()).setIndex(commands.size()-1);

            response.setCommands(commands);
            response.setSuccess(true);


            //------------------------------------Database stuff--------------------------------------------------//
            //Add game in database
            PluginManager.getPlugin().getGameDao().addGame(game);

            //Update User in database (playerID)
            PluginManager.getPlugin().getUserDao().updateUser(serverModel.getUser(game.getPlayers().get(0).getUserName()));

            //Adds commands to that game
            SnapshotHelper.addClientCommandToDatabase(game.getGameID(), command2, command2Index);
            SnapshotHelper.addClientCommandToDatabase(game.getGameID(), command3, command3Index);

        } catch(ServerException e) {
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        } catch (DatabaseException e) {
            System.out.println(e.getMessage());
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
            if (game.get_state() == GameState.READY){
                throw new ServerException("Game is full, can't add new player");
            }

            //Error checking
            for (int i = 0; i < players.size(); i++){
                if(players.get(i).getUserName().equals(jr.getUserName())){
                    throw new ServerException("A player with that username is already in the game");
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

            //it was adding the player to the game, but it was making a new playerID for it when initilzation the server, then the client's player id was invalid.
            if (serverModel.isInitializing()){
                player.setPlayerID(serverModel.getUser(jr.getUserName()).get_playerId());
            } else {
                //Need to add this player's playerID to the User who just joined!
                serverModel.getUser(jr.getUserName()).set_playerId(player.getPlayerID());
            }


            //Player's index is -1 at this point

            //Command for client, player has index -1 (only server needs an updated version of index)
            String className = ConfigurationManager.getString("client_facade_name");
            String methodName = ConfigurationManager.getString("client_join_game_method");
            String[] paramTypes = {Player.class.getCanonicalName()};
            Object[] paramValues = {player};
            //Client will check if the player joining a game is him/herself. If it is, it sets current game
            ICommand command = new GenericCommand(className, methodName, paramTypes, paramValues, null);


            //Makes game action object
            GameAction action = new GameAction(jr.getDisplayName()," joined the game", game.getGameID());

            //adds game action into server model
            serverModel.addGameAction(jr.getGameID(), action);

            //makes command to do same on client
            String className2 = ConfigurationManager.getString("client_facade_name");
            String methodName2 = ConfigurationManager.getString("client_add_game_action_method");
            String[] paramTypes2 = {GameAction.class.getCanonicalName()};
            Object[] paramValues2 = {action};
            ICommand command2 = new GenericCommand(className2, methodName2, paramTypes2, paramValues2, null);

            serverModel.addCommand(jr.getGameID(),command);  //Player joined
            int commandIndex = serverModel.getCommands(jr.getGameID()).size() - 1;
            serverModel.addCommand(jr.getGameID(), command2); //game action of player joined
            int command2Index = serverModel.getCommands(jr.getGameID()).size() - 1;



            //Get list of commands from game the player just joined
            List<ICommand> commands = new ArrayList<>(serverModel.getCommands(player.getGameID()));

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


            //------------------------------------Database stuff--------------------------------------------------//
            //Adds Server command
            if (!serverModel.isInitializing()){
                String[] paramTypesServer = { jr.getClass().getCanonicalName() };
                Object[] paramValuesServer = { jr };
                GenericCommand commandServer = new GenericCommand(
                        ConfigurationManager.get("server_facade_name"),
                        ConfigurationManager.get("server_join_game_method"),
                        paramTypesServer,
                        paramValuesServer,
                        null
                );
                int commandServerIndex = serverModel.getGame(jr.getGameID()).getCommandCountSinceSnapshot();
                SnapshotHelper.addServerCommandToDatabase(jr.getGameID(), commandServer, commandServerIndex);

                //Update User in database (playerID)
                PluginManager.getPlugin().getUserDao().updateUser(serverModel.getUser(jr.getUserName()));

                //adds client commands
                SnapshotHelper.addClientCommandToDatabase(jr.getGameID(), command, commandIndex);
                SnapshotHelper.addClientCommandToDatabase(jr.getGameID(), command2, command2Index);
            }


        } catch(MaxPlayersException | PlayerException | ServerException e){
            response.setSuccess(false);
            response.setErrorMessage(e.getMessage());
        } catch(DatabaseException e) {
            System.out.println(e.getMessage());
        }
        return response;
    }
}
