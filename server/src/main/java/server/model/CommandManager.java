package server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.exception.ServerException;
import shared.command.ICommand;
import shared.exception.DatabaseException;
import shared.model.Game;
import shared.model.Player;
import shared.plugin.ICommandDao;
import shared.plugin.IPersistenceProvider;
import shared.plugin.PluginManager;

/**
 * CommandManager provides access to lists of commands for each game
 *
 * @invariant getInstance() != null
 * @invariant _commandList != null
 */
public class CommandManager {

    private static CommandManager _instance;
    private static Map<String, List<ICommand>> _commandList = new HashMap<>();
    private static boolean _hasBeenInitialized;

    /**
     * Returns the CommandManager instance, this class is a singleton
     *
     * @return      the ServerFacade instance
     */
    public static CommandManager getInstance()
    {
        if (_instance == null) {
            _instance = new CommandManager();
        }

        return _instance;
    }

    public static void init()
    {
        try {
            IPersistenceProvider _plugin = PluginManager.getPlugin();
            ICommandDao commandDao = _plugin.getCommandDao();
            List<Game> games = _plugin.getGameDao().getGames();
            for(int i = 0; i < games.size(); i++) {
                String currGameId = games.get(i).getGameID();
                List<ICommand> clientCommands = commandDao.getClientCommands(currGameId);
                _commandList.put(currGameId, clientCommands);

                //resets games commandCountSinceSnapshot
                ServerModel.getInstance().getGame(currGameId).resetCommandCountSinceSnapshot();

                List<ICommand> serverCommands = commandDao.getServerCommands(currGameId);
                int indexOfCommandsToExecute = _plugin.getGameDao().getIndexOfCompletedCommands(currGameId);
                for(int j = indexOfCommandsToExecute; j < serverCommands.size(); j++){
                    serverCommands.get(j).execute();
                    ServerModel.getInstance().getGame(currGameId).incrementCommandCountSinceSnapshot();
                }

                //Sets all players command index in this game to be the size of the list - 1 (otherwise it executes client commands twice)
                List<Player> players = ServerModel.getInstance().getGame(currGameId).getPlayers();
                for (int k = 0; k < players.size(); k++) {
                    players.get(k).setIndex(clientCommands.size()-1);
                }
            }
        } catch (DatabaseException | ServerException e) {
            e.printStackTrace();
        }
        _hasBeenInitialized = true;
    }

    /**
     * Constructs a new CommandManage
     *
     * @return      A CommandManager object
     */
    private CommandManager()
    {
        _hasBeenInitialized = false;
    }



    /**
     * Returns the list of commands after the index given of the given game
     *
     * @param  gameID  the gameID
     * @param  index the index to which commands have already been successfully taken (it will get index + 1 to the end)
     *
     * @pre gameID != null
     * @pre _commandList.contains(gameID) == true
     * @pre index >= -1
     *
     * @return      the List of Commands after the given index
     *
     * @exception   if gameID isn't in map, throws exception
     * @exception   if index is the size of the list + 1, throws exception
     * @exception   if index is greater than or equal to the size of the list, throws exception
     */
    public List<ICommand> getCommands(String gameID, int index) throws ServerException
    {
        if (!_commandList.containsKey(gameID)){
            throw new ServerException("Game does not exist in Command Manager");
        }

        if (_commandList.get(gameID).size() == (index + 1)) { // if (index == size() - 1
            throw new ServerException("Commands up-to-date");
        }

        if (_commandList.get(gameID).size() < index) {
            throw new ServerException("Too big of an index for command manager");
        }

        List<ICommand> commands = _commandList.get(gameID);
        int size = commands.size();

        return commands.subList(index+1,size);
    }


    /**
     * Returns the entire list of commands for the given game
     *
     * @param  gameID  the gameID
     *
     * @pre gameID != null
     * @pre _commandList.contains(gameID) == true
     *
     * @return      the List of Commands for that game
     *
     * @exception   if gameID isn't in map, throws exception
     */
    public List<ICommand> getCommands (String gameID) throws ServerException
    {
        if (!_commandList.containsKey(gameID)){
            throw new ServerException("No game in command manager with that gameID getCommands");
        }
        return _commandList.get(gameID);
    }


    /**
     * Adds the given command to the list of commands for that game
     *
     * @param  gameID  the gameID
     * @param  command the command to add
     *
     * @pre gameID != null
     * @pre _commandList.contains(gameID) == true
     * @pre index >= -1
     *
     * @post new _currentGames.get(gameID).size() = old _currentGames.get(gameID).size() + 1
     *
     * @exception   if gameID isn't in map, throws exception
     */
    public void addCommand(String gameID, ICommand command) throws ServerException
    {
        if (_hasBeenInitialized == false){
            return;
        }
        if (!_commandList.containsKey(gameID)){
            throw new ServerException("Game does not exist in Command Manager");
        }
        _commandList.get(gameID).add(command);
    }

    /**
     * Adds the gameID to the map of _currentGames
     *
     * @param  gameID  the gameID of a new game
     *
     * @pre gameID != null
     *
     * @post new _currentGames.size() = old _currentGames.size() + 1
     *
     */
    public void addGame(String gameID)
    {
        if (!_commandList.containsKey(gameID)){
            _commandList.put(gameID, new ArrayList<ICommand>());
        }
    }

}
