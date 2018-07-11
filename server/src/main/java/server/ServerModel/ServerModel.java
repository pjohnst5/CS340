package server.ServerModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import server.exception.ServerException;
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
    public void addGame(Game game) throws ServerException {
        return;
    }

    public UUID addUser(User user) throws ServerException {
        return UUID.fromString("hello");
    }

    public void addPlayer(Player player, String gameID) throws ServerException {
        throw new ServerException("not valid");
    }

    public UUID authenticate(String username, String password) throws ServerException {
        return UUID.fromString("hello");
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
