package server.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.exception.ServerException;
import shared.command.ICommand;

public class CommandManager {

    private static CommandManager _instance;
    private Map<String, List<ICommand>> _commandList;

    public static CommandManager getInstance()
    {
        if (_instance == null) {
            _instance = new CommandManager();
        }

        return _instance;
    }

    private CommandManager()
    {
        _commandList = new HashMap<>();
    }



    public List<ICommand> getCommands(String gameID, int index) throws ServerException
    {
        if (!_commandList.containsKey(gameID)){
            throw new ServerException("No game in command manager with that gameID getCommands");
        }

        if (_commandList.get(gameID).size() == (index + 1)) { // if (index == size() - 1
            throw new ServerException("No new commands to get");
        }

        if (_commandList.get(gameID).size() < index) {
            throw new ServerException("Too big of an index for command manager");
        }

        List<ICommand> commands = _commandList.get(gameID);
        int size = commands.size();

        return commands.subList(index+1,size);
    }

    public List<ICommand> getCommands (String gameID) throws ServerException
    {
        if (!_commandList.containsKey(gameID)){
            throw new ServerException("No game in command manager with that gameID getCommands");
        }
        return _commandList.get(gameID);
    }



    public void addCommand(String gameID, ICommand command) throws ServerException
    {
        if (!_commandList.containsKey(gameID)){
            _commandList.put(gameID, new ArrayList<ICommand>());
        }
        _commandList.get(gameID).add(command);
    }

}
