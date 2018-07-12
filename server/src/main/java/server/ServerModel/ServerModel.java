package server.ServerModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import server.exception.ServerException;
import shared.CustomExceptions.InvalidUserException;
import shared.Game;
import shared.Player;
import shared.User;

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
    private Map<String, Player> _players;
    private Map<String, Game> _games;
    private List<UUID> _uuids;
    private CommandManager _manager;


    //Adders
    public User authenticate(String username, String password) throws ServerException {

        //Error checking
        /*
        if (!_users.containsKey(username)){
            throw new ServerException("User is not registered");
        }

        if (!_users.get(username).getPassword().equals(password)) {
            throw new ServerException("Wrong password");
        }
        */

        try {
            //Get user from map
            //User user = _users.get(username);

            User user = new User(username,password);

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

    public void addGame(Game game) throws ServerException {
        return;
    }

    public void addPlayer(Player player, String gameID) throws ServerException {
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
        return null;
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
    public void addCommand(String gameID) throws ServerException {
        return;
    }

    public void getCommands(String gameID) throws ServerException {
        return;
    }

    public List<Game> getGames() {
        return new ArrayList<>(_games.values());
    }










}
