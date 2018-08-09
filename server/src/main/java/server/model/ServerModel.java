package server.model;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import server.exception.ServerException;
import shared.command.ICommand;
import shared.exception.DatabaseException;
import shared.exception.DeckException;
import shared.exception.InvalidGameException;
import shared.exception.InvalidUserException;
import shared.model.Game;
import shared.model.GameAction;
import shared.model.Message;
import shared.model.Player;
import shared.model.User;
import shared.model.decks.DestCard;

import shared.plugin.IGameDao;
import shared.plugin.IPersistenceProvider;
import shared.plugin.IUserDao;
import shared.plugin.PluginManager;

public class ServerModel {

    private static ServerModel _instance;

    public static ServerModel getInstance() {
        if (_instance == null) {
            _instance = new ServerModel();
        }

        return _instance;
    }

    private ServerModel() {
        IPersistenceProvider _plugin = PluginManager.getPlugin();
        IUserDao _userDao = _plugin.getUserDao();
        IGameDao _gameDao = _plugin.getGameDao();

        try {
            List<User> users = _userDao.getUsers();
            for (int i = 0; i < users.size(); i++){
                User user = users.get(i);
                _users.put(user.getUserName(), user);
            }
            for (int i = 0; i < _gameDao.getGames().size(); i++){
                addNewGame(_gameDao.getGames().get(i));
            }
            _manager = CommandManager.getInstance();
            _manager.init();
        } catch (DatabaseException | ServerException e) {
            e.printStackTrace();
        }
    }

    private Map<String, User> _users = new HashMap<>(); //usernames to Users
    private Map<String, Player> _players = new HashMap<>(); //playerid's to Player
    private Map<String, Game> _games = new HashMap<>();  //gameid's to games
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


    public void addDestCardToDeck(String gameID, DestCard card) throws ServerException {

        try {
            if (!_games.containsKey(gameID)) {
                throw new ServerException("The gameID was not found when adding discard back to deck");
            }
            _games.get(gameID).getDestDeck().putDestCardBack(card);
        } catch (DeckException e){
            throw new ServerException("Could not add card back into deck", e);
        }
    }


    //-------------------------------Setters------------------------------------------------------------//
    public void updatePlayer(String gameID, Player player) throws ServerException {
        if (!_games.containsKey(gameID)){
            throw new ServerException("The gameID was not found when updating player");
        }

        if (!_players.containsKey(player.getPlayerID())) {
            throw new ServerException("The PlayerID was not found when updating player");
        }

        //make sure to keep index up to date
        int index = _players.get(player.getPlayerID()).getIndex();
        player.setIndex(index);

        //replace player in server model
        _players.put(player.getPlayerID(), player);

        try {
            //updates player in individual game
            _games.get(gameID).updatePlayer(player);
        } catch (InvalidGameException e){
            throw new ServerException(e.getMessage());
        }
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
