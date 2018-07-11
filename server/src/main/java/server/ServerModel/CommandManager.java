package server.ServerModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.exception.ServerException;
import shared.Command.ICommand;

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
        return null;
    }

    public void addCommand(String gameID, ICommand command) throws ServerException
    {
        return;
    }







    public static void TestCommand(String a, int b, int c){
        System.out.println("TEST COMMAND WORKED!!");
    }

    public static void GameListPoll(){
        // TODO: Implement this command
        System.out.println("GameList Polled");
    }

    public static void GameLobbyPoll(){
        // TODO: Implement this command
        System.out.println("GameLobby Polled");
    }
}
