package server.model;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import server.exception.ServerException;
import shared.command.ICommand;
import shared.exception.InvalidUserException;
import shared.model.Game;
import shared.model.GameAction;
import shared.model.GameMap;
import shared.model.Message;
import shared.model.Player;
import shared.model.User;
import shared.model.decks.DestDeck;
import shared.model.decks.TrainDeck;

public class ServerModel {

    private static ServerModel _instance;

    public static ServerModel getInstance() {
        if (_instance == null) {
            _instance = new ServerModel();
        }

        return _instance;
    }

    private ServerModel() {
        _users = new HashMap<>();
        _players = new HashMap<>();
        _games = new HashMap<>();
        _uuids = new ArrayList<>();
        _manager = CommandManager.getInstance();
    }

    private Map<String, User> _users; //usernames to Users
    private Map<String, Player> _players; //playerid's to Player
    private Map<String, Game> _games;  //gameid's to games
    private List<UUID> _uuids;
    private CommandManager _manager;


    //------------------Login/Register-------------------------------------------------------//
    public User authenticate(String username, String password) throws ServerException {

        //Error checking
        if (!_users.containsKey(username)){
            throw new ServerException("User is not registered");
        }

        if (!_users.get(username).getPassword().equals(password)) {
            throw new ServerException("Wrong password");
        }


        try {
            //Get user from map
            User user = _users.get(username);

            //Make new UUID
            UUID uuid = UUID.randomUUID();

            //Insert UUID into server model
            _uuids.add(uuid);

            //Set new uuid for User
            user.setUUID(uuid);

            //Insert new User (new because new UUID) into the map
            _users.put(user.getUserName(), user);

            return user;

        } catch(InvalidUserException e) {
            throw new ServerException(e.getMessage());
        }
    }

    public User register(String username, String password) throws ServerException {

        //Error checking
        if (_users.containsKey(username)){
            throw new ServerException("User is already registered");
        }

        try {
            //Make new UUID
            UUID uuid = UUID.randomUUID();

            //Insert UUID into server model
            _uuids.add(uuid);

            //Make new user with new UUID
            User user = new User(username, password);
            user.setUUID(uuid);

            //Add user into map
            _users.put(user.getUserName(), user);

            return user;

        } catch(InvalidUserException e) {
            throw new ServerException(e.getMessage());
        }
    }




    //---------------------Adders--------------------------------------------------------//
    public void addNewGame(Game game) throws ServerException {
        if (_games.containsKey(game.getGameID())){
            throw new ServerException("Game already exists in Server");
        }
        _games.put(game.getGameID(), game);
        _manager.addGame(game.getGameID());
    }

    public void addPlayer(Player player) throws ServerException {
        _players.put(player.getPlayerID(), player);
    }

    public void addMessage(String gameID, Message message) throws ServerException {
        if (!_games.containsKey(gameID)){
            throw new ServerException("Could not find game to send message in");
        }

        _games.get(gameID).addMessage(message);
    }

    public void addGameAction(String gameID, GameAction action) throws ServerException{
        if (!_games.containsKey(gameID)){
            throw new ServerException("Could not find game to put action in");
        }
        _games.get(gameID).addGameAction(action);
    }



    //-------------------------------Setters------------------------------------------------------------//
    public void setGameMap(String gameID, GameMap map) throws ServerException {
        if (!_games.containsKey(gameID)){
            throw new ServerException("The gameID was not found when switching out gameMap on server");
        }
        _games.get(gameID).setMap(map);
    }

    public void setTrainDeck(String gameID, TrainDeck deck) throws ServerException {
        if (!_games.containsKey(gameID)){
            throw new ServerException("The gameID was not found when switching out train deck on server");
        }
        _games.get(gameID).setTrainDeck(deck);
    }

    public void setDestDeck(String gameID, DestDeck deck) throws ServerException {
        if (!_games.containsKey(gameID)){
            throw new ServerException("The gameID was not found when switching out dest deck on server");
        }
        _games.get(gameID).setDestDeck(deck);
    }






    //---------------------------------Removers--------------------------------------------//
    public void replaceExistingGame(Game game) throws ServerException {
        _games.put(game.getGameID(), game);
    }

    public void removePlayer(String playerID){
        _players.remove(playerID);
    }






    //------------------------------------Getters-------------------------------------------------------------//
    public Game getGame(String gameID) throws ServerException {
            Game g = _games.get(gameID);

            if(g == null){
                throw new ServerException("Game does not exist in server");
            }

        return _games.get(gameID);
    }

    public Player getPlayer(String playerID) throws ServerException
    {
        if (!_players.containsKey(playerID)){
            throw new ServerException("Player is not in server");
        }
        return _players.get(playerID);
    }

    public User getUser(String username) throws ServerException
    {
        if (!_users.containsKey(username)){
            throw new ServerException("User is not in server");
        }
        return _users.get(username);
    }

    public Map<String, Game> getGames() {
        return _games;
    }





    //------------Commands-------------------------------------------------------------------------------//
    public void addCommand(String gameID, ICommand command) throws ServerException {
        if (!_games.containsKey(gameID)){
            throw new ServerException("Game does not exist: cannot add command to game that doesn't exist");
        }
        _manager.addCommand(gameID,command);
    }

    public List<ICommand> getCommands(String gameID, String playerID) throws ServerException {
        //gets the players index
        int index = _players.get(playerID).getIndex();

        //Gets the list of new commands based on index
        List<ICommand> commands = _manager.getCommands(gameID, index);

        //gets size of list of new commands
        int size = commands.size();

        //updates index of the player
        _players.get(playerID).setIndex(index + size);

        //return list of new commands
        return commands;
    }

    //WHOEVER CALLED ME: update the player index!
    public List<ICommand> getCommands(String gameID) throws ServerException {
        return _manager.getCommands(gameID);
    }
}
