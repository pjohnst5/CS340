package server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import server.exception.ServerException;
import shared.command.ICommand;
import shared.exception.InvalidGameException;
import shared.exception.InvalidUserException;
import shared.model.Game;
import shared.model.Player;
import shared.model.User;

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

    private Map<String, User> _users;
    private Map<String, Player> _players; //playerid's to Player
    private Map<String, Game> _games;  //gameid's to games
    private List<UUID> _uuids;
    private CommandManager _manager;


    //Login/Register
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


    //GameList
    public void addNewGame(Game game) throws ServerException {
        //set gameID
        String gameID = UUID.randomUUID().toString();
        try {
            game.setGameID(gameID);

            //Put game in map
            _games.put(game.getGameID(), game);

        } catch(InvalidGameException e) {
            throw new ServerException(e.getMessage());
        }
    }

    public void replaceExistingGame(Game game) throws ServerException {
        _games.put(game.getGameID(), game);
    }

    public void addPlayer(Player player) throws ServerException {
        throw new ServerException("not valid");
    }




    //Removers
    public void removePlayer(Player player) throws ServerException {
        return;
    }

    public void removeGame(String gameID) throws ServerException {
        return;
    }



    //Getters
    public Game getGame(String gameID) throws ServerException {
            Game g = _games.get(gameID);

            if(g == null){
                throw new ServerException("Game does not exist in server");
            }

        return _games.get(gameID);
    }

    public Player getPlayer(String username) throws ServerException
    {
        return null;
    }

    public User getUser(String username) throws ServerException
    {
        return null;
    }



    //Commands
    public void addCommand(String gameID, ICommand command) throws ServerException {
        if (!_games.containsKey(gameID)){
            throw new ServerException("Game does not exist: cannot add command to game that doesn't exist");
        }
        _manager.addCommand(gameID,command);
    }

    public List<ICommand> getCommands(String gameID, int index) throws ServerException {
        return _manager.getCommands(gameID, index);
    }

    public List<ICommand> getCommands(String gameID) throws ServerException {
        return _manager.getCommands(gameID);
    }

    public Map<String, Game> getGames() {
        return _games;
    }










}
