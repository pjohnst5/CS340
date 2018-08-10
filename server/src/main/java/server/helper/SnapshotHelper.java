package server.helper;

import server.exception.ServerException;
import server.model.ServerModel;
import shared.command.ICommand;
import shared.configuration.ConfigurationManager;
import shared.exception.DatabaseException;
import shared.model.Game;
import shared.plugin.PluginManager;

public class SnapshotHelper {

    public static void addServerCommandToDatabase(String gameID, ICommand command, int indexOfCommand) throws ServerException, DatabaseException {
        ServerModel serverModel = ServerModel.getInstance();
        Game game = serverModel.getGame(gameID);

        //Get the Game's index then add this index to it
        int gameSnapshotIndex = PluginManager.getPlugin().getGameDao().getIndexOfCompletedCommands(gameID);
        indexOfCommand = gameSnapshotIndex + indexOfCommand;

        //Add server command into database
        PluginManager.getPlugin().getCommandDao().storeServerCommand(gameID, indexOfCommand, command);

        //Increment CommandCountSinceSnapshot for this game
        game.incrementCommandCountSinceSnapshot();

        //checks to see if the number of commands since the last snapshot == N
        if (game.getCommandCountSinceSnapshot() == ConfigurationManager.getInt("delta_update_interval")){
            //Takes new snapshot
            PluginManager.getPlugin().getGameDao().updateGame(game);

            //Resets CommandCountSinceSnapshot for this game
            game.resetCommandCountSinceSnapshot();
        }
    }

    public static void addClientCommandToDatabase(String gameID, ICommand command, int indexOfCommand)  throws DatabaseException{
        PluginManager.getPlugin().getCommandDao().storeClientCommand(gameID, indexOfCommand, command);
    }

}
